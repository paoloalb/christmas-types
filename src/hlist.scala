package hlists

trait HList[+T <: HList[_]] {
  def :+:[V](value: V) = HCons(value, this)
  def length: Int}
// T has a + , that makes it covariant.
// The <: makes it a subtype of Hlist[_]. The underscore maes it existential.
// So the contraint is that T must be an hlist of anything
case class HCons[H, +T <: HList[_]](head: H, tail: T) extends HList[HCons[H, T]]{
  def length = tail.length + 1
}

// We use Nothing since in the nil case we have noelements
case object HNil extends HList[Nothing] {
  def length = 0
} 


object Test {
  // the type of myHlist is getting more and more complicated as I add elements.
  // it represents the entire heterogeneous list.
  val myHlist = 'x' :+: "hello" :+: 42 :+: HNil
}

// what is the covariant (+ in front of the T) does?
// in this example:
class Option[+T] {
  def get: T = ???
}
// When we write Option[T], the + means Option[X or a subtype of X]
// if xes i have an Option[Exception] in a method, I only know that it returns an exception.
// this exception can be whatever