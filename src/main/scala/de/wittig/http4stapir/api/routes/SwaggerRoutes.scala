package de.wittig.http4stapir.api.routes

import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.api.tapir.HelloEndpoints.{helloAuthGet2, helloGet1, helloGet2, helloGet3, helloPost1}
import cats.effect.IO
import org.http4s.HttpRoutes
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.openapi.Info
import sttp.tapir.openapi.circe.yaml.RichOpenAPI
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.swagger.SwaggerUI

object SwaggerRoutes {

  private val info = Info(
    "BuildInfo.name",
    "BuildInfo.version",
    Some("BuildTime: " + "BuildInfo.builtAtString")
  )

  def route(implicit config: ServiceConfig): HttpRoutes[IO] = {
    val endpoints = List(
      helloGet1,
      helloGet2,
      helloGet3,
      helloAuthGet2,
      helloPost1
    )

    val yaml = OpenAPIDocsInterpreter()
      .toOpenAPI(endpoints, info)
      .toYaml

    Http4sServerInterpreter[IO]().toRoutes(SwaggerUI[IO](yaml))
  }
}
