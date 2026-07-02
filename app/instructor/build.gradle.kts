plugins {
    id("ssing.android.application")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.ssing.instructor"

    defaultConfig {
        applicationId = "com.ssing.instructor"
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
    implementation(projects.presentation.instructorHome)
    implementation(projects.presentation.instructorMatching)
    implementation(libs.immutable)
    implementation(libs.material)
}
