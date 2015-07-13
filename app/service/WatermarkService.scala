package service

import domain._
import domain.TicketStatus._
import domain.Topic._

import scala.concurrent.Future

trait WatermarkService {
  def generateWatermark(document: Document): Ticket
  def status(ticket: Ticket): TicketStatus
  def retrieve(ticket: Ticket): Future[Option[Document]]
}

class MockWatermarkService extends WatermarkService {

  def generateWatermark(document: Document): Ticket = Ticket("4711")

  def status(ticket: Ticket): TicketStatus = Generating

  def retrieve(ticket: Ticket): Future[Option[Document]] =
    Future.successful(Some(Book("title", "author", Business, None)))
}