// File: settings.gradle.kts

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // Repositori JitPack untuk library seperti 'capturable'
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

rootProject.name = "MyType"
include(":app")
