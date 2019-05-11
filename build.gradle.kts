plugins {
    java
    kotlin("jvm") version "1.3.31"
}

repositories {
    jcenter()
}

allprojects {
    apply {
        plugin("java")
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
        compile(group = "com.google.code.gson", name = "gson", version = "2.8.5")
    }
}