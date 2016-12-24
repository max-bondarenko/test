package run


/**
  * Created by Maksym_Bondarenko on 12/24/2016.
  */
trait OwnApp {
  type Ref = () => Unit

  private var initCode: Ref = _

  def ownApp(e: () => Unit) {
    initCode = e
  }

  def main(args: Array[String]) {
    initCode

    Console.println("asdsd")
  }
}