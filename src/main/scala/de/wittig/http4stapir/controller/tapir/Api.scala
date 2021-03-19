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
import cats.implicits.catsSyntaxOptionId
import de.wittig.BuildInfo
import sttp.tapir.openapi.Info

object Api {
  import sttp.tapir.generic.auto._
  private def getAuth(implicit config: ServiceConfig) =
    endpoint.get
      .in(decodeJwt)
      .out(stringBody)

  def hello1(implicit config: ServiceConfig): Endpoint[(AuthUser, String), Unit, String, Any] =
    getAuth
      .in(query[String]("name"))
      .in("hello1")

  def hello2(implicit config: ServiceConfig): Endpoint[(AuthUser, String), Unit, String, Any] =
    getAuth
      .in(query[String]("name"))
      .in("hello2")

  def hello3(implicit config: ServiceConfig): Endpoint[(JsonInput, AuthUser), Unit, String, Any] =
    endpoint.post
      .in("hello3")
      .in(jsonBody[JsonInput])
      .in(decodeJwt)
      .out(stringBody)

}
