package annex

object Show {
  // convert an int to a string:
  implicit val showInt: Show[Int] = _.toString
  // it is concise. the compiler looks at the show method in trait and interprets
  // the functions that I give here to transform to a string the values .
  implicit val showChar: Show[Char] = ch => s"'$ch'"
  implicit val showString: Show[String] = str => s""""$str""""

//  implicit val showAny: Show[Any] = any => any match{
//    case x: Int => showInt.show(x)
//    case x: Char => showChar.show(x)
//    case x: String => showString.show(x)
//  }

}
// a show type class allows us to convert something to a string
trait Show[T] { def show(value: T): String }



object Annex{
  implicit def conversion[T: Show](t:T): Annex[T] =
    new Annex[T](t, implicitly[Show[T]])
}


class Annex[T](value: T, typeclass: Show[T]){
  def apply(): String = typeclass.show(value)
}

object Main {

  def printAll[T](xs: Annex[_]*): Unit = // underscore makes annex a wildcard type
    xs.foreach { x => println(x()) }
  
  def main(args: Array[String]): Unit = {
/*     I want to printall, but the compiler does not allow it (no implicits found)
     The problem is that we need for T to be a supertype of Int, String, Char
     In this case, the type needed is Any!
     We could implement showAny (commented in the object Show), that redirects to type
     specific methos. But there is a problem with that!
     What if I want to add new types? I could add them one by one, but there is a better way!
     Instead, in this commit, I introduce an Annex class and object
     I use [T: Show] in th conversion, so that if we insert an unwanted type that is not i show,
     it will produce an error.


*/
    printAll(1, "two", '3')
  }
}
