package de.wittig.http4stapir.auth

import cats.data.{Kleisli, OptionT}
import cats.implicits.catsSyntaxOptionId
import de.wittig.http4stapir.ServiceConfig
import monix.eval.Task
import org.http4s.Request
import org.http4s.server.AuthMiddleware
import sttp.tapir.{Codec, CodecFormat}

case class AuthUser(name: String)

object AuthUser {
  implicit val codec: Codec[String, AuthUser, CodecFormat.TextPlain] = Codec.string.map(s => AuthUser(s))(_.name)
}

object AuthenticationMiddleware {

  def apply(config: ServiceConfig): AuthMiddleware[Task, AuthUser] =
    AuthMiddleware(Kleisli(requestToAuthUser(config)))

  private def requestToAuthUser(config: ServiceConfig)(request: Request[Task]): OptionT[Task, AuthUser] = OptionT(
    // Als erster Schritt erstmal nur hart einen Nutzer zurückgeben.
    // TODO: Muß später aus dem jwt Token dekodiert werden.
    Task(
      AuthUser(config.someValue + "Middleware").some
    )
  )
}
