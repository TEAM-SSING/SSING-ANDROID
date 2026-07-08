package com.ssing.presentation.consumerpayment

import androidx.lifecycle.viewModelScope
import com.ssing.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PaymentViewModel @Inject constructor() :
    BaseViewModel<PaymentContract.State, PaymentContract.Effect>(
        PaymentContract.State()
    ) {

    fun onMatchingClick() {
        viewModelScope.launch {
            sendEffect(PaymentContract.Effect.NavigateToMatching)
        }
    }
}
