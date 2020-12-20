package annex

object Show {
  implicit val showInt: Show[Int] = _.toString
  implicit val showChar: Show[Char] = ch => s"'$ch'"
  implicit val showString: Show[String] = str => """"$str""""
}

trait Show[T] { def show(value: T): String }

object Main {
  
  def printAll[T: Show](xs: T*): Unit =
    xs.foreach { x => println(implicitly[Show[T]].show(x)) }
  
  def main(args: Array[String]): Unit =
    ()
    //printAll(1, "two", '3')
}
