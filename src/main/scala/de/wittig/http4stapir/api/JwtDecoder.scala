package de.wittig.http4stapir.api

import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.model.{AuthUser, ErrorInfo, Unauthorized}
import cats.effect.IO
import sttp.tapir.EndpointInput.Auth
import sttp.tapir.{DecodeResult, auth}

final class NotAuthorizedException(message: String, cause: Option[Throwable] = None) extends RuntimeException(message, cause.orNull)

object JwtDecoder {
  def decodeJwt(implicit serviceConfig: ServiceConfig): Auth.Http[AuthUser] = {
    auth
      .bearer[String]()
      .mapDecode[AuthUser] { headerValue =>
        if (headerValue.startsWith(serviceConfig.tokenPrefix))
          DecodeResult.Value(AuthUser(headerValue))
        else
          DecodeResult.Error(headerValue, new NotAuthorizedException("Oooops, nice try :)"))
      }(_.name)
  }

  def authFn(token: String): IO[Either[ErrorInfo, AuthUser]] =
    if (token == "foobar")
      IO.pure(Right(new AuthUser(token)))
    else
      IO.pure(Left(Unauthorized(token)))

}
