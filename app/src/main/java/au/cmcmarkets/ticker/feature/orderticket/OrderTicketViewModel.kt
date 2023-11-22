package au.cmcmarkets.ticker.feature.orderticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.cmcmarkets.ticker.data.BlockchainRepository
import au.cmcmarkets.ticker.data.model.Ticker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderTicketViewModel @Inject constructor(
    private val repository: BlockchainRepository
) : ViewModel() {

    private var currency = "GBP" //TODO implement interface to set currency

    private val _ticker = MutableLiveData<Ticker>()
    val ticker: LiveData<Ticker>
        get() = _ticker

    private var pollingJob: Job? = null

    private val period: Long = 15000

    @Suppress("unused")
    fun getTickerByFlow(currency: String) {
        viewModelScope.launch {
            repository.getTickersFlow()
                .flowOn(Dispatchers.IO)
                .catch {
                    //TODO handle error if required
                }.collect {
                    _ticker.value = it[currency]
                }
        }
    }

    fun startPolling() {
        pollingJob = viewModelScope.launch {
            channelFlow {
                while (true) {
                    send("")
                    kotlinx.coroutines.delay(period)
                }
            }.flowOn(Dispatchers.IO).collect {
                repository.getTickerBy(currency, onSuccess = { ticker ->
                    _ticker.value = ticker
                }, onFailed = {
                    //TODO handle error if required
                })
            }
        }
    }

    fun stopPolling() {
        pollingJob?.cancel()
        pollingJob = null
    }
}