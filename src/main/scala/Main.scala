import scala.concurrent.ExecutionContext

object Main extends App {
  
  // new types
  
  // intersection
  
  trait A {
    def one = 1
  }
  
  trait B {
    def two = 2
  }
  
  type C = A & B
  
  type Cw = A with B
  
  // union
  trait Location
  case class Store(address: String, openingHours: String) extends Location
  case class Factory(address: String) extends Location
  
  
  def handleLocation(loc: Location): Unit = {
    loc match {
      case Store(_, _) => println("It's a store!")
      case Factory(_) => println("It's a factory")
    }
  }
  
  // match types
  type HeadType[T <: NonEmptyTuple] = T match {
    case h *: _ => h
  // NOTE: this is the same as in the new standard library
  }
  
  def tupleHead[T <: NonEmptyTuple](t: T): HeadType[T] =
    t.head
  
  val x: String = tupleHead(("bla", 6))
  
  // enums
  
  enum Color {
    case Red, Green, Blue
  }

  enum MyOption[+T] {
    case Some(x: T) extends MyOption[T]

    case None extends MyOption[Nothing]
    
  }
  
  val some5 = MyOption.Some(5)
  
  // implicits
  
  implicit val ec: ExecutionContext = ???


  
  implicit object IntListOrd extends Ordering[List[Int]] {
    override def compare(x: List[Int], y: List[Int]): Int = x.size - y.size
  }
  
  
  def printListCompare(x: List[Int], y: List[Int])(implicit ord: Ordering[List[Int]]): Unit =
    if(ord.compare(x, y) < 0) println("x is smaller than y")
    else println("y is smaller than x")
  

  
  def printGenericCompare[T : Ordering](x: T, y: T) = x match {
    case (x, y): (List[Int], List[Int]) => printListCompare(x, y)
  }
  
  implicit class ListWithCompare(lst: List[Int]) {
    def printCompare(thatLst: List[Int]): Unit = printListCompare(lst, thatLst)
  }
  

  
  implicit def toIntList(strLst: List[String]): List[Int] = strLst.map(_.size)

  
  
  
  
  
  
}
