name := """api-play-command"""
organization := "co.com.ajac"

version := "1.0.2"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .settings(
    jacocoReportSettings := JacocoReportSettings(
      "Jacoco Coverage Report auth mobile app",
      None,
      JacocoThresholds(),
      Seq(JacocoReportFormats.XML, JacocoReportFormats.HTML, JacocoReportFormats.ScalaHTML),
      "utf-8"
    )
  )

scalaVersion := "2.12.4"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
  guice,
  "com.gitlab.ArielJose55"    % "api-command"        % "1.2.0",
  "net.aichler"               % "jupiter-interface"  % JupiterKeys.jupiterVersion.value  % Test
)

testOptions += Tests.Argument(jupiterTestFramework, "-v")
