package de.wittig.http4stapir.controller

import cats.data.Reader
import cats.implicits.catsSyntaxEitherId
import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.controller.auth.AuthUser
import monix.eval.Task

object Hello {

  def hello1(name: String, authuser: AuthUser, serviceConfig: ServiceConfig): Task[Either[Unit, String]] =
    Task(s"Hello, $name! AuthUser: $authuser.  config: ${serviceConfig.tokenPrefix}".asRight[Unit])

  def hello2(name: String, authUser: AuthUser): Reader[ServiceConfig, Task[Either[Unit, String]]] =
    Reader { config: ServiceConfig =>
      Task(s"Hello, $name! AuthUser: $authUser. config: ${config.tokenPrefix},".asRight[Unit])
    }

  def hello3(name: String, authuser: AuthUser): Reader[ServiceConfig, Task[Either[Unit, String]]] =
    Reader { config: ServiceConfig =>
      Task(s"Hello, $name! AuthUser: $authuser. config: ${config.tokenPrefix}".asRight[Unit])
    }
}
