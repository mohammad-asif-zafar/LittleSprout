import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// ------------------------------------------------------------
// Android release signing
//
// Locally:
//   No keystore is required.
//
// GitHub Actions:
//   These environment variables are supplied by the workflow.
// ------------------------------------------------------------

val releaseKeystorePath = System.getenv("ANDROID_KEYSTORE_PATH")
val hasReleaseSigning = !releaseKeystorePath.isNullOrBlank()

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

dependencies {
    implementation(project(":shared"))

    implementation(libs.androidx.activity.compose)

    implementation(libs.compose.uiToolingPreview)
    debugImplementation(libs.compose.uiTooling)
}

android {
    namespace = "com.hathway.littlesprout"

    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.hathway.littlesprout"

        minSdk = libs.versions.android.minSdk.get().toInt()

        targetSdk = libs.versions.android.targetSdk.get().toInt()

        // ----------------------------------------------------
        // Version
        //
        // Local build:
        //   versionCode = 2
        //   versionName = 1.0.2
        //
        // GitHub release:
        //   Values come from the Git tag.
        //
        // Example:
        //   v1.0.3
        //
        // becomes:
        //   versionName = 1.0.3
        //   versionCode = 1000003
        // ----------------------------------------------------

        versionCode =
            (System.getenv("ANDROID_VERSION_CODE") ?: "2").toInt()

        versionName =
            System.getenv("ANDROID_VERSION_NAME") ?: "1.0.2"
    }

    // ----------------------------------------------------------
    // Release signing
    // ----------------------------------------------------------

    signingConfigs {
        create("release") {
            if (hasReleaseSigning) {
                storeFile = file(releaseKeystorePath!!)

                storePassword =
                    System.getenv("ANDROID_KEYSTORE_PASSWORD")

                keyAlias =
                    System.getenv("ANDROID_KEY_ALIAS")

                keyPassword =
                    System.getenv("ANDROID_KEY_PASSWORD")
            }
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true

            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )

            ndk {
                debugSymbolLevel = "SYMBOL_TABLE"
            }

            // ------------------------------------------------
            // Use the release signing key when running CI.
            //
            // Your normal local debug builds don't need it.
            // ------------------------------------------------

            if (hasReleaseSigning) {
                signingConfig =
                    signingConfigs.getByName("release")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11

        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}