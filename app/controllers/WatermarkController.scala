package controllers

import domain._

import play.api._
import play.api.mvc._

class WatermarkController extends Controller {

  def book(title: String, author: String, topic: String) = Action {
    val ticket = Ticket("4722")
    Ok(ticket.value)
  }


  def journal(title: String, author: String) = Action {
    Ok
  }

  def status(ticket: String) = Action {
    Ok("FINISHED")
  }

  def retrieve(ticket: String) = Action {
    Ok("DOCUMENT")
  }
}
