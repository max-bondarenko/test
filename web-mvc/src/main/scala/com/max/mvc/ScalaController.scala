package com.max.mvc

import com.max.Dto
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

/**
 * Created by mbondarenko on 12.08.2014.
 */
@Controller
class ScalaController {
  @RequestMapping(value = Array("/scala"), produces = Array("application/json"))
  @ResponseBody
  def getScala: Dto = {

    val dto: Dto = Dto("dto name 1", null, 100, null)
    dto.ch = List(
      Dto("dto ch 1", Nil, 1001, dto),
      Dto("dto ch 2", Nil, 1002, dto)
    )

    dto
  }

}
