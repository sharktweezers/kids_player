plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("app-level-plugin") {
            id = libs.plugins.app.level.plugin.get().pluginId
            implementationClass = "plugins.AppLevelPlugin"
        }
        register("module-level-plugin") {
            id = libs.plugins.module.level.plugin.get().pluginId
            implementationClass = "plugins.ModuleLevelPlugin"
        }
    }
}

dependencies {
    compileOnly(libs.android.tools.build.gradle)
    compileOnly(libs.kotlin.gradle.plugin)
}