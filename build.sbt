val Http4sVersion  = "0.21.13"
val CirceVersion   = "0.13.0"
val LogbackVersion = "1.2.3"
val catsVersion    = "2.3.0"
val tapirVersion   = "0.17.0-M11"
val sttpModel      = "1.2.0-RC6"
val sttpShared     = "1.0.0-RC9"
val monixVersion   = "3.3.0"

lazy val root = (project in file("."))
  .settings(
    organization := "de.wittig",
    name := "http4stapir",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.4",
    libraryDependencies ++= Seq(
      "org.http4s"                   %% "http4s-blaze-server"      % Http4sVersion,
      "org.http4s"                   %% "http4s-blaze-client"      % Http4sVersion,
      "org.http4s"                   %% "http4s-circe"             % Http4sVersion,
      "org.http4s"                   %% "http4s-dsl"               % Http4sVersion,
      "io.circe"                     %% "circe-generic"            % CirceVersion,
      "ch.qos.logback"                % "logback-classic"          % LogbackVersion,
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
