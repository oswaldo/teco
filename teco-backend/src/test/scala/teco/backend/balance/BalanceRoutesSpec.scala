package teco.backend.balance

import zio.*
import zio.http.*
import zio.http.netty.NettyConfig
import zio.http.netty.server.NettyDriver
import zio.schema.codec.JsonCodec.schemaBasedBinaryCodec
import zio.test.*

object BalanceRoutesSpec extends ZIOSpecDefault:

  override def spec: Spec[Any, Any] = suite("BalanceRoutes")(
    test("Get the balance of client id 1") {
      for
        client      <- ZIO.service[Client]
        _           <- TestServer.addRoutes(BalanceRoutes())
        port        <- ZIO.serviceWith[Server](_.port)
        p           <- port
        url          = URL.root.port(p)
        testBalance  = Balance("1", BigDecimal(0))
        getResponse <- client(Request.get(url / "balance" / "1"))
        result      <- getResponse.body.to[Balance]
      yield assertTrue(result == testBalance)
    }.provideSome[Client & Driver & BalanceRepo](
      TestServer.layer,
      Scope.default,
      InMemoryBalanceRepo.layer,
    ),
  ).provide(
    ZLayer.succeed(Server.Config.default.onAnyOpenPort),
    Client.default,
    NettyDriver.customized,
    ZLayer.succeed(NettyConfig.defaultWithFastShutdown),
    InMemoryBalanceRepo.layer,
  )

  override def aspects: Chunk[TestAspectPoly] =
    Chunk(TestAspect.timeout(60.seconds), TestAspect.timed)
