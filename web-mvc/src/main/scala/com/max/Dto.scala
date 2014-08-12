package com.max

import scala.beans.BeanProperty
import scala.collection.convert.WrapAsJava.seqAsJavaList


/**
 * Created by mbondarenko on 12.08.2014.
 */

case class Dto(@BeanProperty name: String,
               var ch: List[Dto],
               @BeanProperty id: Long,
               pr: Dto) {

  @BeanProperty
  def getCh: java.util.List[Dto] = ch

  @BeanProperty
  def getPr: Long = {
    Option(pr) match {
      case Some(e) => e.id
      case _ => -1
    }
  }
}

