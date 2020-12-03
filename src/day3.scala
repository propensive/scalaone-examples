

trait NotEqualTo[A, B]
object NotEqualTo extends NotEqualToLowerPriority {
  implicit def net[A]: NotEqualTo[A, A] = null
  implicit def net2[A]: NotEqualTo[A, A] = null
}

trait NotEqualToLowerPriority {
  implicit def net3[A, B]: NotEqualTo[A, B] = null
}

object Example3 {
  def main(args: Array[String]): Unit = {
    val myTmap = new TMap[Any]().add(gender)('m').add(age)(20).add(name)("John Doe").
                     add(birthYear)(2000)
    println(myTmap(anything))
  }

  class Key[T]()
  val age = new Key[Int]()
  val birthYear = new Key[Int]()
  val name = new Key[String]()
  val gender = new Key[Char]()
  val anything = new Key[Any]()

  class TMap[C](map: Map[Key[_], Any] = Map()) {
    def add[T](key: Key[T])(value: T)(implicit ev: (C with key.type) NotEqualTo C): TMap[C with key.type] = new TMap(map + (key -> value))
    def get[T](key: Key[T]): Option[T] = map.get(key).asInstanceOf[Option[T]]
    def apply[T](key: Key[T])(implicit ev: C <:< key.type): T = map(key).asInstanceOf[T]
  }

}