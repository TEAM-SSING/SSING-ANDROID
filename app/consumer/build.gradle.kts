import java.util.Properties

plugins {
    id("ssing.android.application")
    id("org.jetbrains.kotlin.plugin.serialization")
}

val properties = Properties().apply{
    val localProperties = rootProject.file("local.properties")

    if (localProperties.exists()) {
        load(localProperties.inputStream())
    }
}

android {
    namespace = "com.ssing.consumer"

    defaultConfig {
        applicationId = "com.ssing.consumer"
        versionCode = 1
        versionName = "1.0"

        val kakaoNativeAppKey = properties.getProperty("KAKAO_NATIVE_APP_KEY").orEmpty()
        require(kakaoNativeAppKey.isNotBlank()) {
            "로컬 프로퍼티 설정"
        }

        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"$kakaoNativeAppKey\"")
        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = kakaoNativeAppKey
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.network)
    implementation(projects.presentation.auth)
    implementation(projects.presentation.notification)
    implementation(projects.presentation.consumerHome)
    implementation(projects.presentation.consumerProfile)
    implementation(projects.presentation.consumerMatching)
    implementation(projects.presentation.consumerPayment)
    implementation(projects.presentation.consumerLesson)
    implementation(libs.immutable)
    implementation(libs.material)
    implementation(libs.kakao.user)
}
