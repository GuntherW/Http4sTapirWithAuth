package de.wittig.http4stapir

import cats.effect.ExitCode
import cats.implicits._
import de.wittig.http4stapir.controller.Hello
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

  private val hello1Routes: HttpRoutes[Task] = Http4sServerInterpreter.toRoutes(Api.hello1) { case (authUser, n) =>
    Hello.hello1(n, authUser, config)
  }

  private val hello2Routes: HttpRoutes[Task] = Http4sServerInterpreter.toRoutes(Api.hello2) { case (authUser, n) =>
    Hello.hello2(n, authUser).run(config)
  }

  private val hello3Routes: HttpRoutes[Task] = Http4sServerInterpreter.toRoutes(Api.hello3) { case (jsonInput, authUser) =>
    Hello.hello3(jsonInput.name, authUser).run(config)
  }

  def run(args: List[String]): Task[ExitCode] =
    BlazeServerBuilder[Task](scheduler)
      .bindHttp(8084, "0.0.0.0")
      .withHttpApp(
        Router(
          "/" -> (
            hello1Routes <+>
              hello2Routes <+>
              hello3Routes <+>
              Swagger.route
          )
        ).orNotFound
      )
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
