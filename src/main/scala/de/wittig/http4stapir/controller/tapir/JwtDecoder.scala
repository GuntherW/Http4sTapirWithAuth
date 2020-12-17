package de.wittig.http4stapir.controller.tapir

import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.controller.auth.AuthUser
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
}
