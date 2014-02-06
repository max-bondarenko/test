/**
 * Created by mbondarenko on 10.01.14.
 */

object Te {

  def main(arg: Array[String]) {

    println(new S)
    println(new S(second = "adsfdsd"))
  }
}


case class S(f:Int = 0 , second: String = "sec" )




