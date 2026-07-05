package com.ssing.presentation.auth

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakao.sdk.user.UserApiClient
import com.ssing.core.ui.extension.toast
import com.ssing.core.ui.util.HandleUiEffects
import dagger.hilt.android.EntryPointAccessors
import timber.log.Timber

@Composable
internal fun LoginRoute(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val kakaoLoginManager = remember(context) {
        val activity = context as? Activity ?: throw IllegalStateException("Context is not an Activity")
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
                            LoginContract.LoginIntent.OnKakaoLoginSuccess(token.accessToken)
                        )
                    }.onFailure { error ->
                        viewModel.processIntent(
                            LoginContract.LoginIntent.OnKakaoLoginFailure(error.message ?: "카카오 로그인 실패")
                        )
                    }
                }
            }
        }
    }

    LoginScreen(
        state = state,
        onKakaoClick = { viewModel.processIntent(LoginContract.LoginIntent.OnKakaoLoginClick) },
        modifier = modifier,
    )
}

@Composable
private fun LoginScreen(
    state: LoginContract.State,
    onKakaoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "로그인")

        // [TEST] 카카오톡 로그인 테스트용 버튼
        Button(
            onClick = onKakaoClick,
            modifier = Modifier.padding(top = 80.dp)
        ) {
            Text("임시 로그인 버튼")
        }

        // [TEST] 카카오톡 로그아웃 버튼 (자동 로그인 방지)
        Button(
            modifier = Modifier.padding(top = 180.dp),
            onClick = {
                UserApiClient.instance.unlink { error ->
                    if (error != null) {
                        Timber.e(error, "카카오 연결 해제 실패")
                    } else {
                        Timber.i("카카오 연결 해제 성공")
                    }
                }
            }
        ) {
            Text("카카오 연결 해제")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        state = LoginContract.State(),
        onKakaoClick = {},
    )
}
