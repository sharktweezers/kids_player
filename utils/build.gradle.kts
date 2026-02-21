plugins {
    id("com.dsokolov.kidsplayer.module.properties")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.dsokolov.kidsplayer.utils"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.fragment)
    implementation(libs.fragment.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // dagger2
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
}