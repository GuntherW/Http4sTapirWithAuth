package de.wittig.http4stapir

import cats.data.{Kleisli, OptionT}
import monix.eval.Task
import org.http4s.Request
import org.http4s.server.AuthMiddleware
import org.http4s.util.CaseInsensitiveString

import scala.util.{Failure, Success}

object AuthenticationMiddleware {

  def apply(config: ServiceConfig): AuthMiddleware[Task, AuthUser] =
    AuthMiddleware(Kleisli(requestToAuthUser(config)))

  private def requestToAuthUser(config: ServiceConfig)(request: Request[Task]): OptionT[Task, AuthUser] = OptionT(
    Task(
      Some(AuthUser(config.username))
    )
  )
}
