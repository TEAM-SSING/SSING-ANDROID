package com.ssing.presentation.auth.instructor

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssing.core.ui.R
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.designsystem.theme.White
import com.ssing.core.ui.extension.toast
import com.ssing.core.ui.util.HandleUiEffects
import com.ssing.presentation.auth.KakaoLoginEntryPoint
import com.ssing.presentation.auth.LoginContract
import com.ssing.presentation.auth.component.LoginSection
import dagger.hilt.android.EntryPointAccessors

@Composable
internal fun InstructorLoginRoute(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InstructorLoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val activity = LocalActivity.current

    val kakaoLoginManager = remember(activity) {
        val activity = activity ?: throw IllegalStateException("Activity not found")
        EntryPointAccessors.fromActivity(
            activity,
            KakaoLoginEntryPoint::class.java
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

    InstructorLoginScreen(
        onKakaoClick = {
            viewModel.processIntent(LoginContract.Intent.OnKakaoClick)
        },
        onConditionClick = {},
        onServiceCenterClick = {},
        onPersonalInfoClick = {},
        modifier = modifier,
    )
}

@Composable
private fun InstructorLoginScreen(
    onKakaoClick: () -> Unit,
    onConditionClick: () -> Unit,
    onPersonalInfoClick: () -> Unit,
    onServiceCenterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SSINGTheme.colors.primaryNormal)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        InstructorLoginLogo()

        Spacer(modifier = Modifier.weight(1f))

        LoginSection(
            onKakaoClick = onKakaoClick,
            onConditionClick = onConditionClick,
            onPersonalInfoClick = onPersonalInfoClick,
            onServiceCenterClick = onServiceCenterClick,
            textColor = SSINGTheme.colors.primaryAlternative,
        )
    }
}

@Composable
private fun InstructorLoginLogo(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "강습 매칭부터 관리까지 쉽고 빠르게,",
            color = White,
            style = SSINGTheme.typography.body.sb16,
        )

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_instructor_login_logo_white),
            contentDescription = null,
            tint = Color.Unspecified,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InstructorLoginScreenPreview() {
    SSINGTheme {
        InstructorLoginScreen(
            onKakaoClick = {},
            onConditionClick = {},
            onPersonalInfoClick = {},
            onServiceCenterClick = {},
        )
    }
}