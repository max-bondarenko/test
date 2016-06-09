import org.scalatest._

/**
 * Created by mbondarenko on 08.02.14.
 */
class SimpleScalaTest extends FunSuite {

  test("get this") {
    assert(1 > 0)
  }

  ignore("wrong test") {
    assert(0 > 1)
  }

  test("test with exception") {

    intercept[ArithmeticException] {
      assert((1 / 0) > 0)
    }
  }


}
