package au.cmcmarkets.ticker.data.api

import au.cmcmarkets.ticker.data.model.Ticker
import retrofit2.Response
import retrofit2.http.GET

interface BitcoinApi {

    @GET("/ticker")
    suspend fun getTickers(): Map<String, Ticker>

}