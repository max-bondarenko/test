package com.max.entity.repo

import java.util.{List => JL}
import com.max.entity.Simple
import com.max.{LoggingTrait, HiberApplication}
import org.hibernate.{Session, Query}


/**
 * Created by mbondarenko on 20.05.14.
 */
trait SimpleRepoTrait {

  def getAll: JL[Simple]

  def get(id: Long): Simple
}

object SimpleRepo extends SimpleRepoTrait with HiberApplication with LoggingTrait {

  def inTransaction[A](f: => A)(implicit ses: Session): A = {
    val tx = ses.beginTransaction()
    val ret: A = f
    tx.commit()
    ret
  }

  def getAll = {
    implicit val cs = sf.getCurrentSession
    log.debug("getAll ")
    inTransaction {
      val list: JL[_] = cs.createCriteria(classOf[Simple]).list()
      log.debug("got result {}", list)
      list.asInstanceOf[JL[Simple]]
    }

  }

  def get(id: Long) = {
    implicit val cs = sf.getCurrentSession
    log.debug("get instance #{}", id)
    inTransaction {
      val createQuery: Query = cs.createQuery(s"select c from Simple c where id = $id ")
      val simple: Simple = createQuery.uniqueResult().asInstanceOf[Simple]
      simple
    }
  }
}
