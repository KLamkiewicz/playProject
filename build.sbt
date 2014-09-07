name := """SimpleCRM"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final",
  "be.objectify" %% "deadbolt-java" % "2.3.0-RC1"	
)


resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns)
