import java.io.FileInputStream
import java.util.Properties

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {

        val properties = Properties().apply {
            load(FileInputStream(File("local.properties")))
        }
        maven {
            url = uri("https://maven.pkg.github.com/sunfox-technologies/spandan_ecg_android_sdk")
            credentials {
                username = properties.getProperty("repo.username")
                password = properties.getProperty("repo.token")
            }
        }
        maven {
            url = uri("https://jitpack.io")
        }

        google()
        mavenLocal()
        mavenCentral()
    }
}

rootProject.name = "SpandanUiPOC"
include(":app")
 