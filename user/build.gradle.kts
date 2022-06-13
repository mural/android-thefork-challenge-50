plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 30

    defaultConfig {
        minSdk = 21
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
    implementation("androidx.appcompat:appcompat:1.3.1")
}
