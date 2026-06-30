import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class DataConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<AndroidLibraryConventionPlugin>()
            with(pluginManager) {
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }
            dependencies {
                add("implementation", libs.findLibrary("hilt.android").get())
                add("ksp", libs.findLibrary("hilt.compiler").get())
                add("implementation", libs.findLibrary("retrofit.core").get())
                add("implementation", libs.findLibrary("retrofit.kotlin.serialization").get())
                add("implementation", libs.findLibrary("okhttp.core").get())
                add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
                add("implementation", libs.findLibrary("datastore").get())
                add("implementation", project(":core:network"))
                add("implementation", project(":core:localstorage"))
                add("implementation", libs.findLibrary("krossbow.stomp.core").get())
                add("implementation", libs.findLibrary("krossbow.websocket.okhttp").get())
            }
        }
    }
}
