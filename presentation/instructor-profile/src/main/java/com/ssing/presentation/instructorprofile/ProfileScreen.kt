package com.ssing.presentation.instructorprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.toast
import com.ssing.core.ui.util.HandleUiEffects

@Composable
internal fun ProfileRoute(
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    HandleUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            ProfileContract.Effect.NavigateToLogin -> navigateToLogin()
            is ProfileContract.Effect.ShowToast -> context.toast(effect.message)
        }
    }

    ProfileScreen(
        state = state,
        onLogoutClick = viewModel::onLogoutClick,
        modifier = modifier,
    )
}

@Composable
private fun ProfileScreen(
    state: ProfileContract.State,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SSINGTheme.colors.backgroundNormal)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center,
    ) {
        SsingButton(
            text = "로그아웃",
            onClick = onLogoutClick,
            style = SsingButtonStyle.BLUE,
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    SSINGTheme {
        ProfileScreen(
            state = ProfileContract.State(),
            onLogoutClick = {},
        )
    }
}
