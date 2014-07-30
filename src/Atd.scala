/**
 * Created by mbondarenko on 12.02.14.
 */
//sealed trait Atd {}

class ConAtdLike(val x: Int)

object ConAtdLike {
  //  def apply()  = new ConAtdLike()
  //  def apply(x: Int): ConAtdLike = new ConAtdLike(x)


  def unapply(x: Int): Option[Int] = if (x > 100) Some(x) else None

  def unapply(t: ConAtdLike): Option[Int] = t match {
    //t as
    case _ if t.x > 100 => Some(t.x)
    case _ if t.x == 100 => Some(3)
    case _ => None
  }
}

object AtdTest2 extends App {

  val like = new ConAtdLike(100)

  like match {
    case ConAtdLike(3.0) => println('three) //uses unapply (x: ConAtdLike ) : Option
    case ConAtdLike(n) => println("some " + n)
    case _ => println('nothing)
  }

}


/*
case class ConcreatAtd(x: Int) extends Atd

case class SecondAtd(x: Int, d: Double) extends Atd


object AtdTest extends App {
  val atd: Any = ConcreatAtd(1)
  atd match {
    case  e: ConcreatAtd => print('atd) //correct
    case ConcreatAtd => print('atdINC) //incorrect
    case SecondAtd(_, _) => print('atd2)
    //    case ConcreatAtd(x) => print('noatd)
    case _ => print('none)
  }
}
*/



