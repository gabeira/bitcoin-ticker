package au.cmcmarkets.ticker.feature.orderticket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import au.cmcmarkets.ticker.core.di.viewmodel.ViewModelFactory
import au.cmcmarkets.ticker.ui.theme.ComposeSampleTheme
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class OrderTicketFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: OrderTicketViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[OrderTicketViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            viewModel.getTickerBy("GBP")
            setContent {
                ComposeSampleTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        OrderTicker(
                            viewModel,
                            onConfirmClick = {
                                //TODO Implement Confirmation
                                Log.e("Frag", ">> onConfirmClick with amount of ${it.amount}")
                            },
                            onCancelClick = {
                                //TODO Implement Cancel
                                Log.e("Frag", ">> onCancelClick ")
                            },
                            onSwipeMarket = {
                                //TODO Implement Swipe
                                Log.e("Frag", ">> onSwipeMarket")
                            })
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //TODO remove this code, only for testing purpose
        viewModel.ticker.observe(this) {
            Toast.makeText(
                context,
                "Ticker last value ${it.last} for ${it.symbol}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onPause() {
        super.onPause()

        // TODO("Stop polling")
    }
}

