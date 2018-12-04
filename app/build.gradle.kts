plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    buildToolsVersion = Deps.build_tools_version

    lintOptions {
        isAbortOnError = true
        isExplainIssues = true
        isIgnoreWarnings = true
        textReport = true
        textOutput("stdout")
        // Should try to remove last two here
        disable("MissingTranslation", "AppCompatCustomView", "InvalidPackage")
        // I really want some to show as errors
        error("InlinedApi", "StringEscaping")
    }

    defaultConfig {
        applicationId = "com.nononsenseapps.feeder"
        versionCode = 42
        versionName = "1.8.3"
        compileSdkVersion(28)
        minSdkVersion(18)
        targetSdkVersion(28)

        vectorDrawables.useSupportLibrary = true

        // For espresso tests
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Export Room schemas
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }

    sourceSets {
        // To test Room we need to include the schema dir in resources
        getByName("androidTest").assets.srcDir(File("$projectDir/schemas"))
    }

    buildTypes {
        named("debug") {
            isMinifyEnabled = false
        }
        named("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    packagingOptions {
        // Rome incorrectly bundles stuff in its jar
        pickFirst("rome-utils-${Deps.rome_version}.jar")
    }

    compileOptions {
        setSourceCompatibility(JavaVersion.VERSION_1_8)
        setTargetCompatibility(JavaVersion.VERSION_1_8)
    }
}

kapt {
    useBuildCache = true
}

dependencies {
    kapt("androidx.room:room-compiler:${Deps.room_version}")
    implementation("androidx.room:room-runtime:${Deps.room_version}")

    implementation("android.arch.work:work-runtime-ktx:${Deps.work_version}")

    implementation("androidx.constraintlayout:constraintlayout:${Deps.constraintlayout_version}")
    implementation("androidx.recyclerview:recyclerview:${Deps.recyclerview_version}")
    implementation("androidx.legacy:legacy-support-v4:${Deps.legacy_support_version}")
    implementation("androidx.appcompat:appcompat:${Deps.appcompat_version}")
    implementation("androidx.preference:preference:${Deps.preference_version}")
    implementation("com.google.android.material:material:${Deps.material_version}")

    // ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-extensions:${Deps.lifecycle_version}")
    implementation("androidx.paging:paging-runtime:${Deps.paging_version}")

    // To support SDK18
    implementation("com.nononsenseapps:filepicker:4.1.0")
    // Better times
    implementation("joda-time:joda-time:2.3")
    // HTML parsing
    implementation("org.jsoup:jsoup:1.7.3")
    implementation("org.ccil.cowan.tagsoup:tagsoup:1.2.1")
    // RSS
    implementation("com.rometools:rome:${Deps.rome_version}")
    implementation("com.rometools:rome-modules:${Deps.rome_version}")
    // JSONFeed
    implementation(project(":jsonfeed-parser"))
    // For better fetching
    implementation("com.squareup.okhttp3:okhttp:${Deps.okhttp_version}")
    // For supporting missing cyphers on older platforms
    implementation("org.conscrypt:conscrypt-android:${Deps.conscrypt_version}")
    // Image loading
    implementation("com.github.bumptech.glide:glide:3.7.0")
    implementation("com.github.bumptech.glide:okhttp3-integration:1.4.0@aar")


    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Deps.kotlin_version}")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Deps.coroutines_version}")
    // For doing coroutines on UI thread
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Deps.coroutines_version}")
    // tests
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib:${Deps.kotlin_version}")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${Deps.kotlin_version}")
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:2.13.0")
    testImplementation("io.mockk:mockk:1.8.10.kotlin13")
    testImplementation("com.squareup.okhttp3:mockwebserver:${Deps.okhttp_version}")

    androidTestImplementation("org.jetbrains.kotlin:kotlin-stdlib:${Deps.kotlin_version}")
    androidTestImplementation("org.jetbrains.kotlin:kotlin-test-junit:${Deps.kotlin_version}")
    androidTestImplementation("io.mockk:mockk-android:1.8.10.kotlin13")
    androidTestImplementation("junit:junit:4.12")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:${Deps.okhttp_version}")

    androidTestImplementation("androidx.test:core:${Deps.androidx_version}")
    androidTestImplementation("androidx.test:runner:${Deps.test_runner_version}")
    androidTestImplementation("androidx.test:rules:${Deps.test_rules_version}")
    androidTestImplementation("androidx.test.ext:junit:${Deps.test_ext_junit_version}")
    androidTestImplementation("androidx.recyclerview:recyclerview:${Deps.recyclerview_version}")
    androidTestImplementation("androidx.legacy:legacy-support-v4:${Deps.legacy_support_version}")
    androidTestImplementation("androidx.appcompat:appcompat:${Deps.appcompat_version}")
    androidTestImplementation("com.google.android.material:material:${Deps.material_version}")
    androidTestImplementation("androidx.room:room-testing:${Deps.room_version}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Deps.espresso_version}")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:${Deps.espresso_version}")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:${Deps.uiautomator_version}")
}
