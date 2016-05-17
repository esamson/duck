name := "duck"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.github.scopt" %% "scopt" % "3.4.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0"
)

libraryDependencies ++= Seq(
  "ch.qos.logback" %  "logback-classic" % "1.1.7"
) map (_ % Runtime)
