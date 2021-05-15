import sbt._

object Version {
  val catsVersion    = "2.6.1"
  val circeVersion   = "0.13.0"
  val http4sVersion  = "0.21.22"
  val logbackVersion = "1.2.3"
  val monixVersion   = "3.4.0"
  val sttpModel      = "1.4.7"
  val sttpShared     = "1.2.4"
  val tapirVersion   = "0.17.19"

  // Test
  final val scalaTest = "3.2.9"

  // Compiler Plugins
  final val betterMonadicFor = "0.3.1"
  final val kindProjector    = "0.12.0"
}

object Library {
  val cats              = "org.typelevel"                %% "cats-core"                % Version.catsVersion
  val circe             = "io.circe"                     %% "circe-generic"            % Version.circeVersion
  val http4sBlazeServer = "org.http4s"                   %% "http4s-blaze-server"      % Version.http4sVersion
  val http4sBlazeClient = "org.http4s"                   %% "http4s-blaze-client"      % Version.http4sVersion
  val http4sCirce       = "org.http4s"                   %% "http4s-circe"             % Version.http4sVersion
  val http4sDsl         = "org.http4s"                   %% "http4s-dsl"               % Version.http4sVersion
  val logback           = "ch.qos.logback"                % "logback-classic"          % Version.logbackVersion
  val monix             = "io.monix"                     %% "monix"                    % Version.monixVersion
  val sttpModel         = "com.softwaremill.sttp.model"  %% "core"                     % Version.sttpModel
  val sttpShared        = "com.softwaremill.sttp.shared" %% "core"                     % Version.sttpShared
  val tapirHttp4s       = "com.softwaremill.sttp.tapir"  %% "tapir-http4s-server"      % Version.tapirVersion
  val tapirCore         = "com.softwaremill.sttp.tapir"  %% "tapir-core"               % Version.tapirVersion
  val tapirOpenApiDocs  = "com.softwaremill.sttp.tapir"  %% "tapir-openapi-docs"       % Version.tapirVersion
  val tapirOpenApiCirce = "com.softwaremill.sttp.tapir"  %% "tapir-openapi-circe-yaml" % Version.tapirVersion
  val tapirSwagger      = "com.softwaremill.sttp.tapir"  %% "tapir-swagger-ui-http4s"  % Version.tapirVersion
  val tapirRedoc        = "com.softwaremill.sttp.tapir"  %% "tapir-redoc-http4s"       % Version.tapirVersion
  val tapirCirce        = "com.softwaremill.sttp.tapir"  %% "tapir-json-circe"         % Version.tapirVersion

  // Test
  val scalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest

  // Compiler Plugin
  val betterMonadicFor = "com.olegpy"    %% "better-monadic-for" % Version.betterMonadicFor
  val kindProjector    = "org.typelevel" %% "kind-projector"     % Version.kindProjector cross CrossVersion.full
}
