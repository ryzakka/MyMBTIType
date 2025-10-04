plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
        id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" // this version matches your Kotlin version
}

android {
    namespace = "com.dhirekhaf.mytype"
    compileSdk = 36 // Anda bisa menggunakan 34 jika 36 menyebabkan masalah

    defaultConfig {
        applicationId = "com.dhirekhaf.mytype"
        minSdk = 31 // Anda bisa menggunakan 24 jika 31 terlalu tinggi
        targetSdk = 36 // Anda bisa menggunakan 34 jika 36 menyebabkan masalah
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    // Kita hapus buildFeatures Compose untuk sementara
    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // TAMBAHKAN DEPENDENSI COMPOSE BERIKUT
    implementation(platform("androidx.compose:compose-bom:2024.09.00")) // Gunakan versi terbaru dari BOM
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.9.0") // Versi activity-compose yang relevan

    // Dependensi ini diperlukan untuk @Preview
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.09.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}