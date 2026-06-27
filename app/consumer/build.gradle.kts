plugins {
    id("ssing.android.application")
}

android {
    namespace = "com.ssing.consumer"

    defaultConfig {
        applicationId = "com.ssing.consumer"
        versionCode = 1
        versionName = "1.0"
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
}
