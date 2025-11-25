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
        // THÊM DÒNG NÀY ĐỂ SỬA LỖI
        maven("https://maven.google.com/ai")
    }
}

rootProject.name = "ChuXiPetCare"
include(":app")

