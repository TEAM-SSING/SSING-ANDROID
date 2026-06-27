package com.ssing.presentation.consumerhome

import androidx.lifecycle.viewModelScope
import com.ssing.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ConsumerHomeViewModel @Inject constructor() :
    BaseViewModel<ConsumerHomeContract.State, ConsumerHomeContract.Effect>(
        ConsumerHomeContract.State()
    ) {

    fun onMatchingClick() {
        viewModelScope.launch {
            sendEffect(ConsumerHomeContract.Effect.NavigateToMatching)
        }
    }
}
