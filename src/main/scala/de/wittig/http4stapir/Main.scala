package de.wittig.http4stapir

import cats.effect.ExitCode
import cats.implicits._
import de.wittig.http4stapir.controller.MyEndPoints
import monix.eval.{Task, TaskApp}
import org.http4s.HttpRoutes
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.syntax.kleisli._
import sttp.tapir.server.http4s._

case class ServiceConfig(nameSuffix: String)

object Main extends TaskApp {

  private val config = ServiceConfig("Name vom Backend")

  private val helloWorldRoutes: HttpRoutes[Task] = MyEndPoints.helloWorld
    .toRoutes(name => Task(s"Hello, $name!".asRight[Unit]))

//  val auth = AuthenticationMiddleware(config) // TODO

  private val swaggerRoute: HttpRoutes[Task] = MyEndPoints.swagger.routes[Task]

  def run(args: List[String]): Task[ExitCode] =
    BlazeServerBuilder[Task](scheduler)
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(Router("/" -> (helloWorldRoutes <+> swaggerRoute)).orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
