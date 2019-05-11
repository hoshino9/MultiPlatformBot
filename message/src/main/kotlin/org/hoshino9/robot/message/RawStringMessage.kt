package org.hoshino9.robot.message

open class RawStringMessage(val raw: String) : ConsoleMessage() {
    override fun toString(): String {
        return raw
    }
}