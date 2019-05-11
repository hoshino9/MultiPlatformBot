package org.hoshino9.robot.message

open class XmlMessage(val xml: String) : ConsoleMessage() {
    override fun toString(): String {
        return xml
    }
}