plugins {
    id ("com.android.application")
    id ("com.google.dagger.hilt.android")
    id ("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    kotlin("kapt")
}
android {
    namespace = ("com.example.bettingdemoapp")
    compileSdk = 33

    defaultConfig {
        applicationId =("com.example.bettingdemoapp")
        minSdk=21
        targetSdk=33
        versionCode=1
        versionName =("1.0")

        testInstrumentationRunner= ("androidx.test.runner.AndroidJUnitRunner")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = false
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation ("androidx.core:core-ktx:1.9.0")
    implementation ("androidx.appcompat:appcompat:1.6.0")
    implementation ("com.google.android.material:material:1.7.0")
    implementation("com.google.dagger:hilt-android:2.44.2")
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation ("androidx.core:core-splashscreen:1.0.0")

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    kapt("com.google.dagger:hilt-android-compiler:2.44.2")

    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.github.bumptech.glide:glide:4.14.2")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation ("com.google.code.gson:gson:2.9.1")


}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}