package com.max.mvc

/**
 * Created by mbondarenko on 12.08.2014.
 */
@Controller
class ScalaController {
  @RequestMapping(value = Array("/scala"), produces = Array("application/json"))
  @ResponseBody
  def getScala = {
    "{ name : got }"
  }

}
