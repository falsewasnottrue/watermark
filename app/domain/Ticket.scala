package domain

case class Ticket(value: String)

sealed trait TicketStatus
object Generating extends TicketStatus
object Finished extends TicketStatus
object Unknown extends TicketStatus