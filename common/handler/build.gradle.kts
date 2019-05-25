plugins {
    kotlin("jvm") version "1.3.31"
}

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-jdk7"))
    compileOnly(project(":common:base"))
}