package domain

case class Watermark(value: String)

object Topic extends Enumeration {
  type Topic = Value
  val Business, Science, Media = Value
}

trait Document {
  def title: String
  def author: String
  def watermark: Option[Watermark]

  def generateWatermark: Document
}

import Topic._
case class Journal(title: String, author: String, watermark: Option[Watermark]) extends Document {
  def generateWatermark: Document = copy(watermark = Some(Watermark(s"{content:”journal”, title:”$title”, author:”$author”}")))
}

case class Book(title: String, author: String, topic: Topic, watermark: Option[Watermark]) extends Document {
  def generateWatermark: Document = copy(watermark = Some(Watermark(s"{content:”book”, title:”$title”, author:”$author”, topic:”${topic.toString}”}")))
}