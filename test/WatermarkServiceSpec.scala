import org.specs2.mutable._
import play.api.test.WithApplication

import domain._
import domain.TicketStatus._

import service.MockWatermarkService

class WatermarkServiceSpec extends Specification {

  val watermarkService = new MockWatermarkService

  "WatermarkService" should {
    "return Unknown status for unknown ticket" in {
      val status = watermarkService.status(Ticket("gibtsnicht"))

      status must beEqualTo(Unknown)
    }

    "return Generating status for recently created ticket" in new WithApplication(new EmptyFakeApplication) {
      val document = Journal("sometitle", "someauthor", None)
      val ticket = watermarkService.generateWatermark(document)

      val status = watermarkService.status(ticket)
      status must beEqualTo(Generating)
    }

    "return Finished status after ticket was processed" in new WithApplication(new EmptyFakeApplication) {
      val document = Journal("sometitle", "someauthor", None)
      val ticket = watermarkService.generateWatermark(document)

      // wait sufficiently long
      Thread.sleep(200)

      val status = watermarkService.status(ticket)
      status must beEqualTo(Finished)
    }
  }
}
