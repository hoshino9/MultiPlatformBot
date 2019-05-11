package org.hoshino9.robot.handle

import org.hoshino9.robot.dialog.Dialog
import org.hoshino9.robot.message.Message
import org.hoshino9.robot.parser.MessageParser
import java.lang.reflect.Method
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions

@Suppress("MemberVisibilityCanBePrivate", "unused")
class HandlerCenter(val parser: MessageParser) : MessageReceiveHandler {
    override fun handle(handlers: List<HandlerContainer>, message: Message) {
//        fun handleMessage(handlers: List<MessageHandler>) {
//            handlers.forWhen { handler ->
//                handler.regex.matchEntire(message.toString())?.let { result ->
//                    handler.handler.handle(dialog, message, result)
//                } == null || handler.isContinue
//            }
//        }

        val call = parser.parse(message) ?: return

        handlers.forEach { container ->
            val method = container::class.functions.firstOrNull { method ->
                call.functionName == method.findAnnotation<HandlerContainer.Name>()?.name
            } ?: return@forEach

            try {
                method.call(container, *call.arguments)
            } catch (e: Throwable) {
                e.printStackTrace()
            } finally {
                return
            }
        }
    }
}