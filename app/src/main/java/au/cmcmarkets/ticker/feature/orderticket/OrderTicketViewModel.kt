package au.cmcmarkets.ticker.feature.orderticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.cmcmarkets.ticker.data.BlockchainRepository
import au.cmcmarkets.ticker.data.model.Ticker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


class OrderTicketViewModel @Inject constructor(
    private val repository: BlockchainRepository
) : ViewModel() {

    private val _ticker = MutableLiveData<Ticker>()
    val ticker: LiveData<Ticker>
        get() = _ticker

    private val period: Long = 15000
    fun getTickerBy(currency: String) {
        viewModelScope.launch {
            channelFlow {
                //TODO improve this code, maybe add flow into repository to emit properly
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
}