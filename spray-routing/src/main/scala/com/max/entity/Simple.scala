package com.max.entity

import javax.persistence.{Column, Entity, GeneratedValue, Id}
import scala.annotation.meta.field
import scala.beans.BeanProperty

/**
 * Created by mbondarenko on 20.05.14.
 */
@Entity
class Simple {
  @(Id@field)
  @(GeneratedValue@field)
  @BeanProperty
  var id: Long = 0
  @(Column@field)
  @BeanProperty
  var name: String = null


  def canEqual(other: Any): Boolean = other.isInstanceOf[Simple]

  override def equals(other: Any): Boolean = other match {
    case that: Simple =>
      (that canEqual this) &&
        id == that.id &&
        name == that.name
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id, name)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String = s"Simple{ id = $id ; name = $name}"
}

object Simple {
  def apply(id: Long, name: String) {
    val ret = new Simple
    ret.id = id
    ret.name = name
    ret
  }
}

