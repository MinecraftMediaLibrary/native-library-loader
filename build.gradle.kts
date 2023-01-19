plugins {
    java
    `java-library`
    `maven-publish`
    id("com.github.hierynomus.license-base") version "0.16.1"
}

group = "io.github.pulsebeat02"
version = "v1.0.2"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("net.java.dev.jna:jna-platform:5.13.0")
    testImplementation("net.java.dev.jna:jna:5.13.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
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
    test {
        useJUnitPlatform()
    }
}


publishing {
    repositories {
        maven {
            setUrl("https://pulsebeat02.jfrog.io/artifactory/minecraftmedialibrary/")
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
        gradleVersion = "7.6"
    }
}