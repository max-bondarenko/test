/**
 * Created by mbondarenko on 08.02.14.
 */

import org.scalatest.FlatSpec

class TestAsSpec extends FlatSpec {

  "A list " should "have 3 elements "  in {
    val l = List(1,2,3)

    assert( l.length == 3)
  }


}
