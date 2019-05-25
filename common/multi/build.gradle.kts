plugins {
    kotlin("jvm") version "1.3.31"
}

val antlrSource = "./src/test/resources/Message.g4"
val packageName = "org.hoshino9.robot.parser.internal"

sourceSets {
    test.configure {
        java.srcDirs("src/test/gen")
    }
}

val genAntlr = task<JavaExec>("genAntlr") {
    classpath = files("libs/antlr-4.7.2-complete.jar")
    main = "org.antlr.v4.Tool"
    args("./src/test/resources/Message.g4",
        "-package", packageName,
        "-o", "./src/test/gen/${packageName.replace('.', '/')}")
}

repositories {
    jcenter()
}

dependencies {
    compile(fileTree(mapOf("dir" to "libs", "include" to "*.jar")))
    compile(project(":common:handler"))
    compile(group = "com.google.code.gson", name = "gson", version = "2.8.5")
    compileOnly(project(":common:base"))

    testCompile(kotlin("test-junit"))
    testCompile(project(":common:base"))
}

tasks["compileKotlin"].dependsOn(genAntlr)