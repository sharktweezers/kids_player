plugins {
    id("com.dsokolov.kidsplayer.module.properties")
}

android {
    namespace = "com.dsokolov.kidsplayer.player_service"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":injector"))
    implementation(project(":utils"))
    implementation(project(":resources"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    // dagger2
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)
}