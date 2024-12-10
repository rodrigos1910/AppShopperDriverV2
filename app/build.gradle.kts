
plugins {


    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.ksp)

}

android {
    namespace = "com.example.appshopperdriver"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.appshopperdriver"
        minSdk = 24
        targetSdk = 34
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
        viewBinding = true
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
    tasks.withType<Test>().configureEach {
        jvmArgs(
            "--add-opens", "java.base/java.lang=ALL-UNNAMED",
            "--add-opens", "java.base/jdk.internal.reflect=ALL-UNNAMED"
        )
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)


    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Mapa Gratuito OpenStreetMap , alternativa ao google maps
    implementation(libs.osmdroid.android)

    //dependencias do google play service
    implementation(libs.play.services.location)

    //cardview
    implementation(libs.androidx.cardview)


    //Room DataBase
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.core.testing)
    implementation(libs.core.ktx)
    ksp(libs.androidx.room.compiler){
        exclude(group = "com.intellij", module = "annotations")
    }

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation( libs.adapter.rxjava2)


    // Testes Unitários
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.robolectric)

    // Testes Instrumentados
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.espresso.contrib)


}