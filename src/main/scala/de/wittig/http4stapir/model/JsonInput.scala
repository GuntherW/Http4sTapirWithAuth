package de.wittig.http4stapir.model

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

case class JsonInput(
    name: String
)

object JsonInput {
  implicit val codec: Codec.AsObject[JsonInput] = deriveCodec[JsonInput]
}
