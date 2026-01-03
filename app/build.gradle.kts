plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.ressencebb2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ressencebb2"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    
    buildTypes {
        release {
            minifyEnabled = false
        }
    }
}
