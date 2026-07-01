plugins {
    id("ssing.android.application")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.gms.google-services")
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
    implementation(project(":core:fcm"))
    implementation(platform("com.google.firebase:firebase-bom:34.15.0"))
    implementation("com.google.firebase:firebase-analytics")
}
