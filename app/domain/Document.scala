package domain

case class Watermark(value: String)

sealed trait Topic
object Business extends Topic
object Science extends Topic
object Media extends Topic

trait Document {
  def title: String
  def author: String
  def watermark: Option[Watermark]
}

case class Journal(title: String, author: String, watermark: Option[Watermark]) extends Document
case class Book(title: String, author: String, topic: Topic, watermark: Option[Watermark]) extends Document