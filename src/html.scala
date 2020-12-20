package html

object Html {
  
  val table = Tag("table")
  val thead = Tag("thead")
  val tbody = Tag("tbody")
  val tr = Tag("tr")
  val td = Tag("td")
  val th = Tag("th")

  val myTable = table(
    thead(
      tr(
        th("Logic"), th("Types")
      ),
    ),
    tbody(
      tr(
        td("true"), td("Any")
      ),
      tr(
        td("false"), td("Nothing")
      )
    )
  )

  val shouldNotCompile = table(
    thead(
      th("Misplaced <th/>")
    )
  )

  def main(args: Array[String]): Unit = println(myTable)

}
object Node {
  implicit def toText(str: String): Node = Text(str)
}

sealed trait Node

case class Element(tag: Tag, content: Seq[Node]) extends Node {
  override def toString(): String =
    s"<${tag.id}>${content.mkString}</${tag.id}>"
}

case class Text(str: String) extends Node {
  override def toString(): String = str
}

case class Tag(id: String) {
  def apply(nodes: Node*): Node = Element(this, nodes)
}

