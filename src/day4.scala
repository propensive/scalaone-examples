
object Example4 {

  //type F[T] = "if we're at stage 2, then F[T] = T, otherwise F[T] = Unit"

  sealed trait Checked[T]
  case class Valid[T](value: T) extends Checked[T]
  case class Invalid[T](error: String) extends Checked[T]

  object Email {
    def apply(str: String): Checked[Email] =
      if(str.contains("@")) Valid(new Email(str))
      else Invalid("There was no @")
  }

  trait Stage1 {
    type E[T] = Incomplete[T]
    type F[T] = Incomplete[T]
    type G[T] = Incomplete[T]
  }

  trait Stage2 extends Stage1 {
    type E[T] = Complete[T]
  }
  
  trait Stage3 extends Stage2 {
    type F[T] = Complete[T]
  }

  trait Stage4 extends Stage3 {
    type G[T] = Complete[T]
  }

  case class Email(email: String)
  case class Form[S <: Stage1](name: S#E[String] = None,
                              email: S#F[String] = None,
                              height: S#G[Int] = None,
                              age: S#G[Int] = None)

  type Incomplete[T] = None.type
  type Complete[T] = T

  val intermediate = Form[Stage1]()
  val last = Form[Stage3]("John Doe", "john@doe.com")

}




