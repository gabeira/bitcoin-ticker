package au.cmcmarkets.ticker.data

import android.util.Log
import au.cmcmarkets.ticker.data.api.BitcoinApi
import au.cmcmarkets.ticker.data.model.Ticker
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BlockchainRepository @Inject constructor(private val retrofit: BitcoinApi) {

    //Alternative way, explore new implementation
    fun getTickersFlow() = flow { emit(retrofit.getTickers()) }

    suspend fun getTickerBy(
        currency: String,
        onSuccess: (Ticker) -> Unit,
        onFailed: (String) -> Unit
    ) {
        try {
            retrofit.getTickers()[currency]?.let {
                Log.d("BlockchainRepository", "Last price ${currency}: ${it.last}")
                onSuccess(it)
            } ?: {
                onFailed("Error loading ticker for $currency, not found")
            }
        } catch (e: Exception) {
            val error = "Error loading ticker for $currency, ${e.localizedMessage}"
            Log.e("BlockchainRepository", error)
            onFailed(error)
        }
    }
}