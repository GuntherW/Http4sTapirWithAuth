package de.wittig.http4stapir.controller.api

import sttp.tapir._
import sttp.tapir.docs.openapi._
import sttp.tapir.openapi.circe.yaml._
import sttp.tapir.swagger.http4s.SwaggerHttp4s

object Tapir {

  val hello1: Endpoint[String, Unit, String, Any] = endpoint.get
    .in("hello1")
    .in(query[String]("name"))
    .out(stringBody)

  val hello2: Endpoint[String, Unit, String, Any] = endpoint.get
    .in("hello2")
    .in(query[String]("name"))
    .out(stringBody)

  val hello3: Endpoint[String, Unit, String, Any] = endpoint.get
    .in("hello3")
    .in(query[String]("name"))
    .out(stringBody)

  val yaml: String = List(hello1, hello2, hello3)
    .toOpenAPI("Erster Versuch", "1.0")
    .toYaml

  val swagger = new SwaggerHttp4s(yaml)
}
