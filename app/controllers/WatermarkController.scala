package controllers

import domain._

import play.api.mvc._
import service.MockWatermarkService

class WatermarkController extends Controller {

  val watermarkService = new MockWatermarkService

  def book(title: String, author: String, topicName: String) = Action {
    val topic = Topic.withName(topicName)
    val book = Book(title, author, topic, None)

    val ticket = watermarkService.generateWatermark(book)
    Ok(ticket.value)
  }

  def journal(title: String, author: String) = Action {
    val journal = Journal(title, author, None)
    val ticket = watermarkService.generateWatermark(journal)
    Ok(ticket.value)
  }

  def status(ticketValue: String) = Action {
    val status = watermarkService.status(Ticket(ticketValue))
    Ok(status.toString)
  }

  def retrieve(ticketValue: String) = Action {
    watermarkService.retrieve(Ticket(ticketValue)) match {
      case Some(document) => Ok(document.toString)
      case None => NotFound
    }
  }
}
