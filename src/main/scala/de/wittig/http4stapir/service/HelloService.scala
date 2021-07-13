package de.wittig.http4stapir.service

import cats.data.Reader
import cats.implicits.catsSyntaxEitherId
import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.model.{AuthUser, ErrorInfo, JsonOutput, Unauthorized}
import monix.eval.Task

object HelloService {

  def hellosSimple(name: String, authuser: AuthUser)(implicit config: ServiceConfig): Task[Either[Unit, String]] =
    Task(s"Hello, $name! AuthUser: $authuser.  config: ${config.tokenPrefix}".asRight[Unit])

  def helloReader(name: String, authUser: AuthUser): Reader[ServiceConfig, Task[Either[Unit, String]]] = Reader { (config: ServiceConfig) =>
    Task(s"Hello, $name! AuthUser: $authUser. config: ${config.tokenPrefix},".asRight[Unit])
  }

  def helloJsonOutput(name: String, authUser: AuthUser)(implicit config: ServiceConfig): Task[Either[ErrorInfo, JsonOutput]] =
    if (name == "Gunther")
      Task(JsonOutput(name, authUser.name, config.tokenPrefix).asRight[ErrorInfo])
    else
      Task(Unauthorized(name).asLeft[JsonOutput])

}
