plugins {
    kotlin("jvm") version "1.9.23"
    id("org.openapi.generator") version "7.8.0"
//    id("org.frege-lang") version "0.8"
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
//    ${layout.buildDirectory}
    library.set("resttemplate")
    templateResourcePath.set("$rootDir/src/main/resources")
    additionalProperties.put("useOneOfInterfaces", true)
    additionalProperties.put("generateBuilders", true)

    apiPackage.set("erni.dev.openapitoolbox.api")
    invokerPackage.set("erni.dev.openapitoolbox.invoker")
    modelPackage.set("erni.dev.openapitoolbox.model")
    configOptions.put("dateLibrary", "java8")
    configOptions.put("useOneOfInterfaces", "true")
    configOptions.put("generateBuilders", "true")
    configOptions.put("useJakartaEe", "true")
}

dependencies {

    val jackson_version = "2.17.1"
//    val jakarta_annotation_version = "1.3.5"
    val jakarta_annotation_version = "2.1.1"
    val junit_version = "5.10.2"
    val findbugs_version = "3.0.2"
    val openapitools_jackson_databind_nullable_version = "0.2.1"
    val spring_web_version = "5.3.33"

    implementation("com.fasterxml.jackson.core:jackson-core:$jackson_version")
    implementation("com.google.code.findbugs:jsr305:$findbugs_version")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jackson_version")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jackson_version")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jackson_version")
    implementation("org.openapitools:jackson-databind-nullable:$openapitools_jackson_databind_nullable_version")
    implementation("jakarta.annotation:jakarta.annotation-api:$jakarta_annotation_version")

    implementation("org.springframework:spring-web:$spring_web_version")
    implementation("org.springframework:spring-context:$spring_web_version")

    implementation("org.openapi4j:openapi-schema-validator:1.0.7")
    implementation("org.openapi4j:openapi-parser:1.0.7")


    testImplementation("net.jqwik:jqwik:1.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")

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
