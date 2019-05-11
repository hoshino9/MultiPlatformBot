package org.hoshino9.handle

import org.hoshino9.robot.dialog.Dialog
import org.hoshino9.robot.dialog.Group
import org.hoshino9.robot.message.RawStringMessage
import org.hoshino9.robot.message.component.ComponentsMessage

fun Dialog.send(rawString: String) {
    send(RawStringMessage(rawString))
}

fun buildMessage(apply: ComponentsMessage.() -> Unit): ComponentsMessage {
    return ComponentsMessage().apply(apply)
}

fun ComponentsMessage.append(str: String): ComponentsMessage {
    return append(RawStringMessage(str))
}

@Suppress("SpellCheckingInspection")
fun ComponentsMessage.appendln(): ComponentsMessage {
    return append("\n")
}

inline fun Dialog.isGroup(block: (Group) -> Unit) {
    if (this is Group) block(this)
}