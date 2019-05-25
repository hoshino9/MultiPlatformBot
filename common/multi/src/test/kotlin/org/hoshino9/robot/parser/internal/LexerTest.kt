package org.hoshino9.robot.parser.internal

import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.misc.ParseCancellationException
import org.junit.Test

class LexerTest {
    @Test
    fun parse() {
        val id = "hello"
        val integer = "999"
        val string = """"999""""
        val array = """[9, "9", [9]]"""
        val at = "[At:999]"
        val img = """[img="999"]"""

        val str = "$id($integer, $string, $array, $at, $img, $id())"
        val lexer = MessageLexer(CharStreams.fromString(str))
        val tokens = CommonTokenStream(lexer)
        val parser = MessageParser(tokens)
        parser.errorHandler = BailErrorStrategy()

        parser.call().text.run(::println)
    }
}