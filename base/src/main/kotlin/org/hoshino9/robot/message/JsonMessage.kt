package org.hoshino9.robot.message

open class JsonMessage(val json: String) : ConsoleMessage() {
    override fun toString(): String {
        return json
    }
}