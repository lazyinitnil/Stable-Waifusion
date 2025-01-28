import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.stablewaifusionalpha"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.stablewaifusionalpha"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val localProperties = Properties()
        val localPropertiesFile = project.rootProject.file("local.properties")
        if (localPropertiesFile.exists()){
            localProperties.load(FileInputStream(localPropertiesFile))
        }

        val baseUrl: String = localProperties.getProperty("BASE_URL") ?: ""
        val basicRealismModelPath: String = localProperties.getProperty("BASIC_REALISM_MODEL_PATH") ?: ""
        val mediumRealismModelPath: String = localProperties.getProperty("MEDIUM_REALISM_MODEL_PATH") ?: ""
        val mediumRealism2ModelPath: String = localProperties.getProperty("MEDIUM_REALISM_2_MODEL_PATH") ?: ""
        val mediumAnimeModelPath: String = localProperties.getProperty("MEDIUM_ANIME_MODEL_PATH") ?: ""

        buildConfigField(type = "String", name = "BASE_URL", value = "\"baseUrl\"")
        buildConfigField(type = "String", name = "BASIC_REALISM_MODEL_PATH", value = "\"basicRealismModelPath\"")
        buildConfigField(type = "String", name = "MEDIUM_REALISM_MODEL_PATH", value = "\"mediumRealismModelPath\"")
        buildConfigField(type = "String", name = "MEDIUM_REALISM_2_MODEL_PATH", value = "\"mediumRealism2ModelPath\"")
        buildConfigField(type = "String", name = "MEDIUM_ANIME_MODEL_PATH", value = "\"mediumAnimeModelPath\"")

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
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}


dependencies {
    implementation(libs.retrofit)
    implementation (libs.converter.gson)
    implementation(libs.okhttp)
    implementation (libs.coil.kt.coil.compose)
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.runtime.livedata)
    implementation (libs.androidx.material)

    implementation (libs.ui.tooling)
    implementation (libs.androidx.navigation.compose)
    implementation (libs.material3)
    implementation (libs.androidx.material.icons.extended)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation (libs.jetbrains.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    implementation (libs.lottie.compose)

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
}