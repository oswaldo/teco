package teco.backend.balance

import zio.schema.*
import zio.schema.DeriveSchema.*

case class Balance(clientId: String, balance: BigDecimal)

object Balance:
  given Schema[Balance] = DeriveSchema.gen[Balance]
