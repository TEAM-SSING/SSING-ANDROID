package com.ssing.presentation.auth

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import jakarta.inject.Inject
import timber.log.Timber

class KakaoLoginManager @Inject constructor() {
    fun login(
        context: Context,
        onResult: (Result<OAuthToken>) -> Unit
    ) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    Timber.d("🍫 카카오톡 로그인 취소")
                    return@loginWithKakaoTalk
                }
                when {
                    token != null -> {
                        Timber.i("🍫 카카오톡 로그인 성공")
                        onResult(Result.success(token))
                    }
                    error != null -> {
                        Timber.e(error, "🍫 카카오톡 로그인 실패 -> 카카오 계정 로그인 시도")
                        UserApiClient.instance.loginWithKakaoAccount(context) { token2, error2 ->
                            if (token2 != null) {
                                Timber.i("🍫 카카오 계정 로그인 성공")
                                onResult(Result.success(token2))
                            } else {
                                Timber.e(error2, "🍫 카카오계정 로그인 실패")
                                onResult(Result.failure(error2 ?: Exception("Unknown error")))
                            }
                        }
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                if (token != null) {
                    Timber.i("🍫 카카오 계정 로그인 성공")
                    onResult(Result.success(token))
                } else {
                    Timber.e(error, "🍫 카카오 계정 로그인 실패")
                    onResult(Result.failure(error ?: Exception("Unknown error")))
                }
            }
        }
    }
}

@EntryPoint
@InstallIn(ActivityComponent::class)
interface KakaoLoginEntryPoint {
    fun kakaoLoginManager(): KakaoLoginManager
}