package org.hoshino9.robot.parser.internal

import org.antlr.v4.runtime.BailErrorStrategy
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.hoshino9.robot.dialog.Member
import org.hoshino9.robot.message.Message
import org.hoshino9.robot.parser.FunctionCall
import org.hoshino9.robot.parser.MessageParser
import org.hoshino9.robot.parser.internal.MessageParser.*

class InternalMessageParser : MessageParser {
    private class StringParser(val str: String) {
        private var index = 0
        private val current get() = str[index]
        private fun next(): Char {
            ++ index

            return current
        }

        fun escape(): Char {
            return if (current == '\\') {
                when (next()) {
                    'n' -> '\n'
                    'r' -> '\n'
                    '\\' -> '\\'
                    '"' -> '"'

                    else -> throw IllegalStateException("\\$current")
                }
            } else throw IllegalStateException(current.toString())
        }

        fun parse(): String {
            if (current != '"') throw IllegalStateException(current.toString())

            return buildString {
                while (next() != '"') {
                    (if (current == '\\') escape() else current).run(::append)
                }
            }
        }
    }

    private fun convertValue(ctx: ValueContext): Any? {
        return when {
            ctx.array() != null -> {
                ctx.array().value().map {
                    convertValue(it)
                }
            }

            ctx.at() != null -> Member(ctx.at().Integer().symbol.text.toLong())
            ctx.img() != null -> throw IllegalArgumentException("Image argument was not supported")
            ctx.Integer() != null -> ctx.Integer().symbol.text.toInt()
            ctx.String() != null -> StringParser(ctx.String().text).parse()

            else -> throw IllegalArgumentException(ctx.text)
        }
    }

    override fun parse(msg: Message): FunctionCall? {
        val lexer = MessageLexer(CharStreams.fromString(msg.toString()))
        val tokens = CommonTokenStream(lexer)
        val parser = MessageParser(tokens)

        parser.errorHandler = BailErrorStrategy()

        return try {
            val command = parser.call()
            val name = command.Identity().text
            val args = command.value().map {
                convertValue(it)
            }

            FunctionCall(name, args.toTypedArray())
        } catch (e: Throwable) {
            e.printStackTrace()
            return null
        }
    }
}