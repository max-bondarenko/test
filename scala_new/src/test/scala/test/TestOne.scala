package test

import org.scalatest.FunSuite

/**
  * Created by Maksym_Bondarenko on 12/22/2016.
  */
class TestOne  extends FunSuite {

  test("Test one"){
    assert(true)
  }

  test("List"){
    val list = List(1,2,4)
    assertResult(List(1,2,4)){
      list
    }
  }
}
