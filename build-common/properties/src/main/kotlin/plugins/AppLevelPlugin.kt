package plugins

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import extension.getLibsFromVersionCatalog
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AppLevelPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        project.pluginManager.apply("org.jetbrains.kotlin.kapt")
        val libs = getLibsFromVersionCatalog()
        configurePlugins(libs)

        extensions.configure<BaseAppModuleExtension> {
            addSdkAndVersion(libs)
            //addBuildFeatures()
            createBuildTypes()
            addCompileOptions()
            //addTestOptions()
        }

        project.tasks.withType(KotlinCompile::class.java).configureEach {
            kotlinOptions {
                jvmTarget = "11"
            }
        }

        /*extensions.configure<ApplicationAndroidComponentsExtension> {
            configureIgnoredVariants()
        }*/
    }

    private fun Project.configurePlugins(libs: VersionCatalog) {
        plugins.apply(libs.findPlugin("android-application").get().get().pluginId)
        plugins.apply(libs.findPlugin("kotlin-android").get().get().pluginId)
        // plugins.apply(libs.findPlugin("kotlin-compose").get().get().pluginId)
    }

    private fun BaseAppModuleExtension.addSdkAndVersion(libs: VersionCatalog) {
        compileSdk = libs.findVersion("compileSdk").get().requiredVersion.toInt()
        defaultConfig {
            minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
            targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
            targetSdkPreview = libs.findVersion("targetSdk").get().requiredVersion

            versionCode = libs.findVersion("app.version.code").get().requiredVersion.toInt()
            versionName = libs.findVersion("app.version.name").get().requiredVersion
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    /*private fun BaseAppModuleExtension.addBuildFeatures() {
        buildFeatures {
            compose = true
        }
    }*/

    private fun BaseAppModuleExtension.createBuildTypes() {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }

    private fun BaseAppModuleExtension.addCompileOptions() {
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }

    /*private fun BaseAppModuleExtension.addTestOptions() {
        testOptions {
            unitTests.isReturnDefaultValues = true
        }
    }*/

    /*private fun ApplicationAndroidComponentsExtension.configureIgnoredVariants() {
        beforeVariants { variantBuilder ->
            if (variantBuilder.productFlavors.contains("version" to "dev") &&
                variantBuilder.buildType == "release") {
                variantBuilder.enable = false
            }
            if (variantBuilder.productFlavors.contains("version" to "qa") &&
                variantBuilder.buildType == "release") {
                variantBuilder.enable = false
            }
            if (variantBuilder.productFlavors.contains("version" to "prod") &&
                variantBuilder.buildType == "debug") {
                variantBuilder.enable = false
            }
            if (variantBuilder.productFlavors.contains("version" to "prod") &&
                variantBuilder.buildType == "staging") {
                variantBuilder.enable = false
            }
        }
    }*/
}