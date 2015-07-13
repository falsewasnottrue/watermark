
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

import domain._
import domain.Topic._

class DomainSpec extends Specification {

  "Domain specification" should {
    "books are documents with topic" in {
      // {content:”book”, title:”How to make money”, author:”Dr. Evil”, topic:”Business”}
      val book = Book("How to make money", "Dr. Evil", Business, None)

      book must beAnInstanceOf[Document]
      book.title must be("How to make money")
      book.author must be("Dr. Evil")
      book.topic must be(Business)
      book.watermark must beNone
    }

    "journals are documents without topic" in {
      // {content:”journal”, title:”Journal of human flight routes”, author:”Clark Kent”}
      val journal = Journal("Journal of human flight routes", "Clark Kent", None)

      journal must beAnInstanceOf[Document]
      journal.title must be("Journal of human flight routes")
      journal.author must be("Clark Kent")
      journal.watermark must beNone
    }
  }
}
