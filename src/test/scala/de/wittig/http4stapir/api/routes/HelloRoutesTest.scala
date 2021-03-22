package de.wittig.http4stapir.api.routes

import de.wittig.http4stapir.ServiceConfig
import monix.eval.Task
import monix.execution.Scheduler
import org.http4s.implicits.{http4sKleisliResponseSyntaxOptionT, http4sLiteralsSyntax}
import org.http4s.{Header, Headers, Method, Request, Status}
import org.scalatest.funsuite.AnyFunSuite

class HelloRoutesTest extends AnyFunSuite {

  private implicit val config: ServiceConfig = ServiceConfig("foobar")
  private implicit val scheduler: Scheduler  = Scheduler.global
  private val validHeader                    = Headers.of(Header("Authorization", "Bearer foobar"))

  test("ok") {

    val request  = Request[Task](Method.GET, uri"/hello1?name=Gunther", headers = validHeader)
    val response = HelloRoutes.helloGet1Routes.orNotFound.run(request)
    val body     = response.flatMap(_.as[String]).runSyncUnsafe()

    assert(body === "Hello, Gunther! AuthUser: AuthUser(foobar).  config: foobar")
  }

  test("notFound") {

    val request  = Request[Task](Method.GET, uri"/hello2?name=Gunther", headers = validHeader)
    val response = HelloRoutes.helloGet1Routes.orNotFound.run(request)
    val erg      = response.runSyncUnsafe()

    assert(erg.status === Status.NotFound)
  }
}
