// File: app/build.gradle.kts

// Tidak perlu lagi mengimpor 'java.util.Properties' dan 'java.io.FileInputStream'

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

// Bagian untuk membaca local.properties sudah dihapus seluruhnya karena tidak diperlukan.

android {
    namespace = "com.dhirekhaf.mytype"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.dhirekhaf.mytype"
        minSdk = 26
        targetSdk = 36
        versionCode = 2
        versionName = "1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // Baris 'buildConfigField' untuk API_AUTH_TOKEN sudah dihapus dari sini.
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Disarankan untuk membuat signing config release Anda sendiri,
            // tetapi untuk saat ini menggunakan debug sudah cukup untuk build.
            signingConfig = signingConfigs.getByName("debug")
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
        compose = true
        // Tetap aktifkan 'buildConfig' jika Anda punya BuildConfig field lain di masa depan.
        // Jika tidak ada sama sekali, ini bisa dihapus bersama dengan blok buildFeatures.
        // Untuk saat ini, biarkan saja agar aman.
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // --- Core & Material Design ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    // --- Jetpack Compose ---
    implementation(platform(libs.androidx.compose.bom))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.animation:animation") // Untuk animasi
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1")

    // --- Coil (Image Loading) ---
    implementation("io.coil-kt:coil-compose:2.7.0")

    // --- Navigasi ---
    implementation(libs.androidx.navigation.compose)

    // --- Activity & Lifecycle ---
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.activity.ktx)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.4")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.4")
    implementation("dev.shreyaspatil:capturable:1.0.3")
// --- MENJADI SEPERTI INI ---


    // --- Data Persistence ---
    implementation("androidx.datastore:datastore-preferences:1.1.7")

    // --- Paging 3 ---
    implementation("androidx.paging:paging-compose:3.3.6")

    // --- Accompanist Libraries ---
    implementation("com.google.accompanist:accompanist-flowlayout:0.36.0")
    implementation("com.google.accompanist:accompanist-placeholder-material:0.36.0")

    // --- Networking (Tidak digunakan, tapi ada di dependensi Anda) ---
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.2.0")
    implementation("com.google.code.gson:gson:2.13.2")

    // --- Testing ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // --- Debugging ---
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


}
