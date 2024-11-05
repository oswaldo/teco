package teco.backend

import zio.*
import zio.http.*

import teco.backend.balance.*

object MainApp extends ZIOAppDefault:
  def run =
    for
      _ <- ZIO.logInfo("Starting server...")
      _ <- Server
             .serve(
               BalanceRoutes(),
             )
             .provide(
               Server.defaultWithPort(8081),
               InMemoryBalanceRepo.layer,
             )
    yield ()
