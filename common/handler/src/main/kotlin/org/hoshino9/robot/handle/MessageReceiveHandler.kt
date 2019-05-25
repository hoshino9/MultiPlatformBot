package org.hoshino9.robot.handle

import org.hoshino9.robot.dialog.Dialog
import org.hoshino9.robot.dialog.Member
import org.hoshino9.robot.message.Message
import org.hoshino9.robot.parser.MessageParser

interface MessageReceiveHandler {
    data class Context(val parser: MessageParser, val handlers: List<HandlerContainer.Factory>, val dialog: Dialog, val sender: Member?, val message: Message)

    fun handle(context: Context)
}