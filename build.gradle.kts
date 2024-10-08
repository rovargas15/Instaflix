// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.deteck) apply false
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
}

tasks.register<Exec>("pipeline") {
    description = "Executes the pipeline.sh script"
    group = "CI/CD"
    commandLine("./pipeline.sh", "-f")
}