package de.wittig.http4stapir.api.tapir

import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.api.*
import de.wittig.http4stapir.api.JwtDecoder.*
import de.wittig.http4stapir.model.{AuthUser, ErrorInfo, JsonInput, JsonOutput, NoContent, NotFound, Unauthorized, Unknown}
import sttp.model.StatusCode
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.circe.jsonBody
import io.circe.generic.auto.*
import cats.effect.IO
import sttp.tapir.server.PartialServerEndpoint

object HelloEndpoints {

  private val fehler = oneOf[ErrorInfo](
    oneOfMapping(StatusCode.NotFound, jsonBody[NotFound].description("not found")),
    oneOfMapping(StatusCode.Unauthorized, jsonBody[Unauthorized].description("unauthorized")),
    oneOfMapping(StatusCode.NoContent, emptyOutput.map(_ => NoContent)(_ => ())),
    oneOfDefaultMapping(jsonBody[Unknown].description("unknown"))
  )

  private def getAuth(implicit config: ServiceConfig) =
    endpoint
      .in(decodeJwt)

  def helloGet1(implicit config: ServiceConfig): Endpoint[(AuthUser, String), Unit, String, Any] =
    getAuth.get
      .in("hello1")
      .in(query[String]("name"))
      .out(stringBody)

  def helloGet2(implicit config: ServiceConfig): Endpoint[(AuthUser, String), Unit, String, Any] =
    getAuth.get
      .in("hello2")
      .in(query[String]("name"))
      .out(stringBody)

  def helloGet3(implicit config: ServiceConfig): Endpoint[(AuthUser, String), ErrorInfo, JsonOutput, Any] =
    getAuth.get
      .in("hello3")
      .in(query[String]("name"))
      .errorOut(fehler)
      .out(jsonBody[JsonOutput])

  def helloAuthGet1(implicit config: ServiceConfig): PartialServerEndpoint[String, AuthUser, String, ErrorInfo, JsonOutput, Any, IO] =
    endpoint
      .in("helloAuth1")
      .in(auth.bearer[String]())
      .errorOut(fehler)
      .serverLogicForCurrent(authFn)
      .in(query[String]("name"))
      .out(jsonBody[JsonOutput])

  def helloAuthGet2(implicit config: ServiceConfig): Endpoint[(String, String), ErrorInfo, JsonOutput, Any] =
    endpoint
      .in("helloAuth2")
      .in(auth.bearer[String]())
      .in(query[String]("name"))
      .errorOut(fehler)
      .out(jsonBody[JsonOutput])

  def helloPost1(implicit config: ServiceConfig): Endpoint[(AuthUser, JsonInput), ErrorInfo, JsonOutput, Any] =
    getAuth.post
      .in("hello1")
      .in(jsonBody[JsonInput])
      .errorOut(fehler)
      .out(jsonBody[JsonOutput])

}
