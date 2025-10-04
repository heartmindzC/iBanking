plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.ibanking2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ibanking2"
        minSdk = 24
        targetSdk = 35
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.29")


    // Call Api
    implementation("com.google.code.gson:gson:2.13.2")
    // Retrofit core
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    // Converter: Retrofit sử dụng Gson để convert JSON -> POJO
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}