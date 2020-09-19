package de.wittig.http4stapir.controller.api

import sttp.tapir._
import sttp.tapir.docs.openapi._
import sttp.tapir.openapi.circe.yaml._
import sttp.tapir.swagger.http4s.SwaggerHttp4s

object Tapir {

  val helloWorld: Endpoint[String, Unit, String, Any] = endpoint.get
    .in("hello")
    .in(query[String]("name"))
    .out(stringBody)

  val yaml: String = List(helloWorld)
    .toOpenAPI("Erster Versuch", "1.0")
    .toYaml

  val swagger = new SwaggerHttp4s(yaml)
}
