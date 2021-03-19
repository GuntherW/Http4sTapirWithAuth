package de.wittig.http4stapir.controller.tapir

import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.controller.tapir.JwtDecoder._
import de.wittig.http4stapir.model.{AuthUser, JsonInput, JsonOutput}
import sttp.model.StatusCode
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody
import io.circe.generic.auto._
object Api {

  sealed trait ErrorInfo
  case class NotFound(what: String)          extends ErrorInfo
  case class Unauthorized(realm: String)     extends ErrorInfo
  case class Unknown(code: Int, msg: String) extends ErrorInfo
  case object NoContent                      extends ErrorInfo

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
      .in(query[String]("name"))
      .in("hello1")
      .out(stringBody)

  def helloGet2(implicit config: ServiceConfig): Endpoint[(AuthUser, String), Unit, String, Any] =
    getAuth.get
      .in(query[String]("name"))
      .in("hello2")
      .out(stringBody)

  def helloGet3(implicit config: ServiceConfig): Endpoint[(AuthUser, String), Unit, JsonOutput, Any] =
    getAuth.get
      .in(query[String]("name"))
      .in("hello3")
      .out(jsonBody[JsonOutput])

  def helloGet4(implicit config: ServiceConfig): Endpoint[(AuthUser, String), ErrorInfo, JsonOutput, Any] =
    getAuth.get
      .in(query[String]("name"))
      .in("hello4")
      .errorOut(fehler)
      .out(jsonBody[JsonOutput])

  def helloPost1(implicit config: ServiceConfig): Endpoint[(AuthUser, JsonInput), Unit, JsonOutput, Any] =
    getAuth.post
      .in("hello1")
      .in(jsonBody[JsonInput])
      .out(jsonBody[JsonOutput])

}
