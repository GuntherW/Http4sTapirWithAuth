package de.wittig.http4stapir.api.routes

import de.wittig.http4stapir.ServiceConfig
import cats.effect.IO
import org.http4s.implicits.{http4sKleisliResponseSyntaxOptionT}
import org.http4s.{Header, Headers, Method, Request, Status}
import org.scalatest.funsuite.AnyFunSuite
import org.typelevel.ci.CIString
import cats.effect.unsafe.implicits.*
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.io.*
import org.http4s.implicits.*

class HelloRoutesTest extends AnyFunSuite {

  private implicit val config: ServiceConfig = ServiceConfig("foobar")
  private val validHeader: Headers           = Headers(Header.Raw(CIString("Authorization"), "Bearer foobar"))

  test("ok") {
    val request  = Request[IO](Method.GET, uri"/hello1?name=Gunther", headers = validHeader)
    val response = HelloRoutes.helloGet1Routes.orNotFound.run(request)
    val body     = response.flatMap(_.as[String]).unsafeRunSync()

    assert(body === "Hello, Gunther! AuthUser: AuthUser(foobar).  config: foobar")
  }

  test("notFound") {

    val request  = Request[IO](Method.GET, uri"/hello2?name=Gunther", headers = validHeader)
    val response = HelloRoutes.helloGet1Routes.orNotFound.run(request)
    val erg      = response.unsafeRunSync()

    assert(erg.status === Status.NotFound)
  }
}
