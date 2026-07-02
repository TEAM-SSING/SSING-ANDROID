import java.util.Properties
import kotlin.apply

plugins {
    id("ssing.android.application")
    id("org.jetbrains.kotlin.plugin.serialization")
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.ssing.instructor"

    defaultConfig {
        applicationId = "com.ssing.instructor"
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
    implementation(projects.presentation.instructorHome)
    implementation(projects.presentation.instructorMatching)
    implementation(libs.immutable)
    implementation(libs.kakao.user)
}
