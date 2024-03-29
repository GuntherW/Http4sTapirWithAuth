lazy val root = (project in file("."))
  .settings(
    organization := "de.wittig",
    name         := "http4stapir",
    version      := "0.0.1-SNAPSHOT",
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
      Library.catsEffect,
      Library.tapirHttp4s,
      Library.tapirCore,
      Library.tapirOpenApiDocs,
      Library.tapirOpenApiCirce,
      Library.tapirSwagger,
      Library.tapirRedoc,
      Library.tapirCirce,
      Library.scalaTest % Test
    )
  )

scalacOptions ++=
  Seq(
    "-feature",
    "-language:higherKinds",
    "-deprecation",
    "-source:future",  // für better-monadic-for, das es für Scala3 nicht mehr gibt
    "-Ykind-projector" // für KindProjector
  )

Global / onChangedBuildSource := ReloadOnSourceChanges
