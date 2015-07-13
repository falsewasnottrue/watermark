
import org.specs2.mutable._

import play.api.test._

import domain._
import domain.TicketStatus._

import service.MockWatermarkService

class IntegrationSpec extends Specification {

  val watermarkService = new MockWatermarkService

  "Application" should {
    "execute entire use case" in new WithApplication(new EmptyFakeApplication()) {

      val title = "Dies ist der Titel"
      val author = "Dies ist der Autor"

      val journal = Journal(title, author, None)

      val ticket = watermarkService.generateWatermark(journal)

      val statusGenerating = watermarkService.status(ticket)
      statusGenerating must equalTo(Generating)

      watermarkService.retrieve(ticket) must equalTo(None)

      Thread.sleep(200l)

      val statusFinished = watermarkService.status(ticket)
      statusFinished must equalTo(Finished)

      val documentFinished = watermarkService.retrieve(ticket)
      documentFinished must beSome[Document]
      documentFinished.get.title must equalTo(title)
      documentFinished.get.author must equalTo(author)
      documentFinished.get.watermark must beSome[Watermark]
      documentFinished.get.watermark.get must equalTo(Watermark("{content:”journal”, title:”Dies ist der Titel”, author:”Dies ist der Autor”}"))
    }
  }
}
