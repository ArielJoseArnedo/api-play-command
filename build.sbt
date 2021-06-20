name := """api-play-command"""
organization := "co.com.ajac"

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "co.com.ajac",
    buildInfoOptions += BuildInfoOption.ToJson,
    jacocoReportSettings := JacocoReportSettings(
      "Jacoco Coverage Report auth mobile app",
      None,
      JacocoThresholds(),
      Seq(JacocoReportFormats.XML, JacocoReportFormats.HTML, JacocoReportFormats.ScalaHTML),
      "utf-8"
    )
  )

scalaVersion := "2.13.5"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
  guice,
  "com.gitlab.ArielJose55"    % "api-command"        % "1.2.0",
  "net.aichler"               % "jupiter-interface"  % JupiterKeys.jupiterVersion.value  % Test
)

testOptions += Tests.Argument(jupiterTestFramework, "-v")
