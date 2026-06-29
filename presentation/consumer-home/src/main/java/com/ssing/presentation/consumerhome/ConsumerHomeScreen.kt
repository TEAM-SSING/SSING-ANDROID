package com.ssing.presentation.consumerhome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun ConsumerHomeRoute(
    modifier: Modifier = Modifier,
    viewModel: ConsumerHomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ConsumerHomeScreen(
        state = state,
        modifier = modifier,
    )
}

@Composable
private fun ConsumerHomeScreen(
    state: ConsumerHomeContract.State,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "소비자 홈")
    }
}

@Preview(showBackground = true)
@Composable
private fun ConsumerHomeScreenPreview() {
    ConsumerHomeScreen(
        state = ConsumerHomeContract.State(),
    )
}
