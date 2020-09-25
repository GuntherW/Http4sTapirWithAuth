package de.wittig.http4stapir.controller

import cats.data.Reader
import cats.implicits.catsSyntaxEitherId
import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.auth.AuthUser
import monix.eval.Task
import sttp.tapir.model.UsernamePassword

object Hello {

  // works already
  def hello1(name: String, auth: AuthUser): Task[Either[Unit, String]] =
    Task(s"Hello, $name!".asRight[Unit])

  def hello2(name: String, user: AuthUser): Reader[ServiceConfig, Task[Either[Unit, String]]] =
    Reader { config: ServiceConfig =>
      Task(s"Hello, $name! config: ${config.tokenPrefix}, AuthUser: ${user.name}".asRight[Unit])
    }

  // TODO Exercise 2 // würde man den AuthUser dann hier am besten übergeben? Wahrscheinlich führt dieser Weg hier nicht zum Ziel, da keine Benutzung der AuthMiddleware von http4s
  def hello3(name: String, authuser: AuthUser): Reader[ServiceConfig, Task[Either[Unit, String]]] =
    Reader { config: ServiceConfig =>
      Task(s"Hello, $name! You are identified as $authuser. config: ${config.tokenPrefix}".asRight[Unit])
    }

  // TODO Exercise 3
  // only, if user is authorized to call method
}
