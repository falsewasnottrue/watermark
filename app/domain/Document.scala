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

  def withWatermark(watermark: Watermark): Document
}

import Topic._
case class Journal(title: String, author: String, watermark: Option[Watermark]) extends Document {
  def withWatermark(watermark: Watermark) = Journal(title, author, Some(watermark))
}
case class Book(title: String, author: String, topic: Topic, watermark: Option[Watermark]) extends Document {
  def withWatermark(watermark: Watermark) = Book(title, author, topic, Some(watermark))
}