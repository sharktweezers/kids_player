plugins {
    id("com.dsokolov.kidsplayer.module.properties")
}

android {
    namespace = "com.dsokolov.kidsplayer.resources"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}