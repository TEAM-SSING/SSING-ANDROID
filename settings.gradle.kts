pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Ssing-Android"
include(":core:ui")
include(":core:localstorage")
include(":core:network")
include(":data")
include(":presentation:auth")
include(":presentation:notification")
include(":presentation:consumer-home")
include(":presentation:consumer-matching")
include(":presentation:consumer-payment")
include(":presentation:consumer-lesson")
include(":presentation:instructor-home")
include(":presentation:instructor-matching")
include(":app:consumer")
include(":app:instructor")
