package hlists

trait HList[+T <: HList[_]] { def :+:[V](value: V) = HCons(value, this) }
case class HCons[H, +T <: HList[_]](head: H, tail: T) extends HList[HCons[H, T]]
case object HNil extends HList[Nothing]
