package de.wittig.http4stapir.api

sealed trait ErrorInfo
case class NotFound(what: String)          extends ErrorInfo
case class Unauthorized(realm: String)     extends ErrorInfo
case class Unknown(code: Int, msg: String) extends ErrorInfo
case object NoContent                      extends ErrorInfo
