package spring.org.max

import com.typesafe.scalalogging.slf4j.Logging
import org.springframework.scala.context.function.FunctionalConfigApplicationContext


/**
 * Created by mbondarenko on 26.02.14.
 */
object SpringStarter extends App with Logging {

  logger.info("Start app ")
  val ctx = FunctionalConfigApplicationContext(classOf[MyConf])

  ctx.beanNamesForType[Simple](true, false).foreach(println)

  private val bean: Simple = ctx.getBean("joe", classOf[Simple])
  println(bean)

}


