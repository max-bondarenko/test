/**
 * Created by mbondarenko on 08.02.14.
 */

import org.junit.Test
import org.junit.Assert._

class TestLikeJUnit {
  @Test def testOne() {
    val a = 0.5f > 0
    assertTrue(a)
  }

}
