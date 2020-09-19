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

  private val helloWorldRoutes: HttpRoutes[Task] = Tapir.helloWorld
    .toRoutes(Hello.hello1)

//  val auth = AuthenticationMiddleware(config) // TODO

  private val swaggerRoute: HttpRoutes[Task] = Tapir.swagger.routes[Task]

  def run(args: List[String]): Task[ExitCode] =
    BlazeServerBuilder[Task](scheduler)
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(Router("/" -> (helloWorldRoutes <+> swaggerRoute)).orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
