package org.hoshino9.handle

import org.hoshino9.api.SignInException
import org.hoshino9.api.asApiMember
import org.hoshino9.message.errorMessage
import org.hoshino9.robot.dialog.Member
import org.hoshino9.robot.handle.GroupHandler
import org.hoshino9.robot.handle.HandlerContainer
import org.hoshino9.robot.handle.MessageReceiveHandler
import org.hoshino9.robot.message.Message
import org.hoshino9.robot.message.RawStringMessage
import org.hoshino9.robot.message.component.AtMessage
import kotlin.reflect.KClass

@Suppress("unused")
class Money(context: MessageReceiveHandler.Context) : GroupHandler(context) {
    @Name("钱包")
    fun wallet() {
        wallet(sender)
    }

    @Name("钱包")
    fun wallet(target: Member) {
        buildString {
            append(AtMessage(target)).appendln()
            append("金币: ${target.asApiMember(group).money}")
        }.run(::RawStringMessage).run(dialog::send)
    }

    @Name("签到")
    fun signIn() {
        try {
            val info = sender.asApiMember(group).signIn()
            buildMessage {
                append(AtMessage(sender)).appendln()
                append(
                    RawStringMessage(
                        """
                    获得金币: ${info.money}
                """.trimIndent()
                    )
                )
            }
        } catch (e: SignInException) {
            e.errorMessage
        }.run(dialog::send)
    }

    companion object : Factory {
        override val instanceClass: KClass<*>
            get() = Money::class

        override fun newInstance(ctx: MessageReceiveHandler.Context): HandlerContainer? {
            return Money(ctx)
        }
    }
}