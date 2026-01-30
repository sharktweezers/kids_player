package plugins

import com.android.build.api.dsl.LibraryExtension
import extension.getLibsFromVersionCatalog
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class ModuleLevelPlugin : Plugin<Project> {

    override fun apply(project: Project) = with(project) {
        project.pluginManager.apply("org.jetbrains.kotlin.kapt")
        val libs = getLibsFromVersionCatalog()
        configurePlugins(libs)

        extensions.configure<LibraryExtension> {
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
        plugins.apply(libs.findPlugin("android-library").get().get().pluginId)
        plugins.apply(libs.findPlugin("kotlin-android").get().get().pluginId)
        // plugins.apply(libs.findPlugin("kotlin-compose").get().get().pluginId)
    }

    private fun LibraryExtension.addSdkAndVersion(libs: VersionCatalog) {
        compileSdk = libs.findVersion("compileSdk").get().requiredVersion.toInt()
        defaultConfig {
            minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }
    }

    /*private fun LibraryExtension.addBuildFeatures() {
        buildFeatures {
            compose = true
        }
    }*/

    private fun LibraryExtension.createBuildTypes() {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }

    private fun LibraryExtension.addCompileOptions() {
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