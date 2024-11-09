package teco.backend.balance

import zio.*

case class InMemoryBalanceRepo(balanceMapRef: Ref[Map[String, Balance]]) extends BalanceRepo:
  def lookup(clientId: String): UIO[Option[Balance]] =
    for
      _          <- ZIO.logDebug(s"Retrieving balance for client $clientId")
      balanceMap <- balanceMapRef.get
      balance     = balanceMap.get(clientId)
    yield balance

object InMemoryBalanceRepo:
  def layer: ZLayer[Any, Nothing, InMemoryBalanceRepo] =
    ZLayer.fromZIO(
      Ref.make( /*Map.empty[String, Balance]*/ Map("1" -> Balance("1", BigDecimal("0")))).map(InMemoryBalanceRepo(_)),
    )
