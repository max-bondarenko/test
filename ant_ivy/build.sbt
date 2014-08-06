import sbt._
import Keys._

name := "hello"

version := "1.0"

scalaVersion := "2.10.3"

scalaHome := Some(file("D:\\Java\\tools\\Scala.2.10.3"))

logLevel := Level.Debug

externalResolvers := Seq()

libraryDependencies += "commons-lang" % "commons-lang" % "2.1"
