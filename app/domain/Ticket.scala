package domain

case class Ticket(value: String)

object TicketStatus extends Enumeration {
  type TicketStatus = Value
  val Generating, Finished, Unknown = Value
}