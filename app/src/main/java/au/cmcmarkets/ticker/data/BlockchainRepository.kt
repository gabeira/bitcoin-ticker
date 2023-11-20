package au.cmcmarkets.ticker.data

import android.util.Log
import au.cmcmarkets.ticker.data.api.BitcoinApi
import au.cmcmarkets.ticker.data.model.Ticker
import javax.inject.Inject

class BlockchainRepository @Inject constructor(private val retrofit: BitcoinApi) {

    suspend fun getTickerBy(
        currency: String,
        onSuccess: (Ticker) -> Unit,
        onFailed: (String) -> Unit
    ) {
        try {
            val response = retrofit.getTickers()
            if (response.isSuccessful) {
                response.body()?.get(currency)?.let {
                    Log.d("BlockchainRepository", "Last price ${currency}: ${it.last}")
                    onSuccess(it)
                } ?: {
                    onFailed("Error loading ticker for $currency, not found")
                }
            } else {
                Log.e("BlockchainRepository", ">> Else ${response.errorBody()}")
                onFailed("Error loading ticker for $currency, ${response.errorBody()}")
            }
        } catch (e: Exception) {
            val error = "Error loading ticker for $currency, ${e.localizedMessage}"
            Log.e("BlockchainRepository", error)
            onFailed(error)
        }
    }
}