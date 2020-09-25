package de.wittig.http4stapir

import cats.effect.ExitCode
import cats.implicits._
import de.wittig.http4stapir.controller.Hello
import de.wittig.http4stapir.controller.auth.AuthUser
import de.wittig.http4stapir.controller.tapir.{Api, CustomServerOptions}
import monix.eval.{Task, TaskApp}
import org.http4s.HttpRoutes
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.kleisli._
import sttp.tapir.model.UsernamePassword
import sttp.tapir.server.http4s._

case class ServiceConfig(tokenPrefix: String)

object Main extends TaskApp {

  private implicit val config: ServiceConfig                         = ServiceConfig("foobar")
  private implicit val customServerOption: Http4sServerOptions[Task] = CustomServerOptions.customServerOptions

  private val hello1Routes: HttpRoutes[Task] = Api.hello1
    .toRoutes { case (n, authUser) =>
      Hello.hello1(n, authUser, config)
    }

  private val hello2Routes: HttpRoutes[Task] = Api.hello2
    .toRoutes { case (n, authUser) =>
      Hello.hello2(n, authUser).run(config)
    }

  def authenticate(usernamePassword: UsernamePassword): Option[AuthUser] = {
    Some(AuthUser(usernamePassword.username))
  }

  private val hello3Routes: HttpRoutes[Task] = Api.hello3
    .toRoutes { case (jsonInput, authUser) =>
      Hello.hello3(jsonInput.name, authUser).run(config)
    }

  def run(args: List[String]): Task[ExitCode] =
    BlazeServerBuilder[Task](scheduler)
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(
        Router(
          "/" -> (
            hello1Routes <+>
              hello2Routes <+>
              hello3Routes <+>
              Api.swaggerRoute
          )
        ).orNotFound
      )
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
