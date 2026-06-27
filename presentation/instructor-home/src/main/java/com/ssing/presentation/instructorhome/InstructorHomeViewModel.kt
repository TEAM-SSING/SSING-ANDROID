package com.ssing.presentation.instructorhome

import androidx.lifecycle.viewModelScope
import com.ssing.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class InstructorHomeViewModel @Inject constructor() :
    BaseViewModel<InstructorHomeContract.State, InstructorHomeContract.Effect>(
        InstructorHomeContract.State()
    ) {

    fun onMatchingClick() {
        viewModelScope.launch {
            sendEffect(InstructorHomeContract.Effect.NavigateToMatching)
        }
    }
}
