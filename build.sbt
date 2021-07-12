lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "de.wittig",
    buildInfoOptions += BuildInfoOption.BuildTime,
    organization := "de.wittig",
    name := "http4stapir",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := Version.scala,
    libraryDependencies ++= Seq(
      Library.http4sBlazeServer,
      Library.http4sBlazeClient,
      Library.http4sCirce,
      Library.http4sDsl,
      Library.circe,
      Library.logback,
      Library.sttpModel,
      Library.sttpShared,
      Library.cats,
      Library.tapirHttp4s,
      Library.tapirCore,
      Library.tapirOpenApiDocs,
      Library.tapirOpenApiCirce,
      Library.tapirSwagger,
      Library.tapirRedoc,
      Library.tapirCirce,
      Library.monix,
      Library.scalaTest % Test
    ),
    addCompilerPlugin(Library.kindProjector)
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
Global / onChangedBuildSource := ReloadOnSourceChanges
