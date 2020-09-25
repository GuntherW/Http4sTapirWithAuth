package de.wittig.http4stapir

import cats.effect.{ContextShift, ExitCode, Sync}
import cats.implicits._
import de.wittig.http4stapir.auth.AuthUser
import de.wittig.http4stapir.controller.Hello
import de.wittig.http4stapir.controller.api.{GuntherException, Tapir}
import monix.eval.{Task, TaskApp}
import org.http4s.HttpRoutes
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.kleisli._
import sttp.model.StatusCode
import sttp.tapir.DecodeResult
import sttp.tapir.model.UsernamePassword
import sttp.tapir.server.ServerDefaults.{FailureHandling, FailureMessages}
import sttp.tapir.server.{DecodeFailureContext, ServerDefaults}
import sttp.tapir.server.http4s._

case class ServiceConfig(tokenPrefix: String)

object Main extends TaskApp {
  private implicit val config: ServiceConfig = ServiceConfig("foobar")

  private def myFailureHandler(ctx: DecodeFailureContext): Option[StatusCode] = ctx.failure match {
    case DecodeResult.Error(_, _: GuntherException) => Some(StatusCode.Unauthorized)
    case _                                          => FailureHandling.respondWithStatusCode(ctx, badRequestOnPathErrorIfPathShapeMatches = false, badRequestOnPathInvalidIfPathShapeMatches = true)
  }

  private def myFailureMessage(ctx: DecodeFailureContext): String = ctx.failure match {
    case DecodeResult.Error(_, _: GuntherException) => "Nice try! You shall NOT pass"
    case _                                          => FailureMessages.failureMessage(ctx)
  }

  private implicit def customServerOptions[F[_]: Sync: ContextShift]: Http4sServerOptions[F] =
    Http4sServerOptions
      .default(implicitly[Sync[F]], implicitly[ContextShift[F]])
      .copy(decodeFailureHandler = ServerDefaults.decodeFailureHandler.copy(respondWithStatusCode = myFailureHandler, failureMessage = myFailureMessage))

  private val hello1Routes: HttpRoutes[Task] = Tapir.hello1
    .toRoutes {
      case (n, authUser) =>
        Hello.hello1(n, authUser)
    }

  private val hello2Routes: HttpRoutes[Task] = Tapir.hello2
    .toRoutes {
      case (n, authUser) => Hello.hello2(n, authUser).run(config)
    }

  def authenticate(usernamePassword: UsernamePassword): Option[AuthUser] = {
    Some(AuthUser(usernamePassword.username))
  }

  private val hello3Routes: HttpRoutes[Task] = Tapir.hello3
    .toRoutes {
      case (jsonInput, authUser) => Hello.hello3(jsonInput.name, authUser).run(config)
    }

  def run(args: List[String]): Task[ExitCode] =
    BlazeServerBuilder[Task](scheduler)
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(
        Router(
          "/" -> (hello1Routes <+> hello2Routes <+> hello3Routes <+> Tapir.swaggerRoute)
        ).orNotFound
      )
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
