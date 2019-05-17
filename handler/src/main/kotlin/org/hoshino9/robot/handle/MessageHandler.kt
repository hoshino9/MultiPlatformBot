package org.hoshino9.robot.handle

import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions

@Suppress("MemberVisibilityCanBePrivate", "unused")
object MessageHandler : MessageReceiveHandler {
    override fun handle(context: MessageReceiveHandler.Context) {
        val call = context.parser.parse(context.message) ?: return

        context.handlers.forEach { factory ->
            val container = try {
                factory.newInstance(context) ?: return@forEach
            } catch (e: Exception) {
                e.printStackTrace()
                return@forEach
            }

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