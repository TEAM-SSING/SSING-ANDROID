package com.ssing.presentation.consumerpayment

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
internal fun PaymentRoute(
    modifier: Modifier = Modifier,
    viewModel: PaymentViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    PaymentScreen(
        state = state,
        modifier = modifier,
    )
}

@Composable
private fun PaymentScreen(
    state: PaymentContract.State,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "결제홈")
    }
}

@Preview(showBackground = true)
@Composable
private fun PaymentScreenPreview() {
    PaymentScreen(
        state = PaymentContract.State(),
    )
}
