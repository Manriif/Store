@file:Suppress("UnstableApiUsage")

import com.vanniktech.maven.publish.SonatypeHost.S01
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    kotlin("android")
    id("com.android.library")
    id("com.vanniktech.maven.publish")
    id("org.jetbrains.dokka")
    id("org.jetbrains.kotlinx.kover")
    `maven-publish`
    kotlin("native.cocoapods")
}

dependencies {
    implementation(libs.kotlinx.coroutines.rx2)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.rxjava)
    implementation(project(":store"))

    testImplementation(kotlin("test"))
    testImplementation(libs.junit)
    testImplementation(libs.google.truth)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.kotlinx.coroutines.test)
}

android {
    namespace = "org.mobilenativefoundation.store.rx2"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }

    lint {
        disable += "ComposableModifierFactory"
        disable += "ModifierFactoryExtensionFunction"
        disable += "ModifierFactoryReturnType"
        disable += "ModifierFactoryUnreferencedReceiver"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets.configureEach {
        reportUndocumented.set(false)
        skipDeprecated.set(true)
        jdkVersion.set(8)
    }
}

mavenPublishing {
    publishToMavenCentral(S01)
    signAllPublications()
}
