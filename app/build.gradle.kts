plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "com.thefork.challenge"
        minSdk = 21
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
    implementation("androidx.appcompat:appcompat:1.3.1")

    implementation(project(":search"))
}
