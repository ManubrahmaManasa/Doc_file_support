plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.doc_file_support"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.doc_file_support"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(files("libs/poi-5.2.5.jar"))
    implementation(files("libs/poi-ooxml-5.2.5.jar"))
    implementation(files("libs/poi-scratchpad-5.2.5.jar"))
    implementation(files("libs/log4j-api-2.21.1.jar"))
    implementation(files("libs/commons-io-2.15.0.jar"))
    implementation (files("libs/pdfbox-3.0.1.jar"))

    /*implementation (files("libs/pdfbox-3.0.1.jar"))

    implementation ("org.apache.tika:tika-core:2.3.0")
    implementation ("org.jsoup:jsoup:1.14.3")*/









}