package de.wittig.http4stapir
import cats.effect.unsafe.IORuntime

import cats.effect.ExitCode
import de.wittig.http4stapir.api.routes.HelloRoutes
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import cats.effect.*
case class ServiceConfig(tokenPrefix: String)

object Main extends IOApp {

  private implicit val config: ServiceConfig = ServiceConfig("foobar")

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](IORuntime.global.compute)
      .bindHttp(8084, "0.0.0.0")
      .withHttpApp(HelloRoutes.router.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
