package org.hoshino9.handle

import org.hoshino9.api.asApiMember
import org.hoshino9.robot.dialog.Dialog
import org.hoshino9.robot.dialog.Member
import org.hoshino9.robot.handle.HandlerCenter
import org.hoshino9.robot.handle.HandlerContainer
import org.hoshino9.robot.message.RawStringMessage
import org.hoshino9.robot.message.component.AtMessage

@Suppress("unused")
class Money(dialog: Dialog, sender: Member, center: HandlerCenter) : HandlerContainer(dialog, sender, center) {
    @Name("钱包")
    fun wallet() {
        dialog.isGroup { group ->
            buildString {
                append(AtMessage(sender)).appendln()
                append("金币: ${sender.asApiMember(group).money}")
            }.run(::RawStringMessage).run(dialog::send)
        }
    }
}