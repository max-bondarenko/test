/**
 * Created by mbondarenko on 08.02.14.
 */


object ImplicitTest extends App {

  import Help.ToOlolo

  val asd = "dfasdf"
  println(ol"asdfasdf $asd ")

  println(s"asdfasdf $asd ")
}

object Help {

  implicit class ToOlolo(val sc: StringContext)  {
    def ol(x: Any*): String = sc s x


  }

}


