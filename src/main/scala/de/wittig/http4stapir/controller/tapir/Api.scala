package de.wittig.http4stapir.controller.tapir

import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.controller.auth.AuthUser
import de.wittig.http4stapir.model.JsonInput
import monix.eval.Task
import org.http4s.HttpRoutes
import sttp.tapir._
import sttp.tapir.docs.openapi._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.openapi.circe.yaml._
import sttp.tapir.swagger.http4s.SwaggerHttp4s
import JwtDecoder._

object Api {

  private def hello(implicit serviceConfig: ServiceConfig) =
    endpoint.get
      .in(query[String]("name"))
      .in(decodeJwt)
      .out(stringBody)

  def hello1(implicit serviceConfig: ServiceConfig): Endpoint[(String, AuthUser), Unit, String, Any] =
    hello
      .in("hello1")

  def hello2(implicit serviceConfig: ServiceConfig): Endpoint[(String, AuthUser), Unit, String, Any] =
    hello
      .in("hello2")

  def hello3(implicit serviceConfig: ServiceConfig): Endpoint[(JsonInput, AuthUser), Unit, String, Any] =
    endpoint.post
      .in("hello3")
      .in(jsonBody[JsonInput])
      .in(decodeJwt)
      .out(stringBody)

  def swaggerRoute(implicit serviceConfig: ServiceConfig): HttpRoutes[Task] = {

    val yaml = List(
      hello1,
      hello2,
      hello3
    )
      .toOpenAPI("Erster Versuch", "1.0")
      .toYaml

    new SwaggerHttp4s(yaml).routes[Task]
  }
}
