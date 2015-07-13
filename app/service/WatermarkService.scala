package service

import java.util.concurrent.TimeUnit

import domain._
import domain.TicketStatus._

import play.api.libs.concurrent.Akka.system
import scala.concurrent.duration.FiniteDuration

import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global

trait WatermarkService {
  def generateWatermark(document: Document): Ticket
  def status(ticket: Ticket): TicketStatus
  def retrieve(ticket: Ticket): Option[Document]
}

class MockWatermarkService extends WatermarkService {

  val storage = scala.collection.mutable.Map[Ticket, Document]()

  // duration for "watermark generation"
  val delay: FiniteDuration = new FiniteDuration(100, TimeUnit.MILLISECONDS)

  def generateWatermark(document: Document): Ticket = {
    val ticket = Ticket(java.util.UUID.randomUUID.toString)
    // persist document without watermark
    storage.put(ticket, document)

    // schedule watermark generation
    system.scheduler.scheduleOnce(delay) {
      // after delay, "generate" watermark and substitute in storage
      storage.put(ticket, document.generateWatermark)
    }

    ticket
  }

  def status(ticket: Ticket): TicketStatus =
    storage.get(ticket) match {
      case Some(document) if document.watermark.isDefined => Finished
      case Some(document) if document.watermark.isEmpty => Generating
      case _ => Unknown
    }

  def retrieve(ticket: Ticket): Option[Document] =
    storage.get(ticket) match {
      case Some(document) if document.watermark.isDefined => Some(document)
      case _ => None
    }
}