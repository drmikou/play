name := """catnix-core"""

version := "0.1"

scalaVersion := "2.11.6"

playEbeanModels := Seq("models.*")

libraryDependencies ++= Seq(
  javaJdbc,
  evolutions,
  cache,
  javaWs,
  "be.objectify" %% "deadbolt-java" % "2.4.0",
  "it.innove" % "play2-pdf" % "1.2.0",
  "org.jsoup" % "jsoup" % "1.8.2",
  "org.gitlab" % "java-gitlab-api" % "1.1.9",
  "com.typesafe.play" %% "play-mailer" % "3.0.1"
)

routesGenerator := InjectedRoutesGenerator
