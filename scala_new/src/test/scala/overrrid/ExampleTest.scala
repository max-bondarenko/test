package overrrid

import org.scalatest.FlatSpec

/**
  * Created by Maksym_Bondarenko on 12/24/2016.
  */
class ExampleTest extends FlatSpec {

  "El class" should "return El name " in {
    val e  = new Example(new El {})
    assert("El" == e.toString())
  }

  "InhEl class" should "return InhEl name " in {
    val e  = new Example(new InhEl)
    assert("InhEl" == e.toString())
  }

}
