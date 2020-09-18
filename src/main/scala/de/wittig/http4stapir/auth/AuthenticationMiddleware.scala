package de.wittig.http4stapir.auth

import cats.data.{Kleisli, OptionT}
import de.wittig.http4stapir.ServiceConfig
import monix.eval.Task
import org.http4s.Request
import org.http4s.server.AuthMiddleware

case class AuthUser(name: String)

//object AuthenticationMiddleware {
//
//  def apply(config: ServiceConfig): AuthMiddleware[Task, AuthUser] =
//    AuthMiddleware(Kleisli(requestToAuthUser(config)))
//
//  private def requestToAuthUser(config: ServiceConfig)(request: Request[Task]): OptionT[Task, AuthUser] = OptionT(
//    // Als erster Schritt erstmal nur hart einen Nutzer zurückgeben.
//    // TODO: Muß später aus dem jwt Token dekodiert werden.
//    Task(
//      Some(AuthUser(config.nameSuffix))
//    )
//  )
//}
