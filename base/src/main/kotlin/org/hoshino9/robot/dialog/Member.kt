package org.hoshino9.robot.dialog

import org.hoshino9.robot.message.Message

open class Member(override val id: Long) : Dialog {
    override fun send(message: Message) {
        message.sendMember(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Member) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}