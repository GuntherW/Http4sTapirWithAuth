package de.wittig.http4stapir.controller.api

import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.auth.AuthUser
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import monix.eval.Task
import org.http4s.HttpRoutes
import sttp.tapir.EndpointInput.Auth
import sttp.tapir._
import sttp.tapir.docs.openapi._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.openapi.circe.yaml._
import sttp.tapir.swagger.http4s.SwaggerHttp4s

final class GuntherException(message: String, cause: Option[Throwable] = None) extends RuntimeException(message, cause.orNull)
final case class JsonInput(name: String)

object JsonInput {
  implicit val codec: Codec.AsObject[JsonInput] = deriveCodec[JsonInput]
}

object Tapir {
  private def hello(implicit serviceConfig: ServiceConfig) =
    endpoint.get
      .in(query[String]("name"))
      .in(myFancyJwtAuth)
      .out(stringBody)

  private def myFancyJwtAuth(implicit serviceConfig: ServiceConfig): Auth.Http[AuthUser] =
    auth
      .bearer[String]
      .mapDecode[AuthUser] { headerValue =>
        if (headerValue.startsWith(serviceConfig.tokenPrefix)) {
          DecodeResult.Value(AuthUser(headerValue))
        } else {
          DecodeResult.Error(headerValue, new GuntherException("Oooops, nice try :)"))
        }
      }(_.name)

  def hello1(implicit serviceConfig: ServiceConfig): Endpoint[(String, AuthUser), Unit, String, Any] = hello.in("hello1")

  def hello2(implicit serviceConfig: ServiceConfig): Endpoint[(String, AuthUser), Unit, String, Any] = hello.in("hello2")

  def hello3(implicit serviceConfig: ServiceConfig): Endpoint[(JsonInput, AuthUser), Unit, String, Any] =
    endpoint.post.in("hello3").in(jsonBody[JsonInput]).in(myFancyJwtAuth).out(stringBody)

  def yaml(implicit serviceConfig: ServiceConfig): String =
    List(hello1, hello2, hello3)
      .toOpenAPI("Erster Versuch", "1.0")
      .toYaml

  def swaggerRoute(implicit serviceConfig: ServiceConfig): HttpRoutes[Task] = new SwaggerHttp4s(yaml).routes[Task]
}
