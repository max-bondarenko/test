package run

import scala.collection.mutable.ListBuffer

/**
  * Created by Maksym_Bondarenko on 12/24/2016.
  */
trait OwnApp {
  type Ref = () => Unit

  private val initCode = new ListBuffer[Ref]

  def ownApp(e: () => Unit)  {
    initCode +=  e
  }

  def main(args: Array[String]) {
    for (a <- initCode) {
      a()
    }

    Console.println("asdsd")
  }
}