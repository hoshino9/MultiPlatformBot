plugins {
    kotlin("jvm") version "1.3.31"
}

repositories {
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-jdk7"))
    compile(kotlin("reflect"))
    compileOnly(project(":common:base"))
}