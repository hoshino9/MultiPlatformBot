package org.hoshino9.robot.parser

import org.hoshino9.robot.message.Message

data class FunctionCall(val functionName: String, val arguments: Array<Any?>)

interface MessageParser {
    fun parse(msg: Message) : FunctionCall?
}