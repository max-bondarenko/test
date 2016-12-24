/**
  * Created by Maksym_Bondarenko on 12/24/2016.
  */
class Examples {

  def twice(x: Double, op: Double => Double): Double = {
    val op1: Double = op(x)
    val op2: Double = op(op1)
    op2
  }

  def twice2(x: Double)(op: Double => Double): Double = {
    val op1: Double = op(x)
    val op2: Double = op(op1)
    op2
  }

  def myAssert(pred: () => Boolean): Boolean = {
    !pred()
  }

  def myAssertByName(pred: => Boolean): Boolean = {
    !pred
  }

}
