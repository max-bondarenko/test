/**
 * Created by mbondarenko on 17.01.14.
 */
object FuncObj {
  def apply[A](a: A)(op: A =>  Int) = op(a)
}
