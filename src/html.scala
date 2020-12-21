package html

// The objective of this example is representing a subset of html tags
object Html {
  
  val table = Tag[thead.type with tbody.type ]("table")
  val thead = Tag[tr.type]("thead")
  val tbody = Tag[tr.type]("tbody")
  //At first , val tr = Tag[th.type with td.type]("tr") does not work, since there is a problem of invariance
  // There is no relationship between the types. We maywrite Node [-C] instead of Node [C]
  val tr = Tag[th.type with td.type]("tr")
  val td = Tag[Any]("td")
  val th = Tag[Any]("th")

  // representation of an html table as a scala code
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
      // the problem here is that html says that th must be inside a tr.
      // I need to enforce this rule, so that this does actually not compile

      // After enforcing the rule (in this commit), the compiler does not like it
      th("Misplaced <th/>")
    )
  )

  def main(args: Array[String]): Unit = println(myTable)

}
object Node {
  implicit def toText(str: String): Node[Any] = Text(str)
}

// with the - we make it invariant.
sealed trait Node[-C]

case class Element[C](tag: Tag[_], content: Seq[Node[_]]) extends Node[C] {
  // With this method we convert it to a string, with concatenation and the use of mkString
  override def toString(): String =
    s"<${tag.id}>${content.mkString}</${tag.id}>"
}

case class Text(str: String) extends Node[Any] {
  override def toString(): String = str
}

case class Tag[C](id: String) {
  // the apply method in Tag can have multiple nodes ( indicated by the star)
  def apply(nodes: Node[C]*): Node[this.type] = Element[this.type](this, nodes)
}

