name := "date-time-range-parser"

version := "0.0.1"

crossScalaVersions := Seq("2.10.6", "2.11.8", " 2.12.1")

val scalaTestVersion = "3.0.1"
val scalaMockVersion = "3.5.0"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion,
  "org.scalamock" %% "scalamock-scalatest-support" % scalaMockVersion
)
