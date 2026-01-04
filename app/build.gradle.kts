plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.ressencebb2"
    compileSdk = 35
    buildToolsVersion = "34.0.0"

    defaultConfig {
        applicationId = "com.example.ressencebb2"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    
    buildTypes {
        release {
            minifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    val wearWatchFaceVersion = "1.2.1"
    implementation("androidx.wear.watchface:watchface:$wearWatchFaceVersion")
    implementation("androidx.wear.watchface:watchface-complications-data:$wearWatchFaceVersion")
    implementation("androidx.wear.watchface:watchface-complications-rendering:$wearWatchFaceVersion")
    implementation("androidx.wear.watchface:watchface-editor:$wearWatchFaceVersion")
    implementation("androidx.wear.watchface:watchface-style:$wearWatchFaceVersion")
    implementation("androidx.wear.watchface:watchface-client:$wearWatchFaceVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-guava:1.7.3")
}
