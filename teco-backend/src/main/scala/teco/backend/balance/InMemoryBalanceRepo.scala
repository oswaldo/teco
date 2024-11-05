package teco.backend.balance

import zio.*

case class InMemoryBalanceRepo(map: Ref[Map[String, Balance]]) extends BalanceRepo:
  def lookup(clientId: String): UIO[Option[Balance]] =
    map.get.map(_.get(clientId))

object InMemoryBalanceRepo:
  def layer: ZLayer[Any, Nothing, InMemoryBalanceRepo] =
    ZLayer.fromZIO(
      Ref.make( /*Map.empty[String, Balance]*/ Map("1" -> Balance("1", BigDecimal("0")))).map(InMemoryBalanceRepo(_)),
    )
