package teco.backend

import zio.*
import zio.http.*
import zio.logging.backend.SLF4J

import teco.backend.balance.*

object MainApp extends ZIOAppDefault:

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] = Runtime.removeDefaultLoggers >>> SLF4J.slf4j

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
             .onInterrupt(ZIO.logInfo("Server stopped."))
    yield ()
