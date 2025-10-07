// File: app/build.gradle.kts
import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

// +++ KODE UNTUK MEMBACA FILE local.properties +++
// Tambahkan bagian ini persis di atas blok 'android'
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}
// +++ AKHIR BAGIAN BARU +++

android {
    namespace = "com.dhirekhaf.mytype"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.dhirekhaf.mytype"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            type = "String",
            name = "API_AUTH_TOKEN",
            // HAPUS TANDA KUTIP YANG DI-ESCAPE (\"...\") DARI SINI
            value = localProperties.getProperty("API_AUTH_TOKEN", "DEFAULT_DUMMY_TOKEN")
        )
        // +++ AKHIR BAGIAN BARU +++
    }

    buildTypes {
        release {
            // [UBAH INI] dari isMinifyEnabled = false menjadi true
            isMinifyEnabled = true

            // [TAMBAHKAN INI] untuk menghapus sumber daya yang tidak digunakan
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        // +++ KODE UNTUK MENGAKTIFKAN BUILDCONFIG +++
        buildConfig = true // Baris ini sangat penting
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// Dependensi Anda tidak perlu diubah, tetap sama seperti sebelumnya
dependencies {
    // --- Core & Material Design ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    // --- Jetpack Compose ---
    implementation(platform(libs.androidx.compose.bom))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // --- Coil ---
    implementation("io.coil-kt:coil-compose:2.7.0")

    // --- Navigasi ---
    implementation(libs.androidx.navigation.compose)

    // --- Activity & Lifecycle ---
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.activity.ktx)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.4")

    // --- Data Persistence ---
    implementation("androidx.datastore:datastore-preferences:1.1.7")

    // --- Paging 3 ---
    implementation("androidx.paging:paging-compose:3.3.6")

    // --- Testing ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // --- Debugging ---
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // --- Networking (Retrofit & OkHttp) ---
    // Menggunakan versi yang lebih stabil untuk menghindari konflik
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.1.0")
    // Tambahkan ini jika belum ada, biasanya diletakkan bersama dependensi lain
    implementation("com.google.code.gson:gson:2.13.2") // Versi terbaru dan stabil
    implementation("androidx.compose.ui:ui")

        // ... dependensi lain
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.4") // <-- TAMBAHKAN BARIS INI
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.4")
    // ... dependensi lain

    implementation("androidx.compose.material3:material3")

}
