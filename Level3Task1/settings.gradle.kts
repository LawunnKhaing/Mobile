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
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        //        https://github.com/a914-gowtham/compose-ratingbar/blob/main/README.md
        maven { setUrl("https://jitpack.io")  }
    }
}

rootProject.name = "Level 3 Task 1"
include(":app")
