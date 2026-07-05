plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.compiler.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.hilt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "ssing.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidApplication") {
            id = "ssing.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("coreUi") {
            id = "ssing.core.ui"
            implementationClass = "CoreUiConventionPlugin"
        }
        register("coreNetwork") {
            id = "ssing.core.network"
            implementationClass = "CoreNetworkConventionPlugin"
        }
        register("coreNotification") {
            id = "ssing.core.notification"
            implementationClass = "CoreNotificationConventionPlugin"
        }
        register("coreLocalstorage") {
            id = "ssing.core.localstorage"
            implementationClass = "CoreLocalstorageConventionPlugin"
        }
        register("data") {
            id = "ssing.data"
            implementationClass = "DataConventionPlugin"
        }
        register("presentation") {
            id = "ssing.presentation"
            implementationClass = "PresentationConventionPlugin"
        }
    }
}
