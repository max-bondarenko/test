import sbt._
import Keys._

name := "hello"

version := "1.0"

scalaVersion := "2.10.3"

externalResolvers := Seq(Resolver.url("test", url("http://localhost:8081/nexus/content/sites/1/"))
)

libraryDependencies += "commons-lang" % "commons-lang" % "2.1"
