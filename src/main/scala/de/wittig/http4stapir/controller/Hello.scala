package de.wittig.http4stapir.controller

import cats.data.Reader
import cats.implicits.catsSyntaxEitherId
import de.wittig.http4stapir.ServiceConfig
import de.wittig.http4stapir.auth.AuthUser
import monix.eval.Task

object Hello {

  // works already
  def hello1(name: String): Task[Either[Unit, String]] =
    Task(s"Hello, $name!".asRight[Unit])

  // TODO Exercise 1
  def hello2(name: String): Reader[ServiceConfig, Task[Either[Unit, String]]] =
    Reader { config: ServiceConfig =>
      Task(s"Hello, $name! config: ${config.someValue}".asRight[Unit])
    }

  // TODO Exercise 2
  def hello3(name: String, user: AuthUser): Reader[ServiceConfig, Task[Either[Unit, String]]] =
    Reader { config: ServiceConfig =>
      Task(s"Hello, $name! You are identified as ${user.name}. config: ${config.someValue}".asRight[Unit])
    }

  // TODO Exercise 3
  // only, if user is authorized to call method
}
