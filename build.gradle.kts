plugins {
    java
    `java-library`
    `maven-publish`
    id("com.github.hierynomus.license-base") version "0.16.1"
}

group = "io.github.pulsebeat02"
version = "v1.0.1"

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java {
            srcDir("src/main/java")
        }
        resources {
            srcDir("src/main/resources")
        }
    }
}

tasks {
    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    publish {
        dependsOn(clean)
        dependsOn(build)
    }
}


publishing {
    repositories {
        maven {
            setUrl("https://pulsebeat02.jfrog.io/artifactory/pulse-gradle-release-local")
            credentials {
                username = ""
                password = ""
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

subprojects {

    apply(plugin = "com.github.hierynomus.license-base")

    license {
        header = rootProject.file("LICENSE")
        encoding = "UTF-8"
        mapping("java", "SLASHSTAR_STYLE")
        includes(listOf("**/*.java", "**/*.kts"))
    }

    task<Wrapper>("wrapper") {
        gradleVersion = "7.3.3"
    }
}