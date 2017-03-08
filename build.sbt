name := "date-time-range-parser"

version := "1.0"

scalaVersion := "2.11.8"

val shapelessVersion = "2.3.2"
val scalaTestVersion = "2.2.5"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % shapelessVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion
)
