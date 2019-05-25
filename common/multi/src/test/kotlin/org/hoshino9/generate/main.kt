@file:JvmName("Generate")

package org.hoshino9.generate

import org.antlr.v4.Tool

fun main() {
    val packageName = "org.hoshino9.robot.parser.internal"

    Tool.main(
        arrayOf(
            "./src/test/resources/Message.g4",
            "-package", packageName,
            "-o", "./src/test/gen/${packageName.replace('.', '/')}"
        )
    )
}