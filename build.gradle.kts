// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
//    alias(libs.plugins.devtools.ksp) apply false// <-- added this for fixing error with dagger hilt dependency
    alias(libs.plugins.dagger) apply false// <-- added this for fixing error with dagger hilt dependency
}