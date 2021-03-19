package de.wittig.http4stapir.controller.tapir

import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.controller.auth.AuthUser
import de.wittig.http4stapir.controller.tapir.JwtDecoder._
import de.wittig.http4stapir.model.{JsonInput, JsonOutput}
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody

object Api {
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

  def helloPost1(implicit config: ServiceConfig): Endpoint[(AuthUser, JsonInput), Unit, JsonOutput, Any] =
    getAuth.post
      .in("hello1")
      .in(jsonBody[JsonInput])
      .out(jsonBody[JsonOutput])

}
