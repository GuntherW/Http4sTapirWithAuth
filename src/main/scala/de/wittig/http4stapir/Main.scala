package de.wittig.http4stapir

import cats.effect.ExitCode
import cats.implicits._
import de.wittig.http4stapir.controller.HelloController
import de.wittig.http4stapir.controller.tapir.{Api, Swagger}
import monix.eval.{Task, TaskApp}
import org.http4s.HttpRoutes
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.kleisli._
import sttp.tapir.server.http4s._

case class ServiceConfig(tokenPrefix: String)

object Main extends TaskApp {

  private implicit val config: ServiceConfig = ServiceConfig("foobar")
//  private implicit val customServerOption: Http4sServerOptions[Task] = CustomServerOptions.customServerOptions

  private val helloGet1Routes: HttpRoutes[Task] = Http4sServerInterpreter.toRoutes(Api.helloGet1) { case (authUser, n) =>
    HelloController.helloGet1(n, authUser, config)
  }

  private val helloGet2Routes: HttpRoutes[Task] = Http4sServerInterpreter.toRoutes(Api.helloGet2) { case (authUser, n) =>
    HelloController.helloGet2(n, authUser).run(config)
  }

  private val helloGet3Routes: HttpRoutes[Task] = Http4sServerInterpreter.toRoutes(Api.helloGet3) { case (authUser, n) =>
    HelloController.helloGet3(n, authUser, config)
  }

  private val helloGet4Routes: HttpRoutes[Task] = Http4sServerInterpreter.toRoutes(Api.helloGet4) { case (authUser, n) =>
    HelloController.helloGet4(n, authUser, config)
  }

  private val helloPost1Routes: HttpRoutes[Task] = Http4sServerInterpreter.toRoutes(Api.helloPost1) { case (authUser, jsonInput) =>
    HelloController.helloPost1(jsonInput.name, authUser, config)
  }

  def run(args: List[String]): Task[ExitCode] =
    BlazeServerBuilder[Task](scheduler)
      .bindHttp(8084, "0.0.0.0")
      .withHttpApp(
        Router(
          "/" -> (
            helloGet1Routes <+>
              helloGet2Routes <+>
              helloGet3Routes <+>
              helloGet4Routes <+>
              helloPost1Routes <+>
              Swagger.route
          )
        ).orNotFound
      )
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
