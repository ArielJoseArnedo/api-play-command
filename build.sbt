name := """api-play-command"""
organization := "co.com.ajac"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)

scalaVersion := "2.13.6"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
  guice,
  "com.github.ArielJoseArnedo"    % "api-command"        % "3.1.1",
  "net.aichler"                   % "jupiter-interface"  % JupiterKeys.jupiterVersion.value  % Test
)

testOptions += Tests.Argument(jupiterTestFramework, "-v")
