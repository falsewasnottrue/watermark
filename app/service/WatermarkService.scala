package service

import domain._

import scala.concurrent.Future

trait WatermarkService {
  def generateWatermark(document: Document): Ticket
  def status(ticket: Ticket): TicketStatus
  def retrieve(ticket: Ticket): Future[Option[Document]]
}

class MockWatermarkService extends WatermarkService {
  def generateWatermark(document: Document): Ticket = ???
  def status(ticket: Ticket): TicketStatus = ???
  def retrieve(ticket: Ticket): Future[Option[Document]] = ???
}