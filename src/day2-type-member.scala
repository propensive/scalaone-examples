object Examples {

  trait Show[T] { def show(value: T): String }

  implicit def conv[S: Show](t: S): Annex { type T = S } =
    new Annex {
      val value = t
      val show = implicitly[Show[T]]
      type T = S
    }

  abstract class Annex {
    val value: T
    val show: Show[T]
    type T
  }

  implicit val showInt: Show[Int] = _.toString
  implicit val showString: Show[String] = s => s""""$s""""
  implicit val showDouble: Show[Double] = d => s"double: $d"

  def showAll(xs: Annex*): Unit =
    xs.foreach { a =>
      println((a.show: Show[a.T]).show(a.value: a.T))
    }

  implicitly[Annex =:= (Annex { type T = S } forSome { type S })]

  def main(args: Array[String]): Unit = showAll(1, "two", 3.0)


}