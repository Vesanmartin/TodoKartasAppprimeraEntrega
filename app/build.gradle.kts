plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)


// Agregar esto para habilitar KAPT

    kotlin("kapt")

}

android {
    namespace = "com.example.login001v"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.login001v"
        minSdk = 24
        targetSdk = 36
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

    //  implementation(libs.androidx.compose.runtime.livedata)

    // Option 1: If you add it to libs.versions.toml
    //implementation(libs.androidx.compose.runtime.livedata)
    // OR Option 2: Direct notation
    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")

    // Dependencia para la navegación con Jetpack Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")

// Íconos (core opcional) y EXTENDIDOS (¡este es el clave!)
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")

    // Dependencias Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.compose.material3)  // Versión actualizada
    kapt("androidx.room:room-compiler:2.6.1")          // Misma versión
    implementation("androidx.room:room-ktx:2.6.1")     // Misma versión

    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")

// CameraX
    val camerax_version = "1.3.3"
    implementation("androidx.camera:camera-core:$camerax_version")
    implementation("androidx.camera:camera-camera2:$camerax_version")
    implementation("androidx.camera:camera-lifecycle:$camerax_version")
    implementation("androidx.camera:camera-view:$camerax_version")

// ZXing para leer QR
    implementation("com.google.zxing:core:3.5.3")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")

    // ML Kit Barcode Scanning
    implementation("com.google.mlkit:barcode-scanning:17.2.0")


    //Retrofit y Gson Converter
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    //corrutinas para trabajo asincrono
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    // Kotest
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")

    //JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")

    // MockK
    testImplementation("io.mockk:mockk:1.13.10")

    // Compose UI Test
    androidTestImplementation("anrdoidx.compose.ui:ui-test-junit4:1.6.2")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.2")

    // Dependencia de Coil para Compose (se usa para cargar imagenes desde la api)
    implementation("io.coil-kt:coil-compose:2.5.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Obligatorio para usar JUnit 5
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }

}