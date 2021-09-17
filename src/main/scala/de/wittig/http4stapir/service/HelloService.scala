package de.wittig.http4stapir.service

import cats.data.Reader
import cats.implicits.catsSyntaxEitherId
import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.model.{AuthUser, ErrorInfo, JsonOutput, Unauthorized}
import cats.effect.IO

object HelloService {

  def hellosSimple(name: String, authuser: AuthUser)(implicit config: ServiceConfig): IO[Either[Unit, String]] =
    IO(s"Hello, $name! AuthUser: $authuser.  config: ${config.tokenPrefix}".asRight[Unit])

  def helloReader(name: String, authUser: AuthUser): Reader[ServiceConfig, IO[Either[Unit, String]]] = Reader { (config: ServiceConfig) =>
    IO(s"Hello, $name! AuthUser: $authUser. config: ${config.tokenPrefix},".asRight[Unit])
  }

  def helloJsonOutput(name: String, authUser: AuthUser)(implicit config: ServiceConfig): IO[Either[ErrorInfo, JsonOutput]] =
    if (name == "Gunther")
      IO(JsonOutput(name, authUser.name, config.tokenPrefix).asRight[ErrorInfo])
    else
      IO(Unauthorized(name).asLeft[JsonOutput])

}
