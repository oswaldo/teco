package teco.backend.balance

import zio.*

trait BalanceRepo:
  def lookup(clientId: String): Task[Option[Balance]]

object BalanceRepo:
  def lookup(clientId: String): ZIO[BalanceRepo, Throwable, Option[Balance]] =
    ZIO.serviceWithZIO[BalanceRepo](_.lookup(clientId))
