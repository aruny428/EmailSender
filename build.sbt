name := """EmailSender2"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.6"

libraryDependencies += guice
libraryDependencies ++= Seq("org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.6.0",
  "javax.mail" % "javax.mail-api" % "1.5.6",
  "com.sun.mail" % "javax.mail" % "1.5.6",
  "org.apache.commons" % "commons-email" % "1.3.1")
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
