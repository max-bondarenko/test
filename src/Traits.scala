/**
 * Created by mbondarenko on 15.01.14.
 */
class Traits {


}

trait A1 {
  var list: List[Int] = List()

  def add(i: Int): Unit = list = i :: list

  def remove(i: Int): Unit = list = list.filterNot {_ == i}

}

trait A2 extends A1 {

  def  +=(x: Int): Unit =  list = list ++ List(x)

}
