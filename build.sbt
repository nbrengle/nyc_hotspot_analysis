import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.nbrengle",
      scalaVersion := "2.11.11",
      version      := "0.1.0"
    )),
    name := "Main",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.4",
    libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.1.1"
  )
