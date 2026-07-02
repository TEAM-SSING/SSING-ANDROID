import java.util.Properties
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("ssing.core.network")
}

val libs = the<LibrariesForLibs>()

val localProperties = Properties().apply {
    rootProject.file("local.properties").takeIf { it.exists() }?.inputStream()?.use { load(it) }
}

android {
    namespace = "com.ssing.core.network"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"${localProperties.getProperty("BASE_URL", "")}\"")
        buildConfigField("String", "SOCKET_BASE_URL", "\"${localProperties.getProperty("SOCKET_BASE_URL", "")}\"")
    }
}

dependencies {
    implementation(projects.core.localstorage)
    implementation(libs.annotationExperimental)
}
