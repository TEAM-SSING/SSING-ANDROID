package com.ssing.presentation.auth.consumer

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.R
import com.ssing.core.ui.common.component.KakaoButton
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.toast
import com.ssing.core.ui.util.HandleUiEffects
import com.ssing.presentation.auth.KakaoLoginEntryPoint
import com.ssing.presentation.auth.LoginContract
import dagger.hilt.android.EntryPointAccessors


@Composable
internal fun ConsumerLoginRoute(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ConsumerLoginViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = LocalActivity.current

    val kakaoLoginManager = remember(activity) {
        val activity = activity ?: throw IllegalStateException("Activity not found")
        EntryPointAccessors.fromActivity(
            activity,
            KakaoLoginEntryPoint::class.java,
        ).kakaoLoginManager()
    }

    HandleUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is LoginContract.Effect.NavigateToHome -> navigateToHome()
            is LoginContract.Effect.ShowToast -> context.toast(effect.message)

            LoginContract.Effect.LaunchKakaoLogin -> {
                kakaoLoginManager.login(context) { result ->
                    result.onSuccess { token ->
                        viewModel.processIntent(
                            LoginContract.Intent.OnKakaoSuccess(token.accessToken)
                        )
                    }.onFailure { error ->
                        viewModel.processIntent(
                            LoginContract.Intent.OnKakaoFailure(
                                error.message ?: "카카오 로그인 실패"
                            )
                        )
                    }
                }
            }
        }
    }

    ConsumerLoginScreen(
        state = state,
        onKakaoClick = { viewModel.processIntent(LoginContract.Intent.OnKakaoClick) },
        onConditionClick = {},
        onServiceCenterClick = {},
        onPersonalInfoClick = {},
        modifier = modifier,
    )
}

@Composable
private fun ConsumerLoginScreen(
    state: LoginContract.State,
    onKakaoClick: () -> Unit,
    onConditionClick: () -> Unit,
    onPersonalInfoClick: () -> Unit,
    onServiceCenterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SSINGTheme.colors.backgroundNormal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        LoginLogo()

        Spacer(modifier = Modifier.weight(1f))

        LoginSection(
            onKakaoClick = onKakaoClick,
            onConditionClick = onConditionClick,
            onPersonalInfoClick = onPersonalInfoClick,
            onServiceCenterClick = onServiceCenterClick,
        )
    }
}

@Composable
private fun LoginLogo(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.img_consumer_login_logo),
            contentDescription = null,
            modifier =  Modifier.size(
                width = 163.dp,
                height = 32.dp
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "스키 강습을 가장 쉽고 빠르게",
            style = SSINGTheme.typography.title.b20.copy(
                lineHeight = 1.2.em,
            ),
            color = SSINGTheme.colors.textStrong,
        )
    }
}

@Composable
private fun LoginSection(
    onKakaoClick: () -> Unit,
    onConditionClick: () -> Unit,
    onPersonalInfoClick: () -> Unit,
    onServiceCenterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KakaoButton(
            onClick = onKakaoClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            InfoText(
                text = "이용 약관",
                onClick = onConditionClick,
            )

            InfoText(
                text = "개인정보 처리방침",
                onClick = onPersonalInfoClick,
            )

            InfoText(
                text = "고객센터",
                onClick = onServiceCenterClick,
            )
        }
    }
}

@Composable
private fun InfoText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = SSINGTheme.typography.caption.md12,
        color = SSINGTheme.colors.textAlternative,
        modifier = modifier.clickable(onClick = onClick)
    )
}

@Preview(showBackground = true)
@Composable
private fun ConsumerLoginScreenPreview() {
    SSINGTheme {
        ConsumerLoginScreen(
            state = LoginContract.State(),
            onKakaoClick = {},
            onConditionClick = {},
            onPersonalInfoClick = {},
            onServiceCenterClick = {},
        )
    }
}
