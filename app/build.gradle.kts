plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
}


android {
    // Tên gói chuẩn của bạn
    namespace = "com.example.chuxipetcare"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.chuxipetcare"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    // Bật tính năng này để code giao diện nhanh hơn
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Các thư viện mặc định của Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // --- FIREBASE (Kết nối cơ sở dữ liệu) ---
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0") //

    // --- AI GEMINI (Chat bot thông minh) ---
    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // --- MVVM (Mô hình kiến trúc code) ---
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation(libs.firebase.firestore)
    // THƯ VIỆN GLIDE (Hiển thị ảnh)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // Retrofit để gọi API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
// Gson để chuyển đổi dữ liệu JSON từ API về Kotlin Object
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}