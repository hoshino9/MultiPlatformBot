package org.hoshino9.robot.dialog

import org.hoshino9.robot.message.Message

open class Event(override val id: Long) : Dialog {
    override fun send(message: Message) {
        throw UnsupportedOperationException("Event dialog was not supported send message")
    }
}