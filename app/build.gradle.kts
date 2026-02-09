plugins {
    id("com.dsokolov.kidsplayer.app.properties")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.dsokolov.kidsplayer"

    defaultConfig {
        applicationId = "com.dsokolov.kidsplayer"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":injector"))
    implementation(project(":utils"))
    implementation(project(":mvi_core"))
    implementation(project(":domain"))
    implementation(project(":remote"))
    implementation(project(":resources"))
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // dagger2
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}