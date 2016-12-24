import org.scalatest.FunSuite

/**
  * Created by Maksym_Bondarenko on 12/24/2016.
  */
class ExamplesTest extends FunSuite {

  private val examples: Examples = new Examples

  test("testTwice") {
     val twice: Double = examples.twice(1, _ + 5)
     assert( twice == 11.0)
  }

  test("testTwice2"){
    val twice: Double = examples.twice2(2) {
      _ + 5.0
    }
    assert( twice == 12.0)
  }

  test("no name"){
     assert( true == examples.myAssert(() => 3 > 5))
  }
  test("by name "){
     assert( true == examples.myAssertByName(3 > 5))
  }

}
