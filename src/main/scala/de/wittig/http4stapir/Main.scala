package de.wittig.http4stapir

import cats.effect.ExitCode
import de.wittig.http4stapir.controller.routes.HelloRoutes
import monix.eval.{Task, TaskApp}
import org.http4s.server.blaze.BlazeServerBuilder

case class ServiceConfig(tokenPrefix: String)

object Main extends TaskApp {

  private implicit val config: ServiceConfig = ServiceConfig("foobar")

  def run(args: List[String]): Task[ExitCode] =
    BlazeServerBuilder[Task](scheduler)
      .bindHttp(8084, "0.0.0.0")
      .withHttpApp(HelloRoutes.router)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
