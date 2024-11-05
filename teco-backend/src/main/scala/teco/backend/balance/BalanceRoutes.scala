package teco.backend.balance

import zio.*
import zio.http.*
import zio.schema.codec.JsonCodec.schemaBasedBinaryCodec

object BalanceRoutes:
  def apply(): Routes[BalanceRepo, Response] =
    Routes(
      Method.GET / "balance" / string("clientId") -> handler { (clientId: String, _: Request) =>
        BalanceRepo
          .lookup(clientId)
          .mapBoth(
            _ => Response.internalServerError(s"Cannot retrieve balance for client $clientId"),
            {
              case Some(balance) =>
                Response(body = Body.from(balance))
              case None =>
                Response.notFound(s"No records for client $clientId found!")
            },
          )
      },
    )
