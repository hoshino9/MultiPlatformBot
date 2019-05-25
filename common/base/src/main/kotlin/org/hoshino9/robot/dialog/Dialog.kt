package org.hoshino9.robot.dialog

import org.hoshino9.robot.message.Message

interface Dialog {
    val id: Long

    fun send(message: Message)
}