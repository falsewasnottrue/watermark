package service

import java.util.concurrent.TimeUnit

import domain._
import domain.TicketStatus._
import domain.Topic._

import scala.concurrent.Future
import play.api.libs.concurrent.Akka.system
import scala.concurrent.duration.FiniteDuration

import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global

trait WatermarkService {
  def generateWatermark(document: Document): Ticket
  def status(ticket: Ticket): TicketStatus
  def retrieve(ticket: Ticket): Future[Option[Document]]
}

class MockWatermarkService extends WatermarkService {

  val storage = scala.collection.mutable.Map[Ticket, Document]()

  def generateWatermark(document: Document): Ticket = {
    val ticket = Ticket(java.util.UUID.randomUUID.toString)
    storage.put(ticket, document)

    // schedule watermark generation
    system.scheduler.scheduleOnce(new FiniteDuration(100, TimeUnit.MILLISECONDS)) {
      // TODO calculate watermark
      val documentWithWatermark = document.withWatermark(Watermark("test"))
      storage.put(ticket, documentWithWatermark)
    }

    ticket
  }

  def status(ticket: Ticket): TicketStatus =
    storage.get(ticket) match {
      case Some(document) if document.watermark.isDefined => Finished
      case Some(document) if document.watermark.isEmpty => Generating
      case _ => Unknown
    }

  def retrieve(ticket: Ticket): Future[Option[Document]] =
    Future.successful(Some(Book("title", "author", Business, None)))
}