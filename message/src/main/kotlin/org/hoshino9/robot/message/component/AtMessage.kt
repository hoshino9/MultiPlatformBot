package org.hoshino9.robot.message.component

import org.hoshino9.robot.dialog.Member
import org.hoshino9.robot.message.ConsoleMessage
import org.hoshino9.robot.message.Message

open class AtMessage(val target: Member) : ConsoleMessage() {
    override fun toString(): String {
        return "[At:${target.id}]"
    }
}