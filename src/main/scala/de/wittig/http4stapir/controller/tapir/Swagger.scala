package de.wittig.http4stapir.controller.tapir

import de.wittig.BuildInfo
import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.controller.tapir.Api.{hello1, hello2, hello3}
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

    val endpoints = List(hello1, hello2, hello1)
    val yaml      = OpenAPIDocsInterpreter
      .toOpenAPI(endpoints, info)
      .toYaml

    new SwaggerHttp4s(yaml).routes[Task]
  }
}
