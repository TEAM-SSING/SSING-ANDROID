package com.ssing.presentation.devauth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingSelectButton
import com.ssing.core.ui.extension.toast
import com.ssing.core.ui.util.HandleUiEffects

@Composable
internal fun DevAuthRoute(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DevAuthViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    HandleUiEffects(viewModel.uiEffect) {effect ->
        when (effect) {
            DevAuthContract.Effect.NavigateToHome -> navigateToHome()
            is DevAuthContract.Effect.ShowToast -> context.toast(effect.message)
        }
    }

    DevAuthScreen(
        state = uiState,
        onPersonaSelect = viewModel::setPersonaKey,
        onLoginClick = viewModel::login,
        modifier = modifier,
    )
}

@Composable
private fun DevAuthScreen(
    state: DevAuthContract.State,
    onPersonaSelect: (String) -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(text = "개발용 로그인")

        LazyColumn(
            modifier = Modifier.weight(1f),
        ) {
            items(state.personaKeys) { personaKey ->
                SsingSelectButton(
                    text = personaKey,
                    isSelected = personaKey == state.selectedPersonaKey,
                    onClick = { onPersonaSelect(personaKey) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))
            }
        }

        SsingButton(
            text = "로그인",
            onClick = onLoginClick,
            style = SsingButtonStyle.BLUE,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DevAuthScreenPreview() {
    DevAuthScreen(
        state = DevAuthContract.State(
            personaKeys = listOf("CUSTOMER_A", "CUSTOMER_B"),
            selectedPersonaKey = "CUSTOMER_A",
        ),
        onPersonaSelect = {},
        onLoginClick = {},
    )
}