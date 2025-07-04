plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.peoplearoundtheworld"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.peoplearoundtheworld"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.peoplearoundtheworld.utils.CustomTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.compose.runtime:runtime-livedata:1.8.3")
    //Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    androidTestImplementation(libs.hilt.android.testing)
    // ...with Kotlin.
    kaptAndroidTest(libs.hilt.android.compiler)
    testImplementation(libs.androidx.core.testing)


    //Retrofit
    implementation(libs.retrofit.v300)
    implementation("com.squareup.retrofit2:converter-gson:2.2.0")
    implementation (libs.gson)
    testImplementation ("com.squareup.okhttp3:mockwebserver:4.9.1")

    //Room
    val room_version = "2.7.1"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")


    implementation("com.valentinilk.shimmer:compose-shimmer:1.3.3")
    implementation("androidx.compose.foundation:foundation-layout:1.9.0-alpha04")

    //Coil para imagenes
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.2.0")
    implementation("io.coil-kt.coil3:coil-compose:3.2.0")

}