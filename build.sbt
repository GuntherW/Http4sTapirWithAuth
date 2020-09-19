val Http4sVersion  = "0.21.7"
val CirceVersion   = "0.13.0"
val Specs2Version  = "4.10.3"
val LogbackVersion = "1.2.3"
val catsVersion    = "2.2.0"
val tapirVersion   = "0.17.0-M1"
val sttp           = "3.0.0-RC4"
val sttpModel      = "1.2.0-RC3"
val sttpShared     = "1.0.0-RC5"
val monixVersion   = "3.2.2"

lazy val root = (project in file("."))
  .settings(
    organization := "de.wittig",
    name := "http4stapir",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.3",
    libraryDependencies ++= Seq(
      "org.http4s"                   %% "http4s-blaze-server"      % Http4sVersion,
      "org.http4s"                   %% "http4s-blaze-client"      % Http4sVersion,
      "org.http4s"                   %% "http4s-circe"             % Http4sVersion,
      "org.http4s"                   %% "http4s-dsl"               % Http4sVersion,
      "io.circe"                     %% "circe-generic"            % CirceVersion,
      "ch.qos.logback"                % "logback-classic"          % LogbackVersion,
      "com.softwaremill.sttp.model"  %% "core"                     % sttpModel,
      "com.softwaremill.sttp.shared" %% "core"                     % sttpShared,
      "com.softwaremill.sttp.tapir"  %% "tapir-http4s-server"      % tapirVersion,
      "org.typelevel"                %% "cats-core"                % catsVersion,
      "com.softwaremill.sttp.tapir"  %% "tapir-core"               % tapirVersion,
      "com.softwaremill.sttp.tapir"  %% "tapir-openapi-docs"       % tapirVersion,
      "com.softwaremill.sttp.tapir"  %% "tapir-openapi-circe-yaml" % tapirVersion,
      "com.softwaremill.sttp.tapir"  %% "tapir-swagger-ui-http4s"  % tapirVersion,
      "com.softwaremill.sttp.tapir"  %% "tapir-redoc-http4s"       % tapirVersion,
      "io.monix"                     %% "monix"                    % monixVersion
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.10.3"),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1")
  )

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Xfatal-warnings"
)
