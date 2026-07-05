plugins {
    id("ssing.core.notification")
}

android {
    namespace = "com.ssing.core.notification"

    defaultConfig {
        minSdk = 28
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}
