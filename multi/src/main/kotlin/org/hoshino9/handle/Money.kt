package org.hoshino9.handle

import org.hoshino9.api.asApiMember
import org.hoshino9.robot.handle.GroupHandler
import org.hoshino9.robot.handle.HandlerContainer
import org.hoshino9.robot.handle.MessageReceiveHandler
import org.hoshino9.robot.message.RawStringMessage
import org.hoshino9.robot.message.component.AtMessage

@Suppress("unused")
class Money(context: MessageReceiveHandler.Context) : GroupHandler(context) {
    @Name("钱包")
    fun wallet() {
        buildString {
            append(AtMessage(sender)).appendln()
            append("金币: ${sender.asApiMember(group).money}")
        }.run(::RawStringMessage).run(dialog::send)

    }

    companion object : Factory {
        override fun newInstance(ctx: MessageReceiveHandler.Context): HandlerContainer? {
            return Money(ctx)
        }
    }
}