package de.wittig.http4stapir.controller.api

import monix.eval.Task
import sttp.tapir._
import sttp.tapir.docs.openapi._
import sttp.tapir.openapi.circe.yaml._
import sttp.tapir.swagger.http4s.SwaggerHttp4s

object Tapir {

  private val hello = endpoint.get
    .in(query[String]("name"))
    .out(stringBody)

  val hello1: Endpoint[String, Unit, String, Any] = hello.in("hello1")

  val hello2: Endpoint[String, Unit, String, Any] = hello.in("hello2")

  val hello3: Endpoint[String, Unit, String, Any] = hello.in("hello3")

  val yaml: String = List(hello1, hello2, hello3)
    .toOpenAPI("Erster Versuch", "1.0")
    .toYaml

  val swaggerRoute = new SwaggerHttp4s(yaml).routes[Task]
}
