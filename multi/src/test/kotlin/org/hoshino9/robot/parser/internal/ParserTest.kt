package org.hoshino9.robot.parser.internal

import org.hoshino9.handle.Main
import org.hoshino9.propertyInit
import org.hoshino9.robot.dialog.Group
import org.hoshino9.robot.dialog.Member
import org.hoshino9.robot.handle.HandlerContainer
import org.hoshino9.robot.handle.MessageHandler
import org.hoshino9.robot.handle.MessageReceiveHandler
import org.hoshino9.robot.message.RawStringMessage
import org.junit.Test
import kotlin.reflect.full.primaryConstructor

class ParserTest {
    init {
        propertyInit()
    }

    @Test
    fun parse() {
        val dialog = Group(111)
        val sender = Member(123)
        val msg = RawStringMessage("""搜索("搜索")""".trimMargin())
        val handlers = Main.containers.mapNotNull { it.nestedClasses.first { it.simpleName == "Companion" }.objectInstance as? HandlerContainer.Factory }

        val context = MessageReceiveHandler.Context(InternalMessageParser(), handlers, dialog, sender, msg)

        MessageHandler.handle(context)
    }
}