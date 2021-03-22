package de.wittig.http4stapir.api

import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.model.{AuthUser, ErrorInfo, Unauthorized}
import monix.eval.Task
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

  def authFn(token: String): Task[Either[ErrorInfo, AuthUser]] =
    if (token == "foobar")
      Task.pure(Right(new AuthUser(token)))
    else
      Task.pure(Left(Unauthorized(token)))

}
