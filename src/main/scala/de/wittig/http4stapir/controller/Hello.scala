package de.wittig.http4stapir.controller

import cats.implicits.catsSyntaxEitherId
import monix.eval.Task

object Hello {

  def helloWorld(name: String): Task[Either[Unit, String]] =
    Task(s"Hello, $name!".asRight[Unit])
}
