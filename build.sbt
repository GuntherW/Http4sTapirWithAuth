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
      Library.tapirSwagger cross CrossVersion.for3Use2_13,
      Library.tapirRedoc cross CrossVersion.for3Use2_13,
      Library.tapirCirce,
      Library.monix,
      Library.scalaTest % Test
    )
    //addCompilerPlugin(Library.kindProjector)
  )

scalacOptions ++=
  Seq(
    "-feature",
    "-language:higherKinds",
    "-deprecation"
  ) ++ {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((3, _)) =>
        Seq(
          "-source:future",               // für better-monadic-for, das es für Scala3 nicht mehr gibt
          "-Ykind-projector:underscores", // für KindProjector
          "-Ykind-projector"
        )
      case _            =>
        Seq(
          "-Wvalue-discard",
          "-Wunused:imports,privates,locals,explicits,implicits,params",
          "-language:_",
          "-encoding",
          "UTF-8",
          // Emit warning for usages of features that should be imported explicitly
          "-feature",
          // Emit warning for usages of deprecated APIs
          "-unchecked",
          "-Ywarn-value-discard",
          "-Ymacro-annotations" // scala 2.13.0
          //          "-Xsource:3",
          //          "-P:kind-projector:underscore-placeholders"
        )
    }
  }
Global / onChangedBuildSource := ReloadOnSourceChanges
