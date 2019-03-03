name := "StateActors"

version := "0.1"

scalaVersion := "2.12.8"

version := "1.0"

scalaVersion := "2.12.6"

lazy val akkaVersion = "2.5.21"
lazy val akkaHttpVersion = "10.1.7"

libraryDependencies ++= Seq(
  //Akka Core
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,


  //Tests
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)