package org.hoshino9.robot.message.component

import org.hoshino9.robot.message.ConsoleMessage
import java.net.URI

@Suppress("MemberVisibilityCanBePrivate")
class ImageMessage(val type: String, val uri: URI) : ConsoleMessage() {
    override fun toString(): String {
        return "[img:$type=\"${uri.path}\"]"
    }
}