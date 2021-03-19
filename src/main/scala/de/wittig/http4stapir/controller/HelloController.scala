package de.wittig.http4stapir.controller

import cats.data.Reader
import cats.implicits.catsSyntaxEitherId
import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.controller.auth.AuthUser
import de.wittig.http4stapir.model.JsonOutput
import monix.eval.Task

object HelloController {

  def helloGet1(name: String, authuser: AuthUser, serviceConfig: ServiceConfig): Task[Either[Unit, String]] =
    Task(s"Hello, $name! AuthUser: $authuser.  config: ${serviceConfig.tokenPrefix}".asRight[Unit])

  def helloGet2(name: String, authUser: AuthUser): Reader[ServiceConfig, Task[Either[Unit, String]]] = Reader { config: ServiceConfig =>
    Task(s"Hello, $name! AuthUser: $authUser. config: ${config.tokenPrefix},".asRight[Unit])
  }

  def helloGet3(name: String, authUser: AuthUser, config: ServiceConfig): Task[Either[Unit, JsonOutput]] =
    Task(JsonOutput(name, authUser.name, config.tokenPrefix).asRight[Unit])

  def helloPost1(name: String, authUser: AuthUser, config: ServiceConfig): Task[Either[Unit, JsonOutput]] =
    Task(JsonOutput(name, authUser.name, config.tokenPrefix).asRight[Unit])

}
