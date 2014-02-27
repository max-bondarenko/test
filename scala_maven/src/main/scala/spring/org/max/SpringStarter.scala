package spring.org.max

import com.typesafe.scalalogging.slf4j.Logging
import org.springframework.scala.context.function.FunctionalConfigApplicationContext


/**
 * Created by mbondarenko on 26.02.14.
 */
object SpringStarter extends App with Logging {

  logger.info("Start app ")
  val ctx = FunctionalConfigApplicationContext(classOf[MyConf])

  val beansOfType = ctx.beanNamesForType[Simple](includeNonSingletons = true, allowEagerInit = false).mkString(",")
  logger.info("get beanNamesForType [Simple] : {}", beansOfType)


  private val bean: Simple = ctx.getBean("joe", classOf[Simple])
  logger.info("got bean {} ", bean)

}


