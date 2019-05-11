package org.hoshino9.robot.handle

import org.hoshino9.robot.dialog.Dialog
import org.hoshino9.robot.message.Message
import org.hoshino9.robot.parser.MessageParser
import java.lang.reflect.Method
import kotlin.reflect.KFunction

interface MessageReceiveHandler {
    fun handle(handlers: List<HandlerContainer>, message: Message)
}