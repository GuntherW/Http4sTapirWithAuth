package de.wittig.http4stapir.controller.routes

import cats.implicits.toSemigroupKOps
import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.controller.tapir.Api
import de.wittig.http4stapir.service.HelloService
import monix.eval.Task
import org.http4s.HttpRoutes
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.Router
import sttp.tapir.server.http4s.Http4sServerInterpreter

object HelloRoutes {

  private def helloGet1Routes(implicit config: ServiceConfig): HttpRoutes[Task] = Http4sServerInterpreter.toRoutes(Api.helloGet1) { case (authUser, n) =>
    HelloService.hellosSimple(n, authUser)
  }

  private def helloGet2Routes(implicit config: ServiceConfig): HttpRoutes[Task] = Http4sServerInterpreter.toRoutes(Api.helloGet2) { case (authUser, n) =>
    HelloService.helloReader(n, authUser).run(config)
  }

  private def helloGet3Routes(implicit config: ServiceConfig): HttpRoutes[Task] = Http4sServerInterpreter.toRoutes(Api.helloGet3) { case (authUser, n) =>
    HelloService.helloJsonOutput(n, authUser)
  }

  // https://tapir.softwaremill.com/en/latest/server/logic.html
  private def helloAuth1Routes(implicit config: ServiceConfig): HttpRoutes[Task] =
    Http4sServerInterpreter.toRoutes(
      Api.helloAuthGet1.serverLogic { case (authUser, n) =>
        HelloService.helloJsonOutput(n, authUser)
      }
    )

  private def helloAuth2Routes(implicit config: ServiceConfig): HttpRoutes[Task] = {
    ???
  }

  private def helloPost1Routes(implicit config: ServiceConfig): HttpRoutes[Task] = Http4sServerInterpreter.toRoutes(Api.helloPost1) {
    case (authUser, jsonInput) =>
      HelloService.helloJsonOutput(jsonInput.name, authUser)
  }

  def router(implicit config: ServiceConfig) = Router(
    "/" -> (
      helloGet1Routes <+>
        helloGet2Routes <+>
        helloGet3Routes <+>
        helloAuth1Routes <+>
        helloPost1Routes <+>
        SwaggerRoutes.route
    )
  ).orNotFound
}
