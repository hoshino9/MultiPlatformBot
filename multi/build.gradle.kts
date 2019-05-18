val antlr = "antlr-4.7.1-complete"
val antlrSource = "./src/test/resources/Message.g4"
val generateDir = "./src/test/gen/org/hoshino9/robot/parser/internal"
val packageName = "org.hoshino9.robot.parser.internal"

sourceSets {
    test.configure {
        java.srcDirs("src/test/gen")
    }
}

val genAntlr = task<JavaExec>("genAntlr") {
    classpath = sourceSets.test.get().runtimeClasspath
    main = "org.hoshino9.generate.Generate"
}

dependencies {
    compile(group = "org.antlr", name = "antlr4", version = "4.7.2")
    compile(project(":handler"))
    compileOnly(project(":base"))
    testCompile(kotlin("test-junit"))
    testCompile(project(":base"))
}