package de.wittig.http4stapir.api.routes

import de.wittig.BuildInfo
import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.api.tapir.HelloEndpoints.{helloAuthGet2, helloGet1, helloGet2, helloGet3, helloPost1}
import monix.eval.Task
import org.http4s.HttpRoutes
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.openapi.Info
import sttp.tapir.openapi.circe.yaml.RichOpenAPI
import sttp.tapir.swagger.http4s.SwaggerHttp4s

object SwaggerRoutes {

  private val info = Info(
    BuildInfo.name,
    BuildInfo.version,
    Some("BuildTime: " + BuildInfo.builtAtString)
  )

  def route(implicit config: ServiceConfig): HttpRoutes[Task] = {
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

    new SwaggerHttp4s(yaml).routes[Task]
  }
}
