
object Tags {

  implicit def conv(str: String): Node[Nothing] = ???

  class Node[C]()
  
  object Tag {
    def apply[C1](): Tag { type C = C1 } = new Tag() { type C = C1 }
    //def apply[C1](): Tag[C1] = new Tag[C1]()
  }

  class Tag() {
    type C
    def apply[T](nodes: Node[_ >: C]*): Node[this.type] = ???
  }

  val table = Tag[thead.type with tbody.type]()
  val thead = Tag[tr.type]()
  val tbody = Tag[tr.type]()
  val tr = Tag[td.type with th.type]()
  val td = Tag[Nothing]()
  val th = Tag[Nothing]()
}


object Example {
  import Tags._
  def main(args: Array[String]): Unit =
    table(
      thead(
        tr(th("Head 1"), th("Head 2"))
      ),
      thead(),
      tbody(
        tr(td("Cell 1"), td("Cell 2"))
      )
    )
}