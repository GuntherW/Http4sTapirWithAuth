package de.wittig.http4stapir

import shapeless.Lazy.apply
import sttp.tapir.TapirAuth.bearer
import sttp.tapir.{Endpoint, endpoint, query, stringBody}
import sttp.tapir._
import sttp.tapir.docs.openapi._
import sttp.tapir.openapi.circe.yaml._
import sttp.tapir.swagger.http4s.SwaggerHttp4s

object MyEndPoints {

  val helloWorld: Endpoint[(String, String), Unit, String, Any] = endpoint.get
    .in("hello")
    .in(bearer[String])
    .in(query[String]("name"))
    .out(stringBody)

  val yaml: String = List(helloWorld)
    .toOpenAPI("Erster Versuch", "1.0")
    .toYaml

  val swagger = new SwaggerHttp4s(yaml)
}
