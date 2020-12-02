object Examples2 {

  trait Show[T] { def show(value: T): String }

  implicit def conv[T: Show](t: T): Annex[T] = new Annex[T](t, implicitly[Show[T]])

  class Annex[T](val value: T, val show: Show[T])

  implicit val showInt: Show[Int] = _.toString
  implicit val showString: Show[String] = s => s""""$s""""
  implicit val showDouble: Show[Double] = d => s"double: $d"

  def showAll(xs: Annex[_]*): Unit =
    xs.foreach { case annex: Annex[_] =>
      println(annex.show.show(annex.value))
    }
  
  def main(args: Array[String]): Unit = showAll(1, "two", 3.0)


}