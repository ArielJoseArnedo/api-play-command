name := """api-play-command"""
organization := "co.com.ajac"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)

javacOptions ++= Seq("-source", "17", "-target", "17")

scalaVersion := "2.13.6"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
  guice,
  "com.github.ArielJoseArnedo"    % "api-command"        % "3.2.0",
  "net.aichler"                   % "jupiter-interface"  % JupiterKeys.jupiterVersion.value  % Test
)

testOptions += Tests.Argument(jupiterTestFramework, "-v")
