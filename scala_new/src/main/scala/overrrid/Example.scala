package overrrid

/**
  * Created by Maksym_Bondarenko on 12/24/2016.
  */
class Example( x :El)  {
  override def toString = x.content
}

abstract class El {
  def content: String = "El"
}

class InhEl extends El {
  override val content: String = "InhEl"
}
