plugins {
    maven
    kotlin("jvm") version "1.3.31"
}

repositories {
    jcenter()
}

allprojects {
    apply {
        plugin("maven")
        plugin("kotlin")
    }

    group = "org.hoshino9"
    version = "1.0-SNAPSHOT"

    repositories {
        jcenter()
    }

    dependencies {
        compile(kotlin("stdlib-jdk7"))
        compile(kotlin("reflect"))
    }
}