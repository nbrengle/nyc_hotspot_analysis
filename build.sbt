import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.nbrengle",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Main",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.4"
  )
