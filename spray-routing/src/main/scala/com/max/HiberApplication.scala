package com.max

import org.hibernate.cfg.Configuration
import org.hibernate.SessionFactory
import org.hibernate.service.{ServiceRegistry, ServiceRegistryBuilder}

/**
 * Created by mbondarenko on 20.05.14.
 */
trait HiberApplication extends LoggingTrait {

  var cfg: Configuration = new Configuration()

  cfg.configure()
  private val buildServiceRegistry: ServiceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties).buildServiceRegistry()

  val sf: SessionFactory = {
    log.debug("create session factory")
    cfg.buildSessionFactory(buildServiceRegistry)
  }

}

object HiberApplication extends HiberApplication
