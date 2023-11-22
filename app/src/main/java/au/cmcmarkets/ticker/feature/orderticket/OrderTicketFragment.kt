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
                                Toast.makeText(
                                    context,
                                    "Order of ${it.units} units in total ${it.amount} on ${it.symbol} currency",
                                    Toast.LENGTH_LONG
                                ).show()
                            },
                            onCancelClick = {
                                //TODO Implement Cancel
                                Toast.makeText(context, "Cancel Click", Toast.LENGTH_LONG).show()
                            },
                            onSwipeMarket = {
                                //TODO Implement Swipe
                                Toast.makeText(context, "Market Swipe clicked", Toast.LENGTH_LONG)
                                    .show()
                            })
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startPolling()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopPolling()
    }
}

