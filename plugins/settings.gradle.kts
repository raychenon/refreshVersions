import de.fayard.refreshVersions.bootstrapRefreshVersions

buildscript {
    repositories {
        maven(url = "https://dl.bintray.com/jmfayard/maven")
        gradlePluginPortal()
    }
    dependencies.classpath("de.fayard.refreshVersions:refreshVersions:0.9.6-dev-006")
}

bootstrapRefreshVersions()

plugins {
    id("com.gradle.enterprise").version("3.1.1")
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlwaysIf(System.getenv("GITHUB_ACTIONS")?.toBoolean() == true)
    }
}

val pluginsVersion: String = file("version.txt").bufferedReader().use { it.readLine() }

gradle.beforeProject {
    version = pluginsVersion
    group = "de.fayard.refreshVersions"
    loadLocalProperties()
}

include("core", "dependencies")
project(":core").name = "refreshVersions-core"
project(":dependencies").name = "refreshVersions"

fun Project.loadLocalProperties() {
    val localPropertiesFile = rootDir.resolve("local.properties")
    if (localPropertiesFile.exists()) {
        val localProperties = java.util.Properties()
        localProperties.load(localPropertiesFile.inputStream())
        localProperties.forEach { (k, v) -> if (k is String) project.extra.set(k, v) }
    }
}
