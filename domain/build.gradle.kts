plugins {
    id("com.dsokolov.kidsplayer.module.properties")
}

android {
    namespace = "com.dsokolov.kidsplayer.domain"
}

dependencies {
    implementation(project(":utils"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // dagger2
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
}