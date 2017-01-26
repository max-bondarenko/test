lazy val root = (project in file("."))
  .settings(
    name := "scala_new",
    version := "1.0_SNAPSHOT",
    scalaVersion := "2.11.7"
  )

resolvers += "Artima Maven" at "http://repo.artima.com/releases"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.+" % "test"
