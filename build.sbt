name := """api-play-command"""
organization := "co.com.ajac"

version := "1.2.1"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)

scalaVersion := "2.13.6"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
  guice,
  "com.gitlab.ArielJose55"    % "api-command"        % "1.2.1",
  "net.aichler"               % "jupiter-interface"  % JupiterKeys.jupiterVersion.value  % Test
)

testOptions += Tests.Argument(jupiterTestFramework, "-v")
