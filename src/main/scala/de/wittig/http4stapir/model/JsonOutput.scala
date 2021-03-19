package de.wittig.http4stapir.model

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

case class JsonOutput(name: String, authUser: String, token: String)

object JsonOutput {
  implicit val codec: Codec.AsObject[JsonOutput] = deriveCodec[JsonOutput]
}
