package spring.org.max

import org.springframework.scala.context.function.FunctionalConfiguration

/**
 * Created by mbondarenko on 27.02.14.
 */
class MyConf extends FunctionalConfiguration{
  bean(name = "joe"){
    new Simple
  }
  bean(name = "list"){
    List[String]("lkhna;ldjf","asdlfjhasdlfs","asldfjhdkasjhfask")
  }

}
