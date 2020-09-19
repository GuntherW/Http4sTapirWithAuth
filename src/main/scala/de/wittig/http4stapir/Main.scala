package de.wittig.http4stapir

import cats.effect.ExitCode
import cats.implicits._
import de.wittig.http4stapir.controller.Hello
import de.wittig.http4stapir.controller.api.Tapir
import monix.eval.{Task, TaskApp}
import org.http4s.HttpRoutes
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.kleisli._
import sttp.tapir.server.http4s._

case class ServiceConfig(someValue: String)

object Main extends TaskApp {

  private val config = ServiceConfig("some special value")

  private val hello1Routes: HttpRoutes[Task] = Tapir.hello1
    .toRoutes(Hello.hello1)

  private val hello2Routes: HttpRoutes[Task] = Tapir.hello2
    .toRoutes(n => Hello.hello2(n).run(config))

//  val auth = AuthenticationMiddleware(config) // TODO

  private val swaggerRoute: HttpRoutes[Task] = Tapir.swagger.routes[Task]

  def run(args: List[String]): Task[ExitCode] =
    BlazeServerBuilder[Task](scheduler)
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(
        Router(
          "/" -> (hello1Routes <+> hello2Routes <+> swaggerRoute)
        ).orNotFound
      )
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
