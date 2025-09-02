// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.11.1" apply false

    id("org.jetbrains.kotlin.android") version "2.2.10" apply false

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.3" apply false

    // Add the dependency for the Crashlytics Gradle plugin
    id("com.google.firebase.crashlytics") version "3.0.6" apply false

    id("org.jetbrains.kotlin.plugin.compose") version "2.2.10" apply false
}