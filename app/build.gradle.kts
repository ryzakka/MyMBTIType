// File: app/build.gradle.kts
// [PERBAIKAN] - Mengembalikan dependensi Gson yang diperlukan untuk parsing JSON lokal.

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

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
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    implementation("androidx.compose.animation:animation")

    // --- Coil (Image Loading) ---
    implementation("io.coil-kt:coil-compose:2.7.0")

    // --- Navigasi ---
    implementation(libs.androidx.navigation.compose)

    // --- Activity & Lifecycle ---
    implementation(libs.androidx.activity.compose)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.4")

    // --- UI Utilities ---
    implementation("dev.shreyaspatil:capturable:1.0.3")
    implementation("com.google.accompanist:accompanist-flowlayout:0.36.0")

    // --- Data Persistence ---
    implementation("androidx.datastore:datastore-preferences:1.1.7")

    // --- [DIKEMBALIKAN] JSON Parsing ---
    // Diperlukan untuk membaca questions.json dari assets.
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
