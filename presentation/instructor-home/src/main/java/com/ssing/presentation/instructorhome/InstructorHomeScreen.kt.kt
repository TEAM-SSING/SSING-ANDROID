package com.ssing.presentation.instructorhome

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
internal fun InstructorHomeRoute(
    modifier: Modifier = Modifier,
    viewModel: InstructorHomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    InstructorHomeScreen(
        state = uiState,
        modifier = modifier,
    )
}

@Composable
private fun InstructorHomeScreen(
    state: InstructorHomeContract.State,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "강사 홈")
    }
}

@Preview(showBackground = true)
@Composable
private fun InstructorHomeScreenPreview() {
    InstructorHomeScreen(
        state = InstructorHomeContract.State(),
    )
}
