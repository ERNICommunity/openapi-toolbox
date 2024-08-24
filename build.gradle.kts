plugins {
    kotlin("jvm") version "1.9.23"
    id("org.openapi.generator") version "7.8.0"
}

group = "erni.dev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

openApiGenerate {

    generatorName.set("java")
    inputSpec.set("$rootDir/src/main/openapi/hiking-routes-api.yml")
    outputDir.set("$buildDir/generated-java")
//    library.set("resttemplate")
    library.set("native")
    templateResourcePath.set("$rootDir/src/main/resources")


    apiPackage.set("erni.dev.openapitoolbox.api")
    invokerPackage.set("erni.dev.openapitoolbox.invoker")
    modelPackage.set("erni.dev.openapitoolbox.model")
    configOptions.put("dateLibrary", "java8")
}

dependencies {

    val jackson_version = "2.17.1"
    val jakarta_annotation_version = "1.3.5"
    val junit_version = "5.10.2"
    val findbugs_version = "3.0.2"
    val openapitools_jackson_databind_nullable_version = "0.2.1"

    implementation("com.fasterxml.jackson.core:jackson-core:$jackson_version")
    implementation("com.google.code.findbugs:jsr305:$findbugs_version")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jackson_version")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jackson_version")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jackson_version")
    implementation("org.openapitools:jackson-databind-nullable:$openapitools_jackson_databind_nullable_version")
    implementation("jakarta.annotation:jakarta.annotation-api:$jakarta_annotation_version")
    implementation("org.junit.jupiter:junit-jupiter-api:$junit_version")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit_version")
}

sourceSets {
    main {
        // TODO: define as _generated_ / _test_ source root
        java.srcDir("build/generated-java/src/main/java")
        java.srcDir("build/generated-java/src/test/java")
    }
}
