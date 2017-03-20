name := "date-time-range-parser"

version := "1.0"

scalaVersion := "2.11.8"

val scalaTestVersion = "3.0.1"
val scalaMockVersion = "3.5.0"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion,
  "org.scalamock" %% "scalamock-scalatest-support" % scalaMockVersion
)
