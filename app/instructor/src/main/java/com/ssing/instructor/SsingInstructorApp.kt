package com.ssing.instructor

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.ssing.core.notification.PushNotificationService
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SsingInstructorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initKakaoSdk()
        PushNotificationService.createChannels(this)
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}