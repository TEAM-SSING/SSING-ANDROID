import java.util.Properties

plugins {
    id("ssing.android.application")
    id("org.jetbrains.kotlin.plugin.serialization")
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.ssing.consumer"

    defaultConfig {
        applicationId = "com.ssing.consumer"
        versionCode = 1
        versionName = "1.0"

        val kakaoNativeAppKey = properties["KAKAO_NATIVE_APP_KEY"].toString()
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"$kakaoNativeAppKey\"")
        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = kakaoNativeAppKey
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.presentation.auth)
    implementation(projects.presentation.notification)
    implementation(projects.presentation.consumerHome)
    implementation(projects.presentation.consumerMatching)
    implementation(projects.presentation.consumerPayment)
    implementation(projects.presentation.consumerLesson)
    implementation(libs.immutable)
    implementation(libs.kakao.user)
}
