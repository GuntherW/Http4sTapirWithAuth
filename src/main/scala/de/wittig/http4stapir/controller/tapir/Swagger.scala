package de.wittig.http4stapir.controller.tapir

import de.wittig.BuildInfo
import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.controller.tapir.Api.{helloGet1, helloGet2, helloPost1}
import monix.eval.Task
import org.http4s.HttpRoutes
import sttp.tapir.openapi.Info
import sttp.tapir.docs.openapi._
import sttp.tapir.openapi.circe.yaml._
import sttp.tapir.swagger.http4s.SwaggerHttp4s

object Swagger {

  def route(implicit config: ServiceConfig): HttpRoutes[Task] = {

    val info = Info(
      BuildInfo.name,
      BuildInfo.version,
      Some("BuildTime: " + BuildInfo.builtAtString)
    )

    val endpoints = List(helloGet1, helloGet2, helloPost1)
    val yaml      = OpenAPIDocsInterpreter
      .toOpenAPI(endpoints, info)
      .toYaml

    new SwaggerHttp4s(yaml).routes[Task]
  }
}
