package de.wittig.http4stapir.controller.auth

import de.wittig.http4stapir.ServiceConfig
import sttp.tapir.{Codec, CodecFormat}

case class AuthUser(name: String)

object AuthUser {

  implicit def codec(implicit serviceConfig: ServiceConfig): Codec[String, AuthUser, CodecFormat.TextPlain] =
    Codec.string.map(s => AuthUser(s))(_.name)
}
