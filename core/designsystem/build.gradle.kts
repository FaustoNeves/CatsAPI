plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.fausto.designsystem"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {
    //Core
    implementation(libs.androidx.ktx)
    //Appcompat
    implementation(libs.androidx.appcompat)

    //Compose, Material
    val composeBom = platform(libs.compose.bom)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.compose.tooling.preview)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.androidx.compose.tooling)
    implementation(libs.material)
    implementation(libs.coil.compose)
    implementation(libs.coil.network)
    
    implementation("com.squareup.picasso:picasso:2.8")
    val lottieVersion = "6.6.2"
    implementation("com.airbnb.android:lottie:$lottieVersion")
    implementation(project(":core:texts"))
    implementation(project(":core:model"))
}