package org.hoshino9.robot.dialog

import org.hoshino9.robot.message.Message

open class Group(override val id: Long) : Dialog {
    override fun send(message: Message) {
        message.sendGroup(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Group) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}