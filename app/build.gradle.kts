plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)
    kotlin(libs.plugins.kapt.get().pluginId)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.fausto.cats"
    compileSdk = 36

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.fausto.cats"
        minSdk = 24
        targetSdk = 36
        versionCode = 12
        versionName = "10.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            val storeFile = project.findProperty("RELEASE_STORE_FILE") as? String ?: System.getenv("KEYSTORE_FILE")
            val storePassword = project.findProperty("RELEASE_STORE_PASSWORD") as? String ?: System.getenv("KEYSTORE_PASS")
            val keyAlias = project.findProperty("RELEASE_KEY_ALIAS") as? String ?: System.getenv("ALIAS")
            val keyPassword = project.findProperty("RELEASE_KEY_PASSWORD") as? String ?: System.getenv("KEY_PASS")

            if (storeFile != null) {
                this.storeFile = file(storeFile)
                this.storePassword = storePassword
                this.keyAlias = keyAlias
                this.keyPassword = keyPassword
            }
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            resValue("string", "app_name", "Debug")
        }

        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            resValue("string", "app_name", "Cats")
        }

        create("qa") {
            initWith(getByName("debug"))
            resValue("string", "app_name", "QA")
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

    implementation(libs.androidx.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.1")
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation(libs.kotlinx.serialization.json)

    //Compose
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
    implementation(libs.navigation.compose)
    debugImplementation(libs.androidx.compose.tooling)

    implementation(project(":feature:breeds"))
    implementation(project(":feature:breeddetails"))
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:texts"))
}