plugins {
    id("com.dsokolov.kidsplayer.module.properties")
}

android {
    namespace = "com.dsokolov.kidsplayer.remote"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":resources"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    // dagger2
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
}