package org.hoshino9.robot.message.component

import org.hoshino9.robot.dialog.Member

open class AtMessage(val target: Member) : ComponentMessage() {
    override fun toString(): String {
        return "[At:${target.id}]"
    }
}