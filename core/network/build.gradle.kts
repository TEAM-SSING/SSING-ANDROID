plugins {
    id("ssing.core.network")
}

android {
    namespace = "com.ssing.core.network"
}

dependencies {
    implementation(projects.core.localstorage)
}
