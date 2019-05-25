package org.hoshino9.robot.message.component

import java.net.URI

@Suppress("MemberVisibilityCanBePrivate")
class ImageMessage(val type: String, val uri: URI) : ComponentMessage() {
    override fun toString(): String {
        return "[img:$type=\"${uri.path}\"]"
    }
}