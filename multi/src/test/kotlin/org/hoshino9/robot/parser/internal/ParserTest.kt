package org.hoshino9.robot.parser.internal

import org.hoshino9.handle.Main
import org.hoshino9.handle.Money
import org.hoshino9.propertyInit
import org.hoshino9.robot.dialog.Group
import org.hoshino9.robot.dialog.Member
import org.hoshino9.robot.handle.HandlerCenter
import org.hoshino9.robot.message.RawStringMessage
import org.junit.Test
import kotlin.reflect.full.primaryConstructor

class ParserTest {
    init {
        propertyInit()
    }

    @Test
    fun parse() {
        val handlerCenter = HandlerCenter(InternalMessageParser())
        val dialog = Group(111)
        val sender = Member(123)
        val msg = RawStringMessage("""搜索("搜索")""".trimMargin())

        val handlers = Main.containers.map { it.primaryConstructor!!.call(dialog, sender, handlerCenter) }
        handlerCenter.handle(handlers, msg)
    }
}