package org.hoshino9.robot.message.component

import org.hoshino9.robot.message.ConsoleMessage
import org.hoshino9.robot.message.Message
import java.util.*

open class ComponentsMessage : ConsoleMessage() {
    private val messages: MutableList<Message> = LinkedList()

    fun append(msg: Message): ComponentsMessage {
        messages.add(msg)

        return this
    }

    override fun toString(): String {
        return messages.joinToString(separator = "") {
            it.toString()
        }
    }
}