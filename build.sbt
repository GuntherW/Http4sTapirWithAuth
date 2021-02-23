val catsVersion    = "2.4.2"
val circeVersion   = "0.13.0"
val http4sVersion  = "0.21.19"
val logbackVersion = "1.2.3"
val monixVersion   = "3.3.0"
val sttpModel      = "1.3.3"
val sttpShared     = "1.1.1"
val tapirVersion   = "0.17.12"

lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "de.wittig",
    buildInfoOptions += BuildInfoOption.BuildTime,
    organization := "de.wittig",
    name := "http4stapir",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.5",
    libraryDependencies ++= Seq(
      "org.http4s"                   %% "http4s-blaze-server"      % http4sVersion,
      "org.http4s"                   %% "http4s-blaze-client"      % http4sVersion,
      "org.http4s"                   %% "http4s-circe"             % http4sVersion,
      "org.http4s"                   %% "http4s-dsl"               % http4sVersion,
      "io.circe"                     %% "circe-generic"            % circeVersion,
      "ch.qos.logback"                % "logback-classic"          % logbackVersion,
      "com.softwaremill.sttp.model"  %% "core"                     % sttpModel,
      "com.softwaremill.sttp.shared" %% "core"                     % sttpShared,
      "org.typelevel"                %% "cats-core"                % catsVersion,
      "com.softwaremill.sttp.tapir"  %% "tapir-http4s-server"      % tapirVersion,
      "com.softwaremill.sttp.tapir"  %% "tapir-core"               % tapirVersion,
      "com.softwaremill.sttp.tapir"  %% "tapir-openapi-docs"       % tapirVersion,
      "com.softwaremill.sttp.tapir"  %% "tapir-openapi-circe-yaml" % tapirVersion,
      "com.softwaremill.sttp.tapir"  %% "tapir-swagger-ui-http4s"  % tapirVersion,
      "com.softwaremill.sttp.tapir"  %% "tapir-redoc-http4s"       % tapirVersion,
      "com.softwaremill.sttp.tapir"  %% "tapir-json-circe"         % tapirVersion,
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
