package `trait`

/**
  * Created by Maksym_Bondarenko on 12/26/2016.
  */

trait MixInToString{
  self =>

  override def toString = "MixInToString"+ self.getClass.toString
}
trait MixInToString2 {
  override def toString = "MixInToString2"
}


class Example extends MixInToString2 with MixInToString {

}

