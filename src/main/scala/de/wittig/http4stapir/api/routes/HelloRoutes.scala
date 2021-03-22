package de.wittig.http4stapir.api.routes

import cats.implicits.toSemigroupKOps
import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.api.JwtDecoder
import de.wittig.http4stapir.api.tapir.HelloEndpoints
import de.wittig.http4stapir.service.HelloService
import monix.eval.Task
import org.http4s.HttpRoutes
import org.http4s.server.Router
import sttp.tapir.server.http4s.Http4sServerInterpreter

object HelloRoutes {

  private[routes] def helloGet1Routes(implicit config: ServiceConfig): HttpRoutes[Task] = Http4sServerInterpreter.toRoutes(HelloEndpoints.helloGet1) {
    case (authUser, n) =>
      HelloService.hellosSimple(n, authUser)
  }

  private[routes] def helloGet2Routes(implicit config: ServiceConfig): HttpRoutes[Task] = Http4sServerInterpreter.toRoutes(HelloEndpoints.helloGet2) {
    case (authUser, n) =>
      HelloService.helloReader(n, authUser).run(config)
  }

  private[routes] def helloGet3Routes(implicit config: ServiceConfig): HttpRoutes[Task] = Http4sServerInterpreter.toRoutes(HelloEndpoints.helloGet3) {
    case (authUser, n) =>
      HelloService.helloJsonOutput(n, authUser)
  }

  // https://tapir.softwaremill.com/en/latest/server/logic.html
  private[routes] def helloAuth1Routes(implicit config: ServiceConfig): HttpRoutes[Task] =
    Http4sServerInterpreter.toRoutes(
      HelloEndpoints.helloAuthGet1.serverLogic { case (authUser, n) =>
        HelloService.helloJsonOutput(n, authUser)
      }
    )

  private[routes] def helloAuth2Routes(implicit config: ServiceConfig): HttpRoutes[Task] =
    Http4sServerInterpreter.toRoutes(
      HelloEndpoints.helloAuthGet2
        .serverLogicPart(JwtDecoder.authFn)
        .andThen { case (authUser, n) =>
          HelloService.helloJsonOutput(n, authUser)
        }
    )

  private[routes] def helloPost1Routes(implicit config: ServiceConfig): HttpRoutes[Task] = Http4sServerInterpreter.toRoutes(HelloEndpoints.helloPost1) {
    case (authUser, jsonInput) =>
      HelloService.helloJsonOutput(jsonInput.name, authUser)
  }

  def router(implicit config: ServiceConfig): HttpRoutes[Task] = Router(
    "/" -> (
      helloGet1Routes <+>
        helloGet2Routes <+>
        helloGet3Routes <+>
        helloAuth1Routes <+>
        helloAuth2Routes <+>
        helloPost1Routes <+>
        SwaggerRoutes.route
    )
  )
}
