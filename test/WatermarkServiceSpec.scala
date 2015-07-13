import org.specs2.mutable._
import play.api.{DefaultGlobal, GlobalSettings}
import play.api.mvc.Handler
import play.api.test.{WithApplication, FakeApplication}

//import org.specs2.runner._
//import org.junit.runner._
//
//import play.api.test._
//import play.api.test.Helpers._

import domain._
//import domain.Topic._
import domain.TicketStatus._

import service.MockWatermarkService

class EmptyFakeApplication(override val withRoutes: PartialFunction[(String, String), Handler] = PartialFunction.empty, override val withoutPlugins: Seq[String] = Seq("is24.modis.reporting.MetricsPlugin"), override val withGlobal: Option[GlobalSettings] = Some(DefaultGlobal), override val additionalConfiguration: Map[String, Any] = Map()) extends FakeApplication(withGlobal = withGlobal, additionalConfiguration = additionalConfiguration, withRoutes = withRoutes, withoutPlugins = withoutPlugins)

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
      Thread.sleep(500)

      val status = watermarkService.status(ticket)
      status must beEqualTo(Finished)
    }
  }
}
