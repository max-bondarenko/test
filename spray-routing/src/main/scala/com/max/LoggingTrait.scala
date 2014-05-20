package com.max

/**
 * Created by mbondarenko on 20.05.14.
 */
trait LoggingTrait {
  self =>

  import org.slf4j._

  lazy val log: Logger = LoggerFactory.getLogger(self.getClass)

}
