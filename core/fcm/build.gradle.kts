plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("ssing.core.network")
}

android {
    namespace = "com.ssing.core.fcm"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(platform(libs.findLibrary("firebase-bom").get()))
    implementation(libs.findLibrary("firebase-messaging").get())
    implementation(projects.data)
}
