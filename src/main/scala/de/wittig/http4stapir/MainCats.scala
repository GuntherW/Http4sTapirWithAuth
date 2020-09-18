package de.wittig.http4stapir

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import de.wittig.http4stapir.controller.MyEndPoints
import org.http4s.HttpRoutes
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.kleisli._
import sttp.tapir.server.http4s._

import scala.concurrent.ExecutionContext.Implicits.global

object MainCats extends IOApp {

  private val helloWorldRoutes: HttpRoutes[IO] = MyEndPoints.helloWorld
    .toRoutes(name => IO(s"Hello, $name!".asRight[Unit]))

  private val swaggerRoute: HttpRoutes[IO] = MyEndPoints.swagger.routes[IO]

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](global)
      .bindHttp(8081, "0.0.0.0")
      .withHttpApp(Router("/" -> (helloWorldRoutes <+> swaggerRoute)).orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
