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
    }
}

rootProject.name = "Cats"
include(":app")
include(":core:model")
include(":core:common")
include(":core:network")
include(":core:data")
include(":core:domain")
include(":feature:breeds")
include(":core:designsystem")
include(":feature:breeddetails")
include(":core:datastore")
include(":core:tracking")
include(":core:texts")
