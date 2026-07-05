plugins {
    id("ssing.presentation")
}

android {
    namespace = "com.ssing.presentation.auth"
}

dependencies {
    implementation(libs.kakao.user)
}
