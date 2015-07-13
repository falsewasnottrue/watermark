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
}

import Topic._
case class Journal(title: String, author: String, watermark: Option[Watermark]) extends Document
case class Book(title: String, author: String, topic: Topic, watermark: Option[Watermark]) extends Document