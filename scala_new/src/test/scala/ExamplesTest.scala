import `case`.Example
import org.scalatest.FunSuite

/**
  * Created by Maksym_Bondarenko on 12/24/2016.
  */
class ExamplesTest extends FunSuite {

  private val examples: Examples = new Examples

  test("testTwice") {
    val twice: Double = examples.twice(1, _ + 5)
    assert(twice == 11.0)
  }

  test("testTwice2") {
    val twice: Double = examples.twice2(2) {
      _ + 5.0
    }
    assert(twice == 12.0)
  }

  test("no name") {
    assert(true == examples.myAssert(() => 3 > 5))
  }
  test("by name ") {
    assert(true == examples.myAssertByName(3 > 5))
  }

  test("Pattern match type ") {
    def s(s: Any) = s match {
      case x: String => "is str"
      case m: Map[_, String] => "any to obj"
      case a : Array[String] => "strings"
      case a : Array[Int] => "ints"
      case List(e@_,_) => e

      case _ => None
    }

    assert("is str" == s("String"))

    assert(None == s(10))

    assert("any to obj" == s(Map[String, Int]())) //type erasure
    assert("any to obj" == s(Map[Int, String]()))

    assert("ints"  == s(Array[Int](1,2)))
    assert("strings"  == s(Array[String]("1,2")))

    assert("Next" == s(List("Next","Prev")))

    val strList : List[String] = "Prev" :: "Next" :: Nil
    assert("Next" != s( strList ))
    assert("Prev" == s( strList ))

  }

  test("reverse pattern"){
    val straight = Example("a")
    val Example(reverse) = straight

    assert("a" == reverse )
  }


  test("fold"){
    val strings: List[String] = List("a","b","c","d")

    val l: String = ("_" /: strings) (_ + _)
    assert("_abcd" == l)
    val r = strings.foldRight("_")( (a,accum) => a+ " " + accum)
    assert("a b c d _" == r)
  }

}
