package org.hoshino9.robot.parser.internal

import org.antlr.v4.runtime.BailErrorStrategy
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.hoshino9.robot.dialog.Member
import org.hoshino9.robot.message.Message
import org.hoshino9.robot.message.RawStringMessage
import org.hoshino9.robot.message.component.ImageMessage
import org.hoshino9.robot.parser.FunctionCall
import org.hoshino9.robot.parser.MessageParser
import java.net.URI

object InternalMessageParser : MessageParser {
    private class StringParser(val str: String) {
        private var index = 0
        private val current get() = str[index]
        private fun next(): Char {
            ++index

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

    private val atRegex = Regex("""\[At:(\d+)]""")
    private val imgRegex = Regex("""\[img:(\w+)="(.+?)"]""")

    private fun convertValue(ctx: org.hoshino9.robot.parser.internal.MessageParser.ValueContext): Any? {
        return when {
            ctx.array() != null -> {
                ctx.array().value().map {
                    convertValue(it)
                }
            }

            ctx.At() != null -> atRegex.matchEntire(ctx.At().text)!!.groupValues[1].toLong().run(::Member)
            ctx.Img() != null -> ctx.Img().text.run(imgRegex::matchEntire)!!.let {
                ImageMessage(it.groupValues[1], it.groupValues[2].run(::URI))
            }
            ctx.Integer() != null -> ctx.Integer().symbol.text.toInt()
            ctx.String() != null -> StringParser(ctx.String().text).parse()
            ctx.call() != null -> parse(RawStringMessage(ctx.call().text))

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