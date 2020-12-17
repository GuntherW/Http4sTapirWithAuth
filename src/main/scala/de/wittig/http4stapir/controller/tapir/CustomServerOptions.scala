package de.wittig.http4stapir.controller.tapir

import cats.effect.{ContextShift, Sync}
import sttp.model.StatusCode
import sttp.tapir.DecodeResult
import sttp.tapir.server.{DecodeFailureContext, ServerDefaults}
import sttp.tapir.server.ServerDefaults.{FailureHandling, FailureMessages}
import sttp.tapir.server.http4s.Http4sServerOptions

//object CustomServerOptions {
//
//  private def myFailureHandler(ctx: DecodeFailureContext): Option[StatusCode] = ctx.failure match {
//    case DecodeResult.Error(_, _: NotAuthorizedException) => Some(StatusCode.Unauthorized)
//    case _                                                => FailureHandling.respondWithStatusCode(ctx, badRequestOnPathErrorIfPathShapeMatches = false, badRequestOnPathInvalidIfPathShapeMatches = true)
//  }
//
//  private def myFailureMessage(ctx: DecodeFailureContext): String = ctx.failure match {
//    case DecodeResult.Error(_, _: NotAuthorizedException) => "Nice try! You shall NOT pass"
//    case _                                                => FailureMessages.failureMessage(ctx)
//  }
//
//  def customServerOptions[F[_]: Sync: ContextShift]: Http4sServerOptions[F] =
//    Http4sServerOptions
//      .default(implicitly[Sync[F]], implicitly[ContextShift[F]])
//      .copy(decodeFailureHandler = ServerDefaults.decodeFailureHandler.copy(respondWithStatusCode = myFailureHandler, failureMessage = myFailureMessage))
//}
