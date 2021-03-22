package de.wittig.http4stapir.api.tapir

import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.api._
import de.wittig.http4stapir.api.JwtDecoder._
import de.wittig.http4stapir.model.{AuthUser, ErrorInfo, JsonInput, JsonOutput, NoContent, NotFound, Unauthorized, Unknown}
import sttp.model.StatusCode
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody
import io.circe.generic.auto._
import monix.eval.Task
import sttp.tapir.server.PartialServerEndpoint

object HelloEndpoints {

  val fehler = oneOf[ErrorInfo](
    statusMapping(StatusCode.NotFound, jsonBody[NotFound].description("not found")),
    statusMapping(StatusCode.Unauthorized, jsonBody[Unauthorized].description("unauthorized")),
    statusMapping(StatusCode.NoContent, emptyOutput.map(_ => NoContent)(_ => ())),
    statusDefaultMapping(jsonBody[Unknown].description("unknown"))
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

  def helloAuthGet1(implicit config: ServiceConfig): PartialServerEndpoint[AuthUser, String, ErrorInfo, JsonOutput, Any, Task] =
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
