package org.hoshino9.robot.dialog

import com.example.plugin.APP
import org.hoshino9.robot.message.Message

open class Group(override val id: Long) : Dialog {
    private fun log(msg: String) {
        println("[Group: $id]\n$msg")
    }

    val name: String get() = id.toString()

    fun name(member: Member): String {
        return "($id, $member)"
    }

    fun kick(target: Member) {
        APP.pansy.kick(id, target.id)
    }

    fun shutUp(target: Member, seconds: Long) {
        log("shut up $target ($seconds s)")
    }

    fun shutUp(isShutUp: Boolean) {
        log("${if (isShutUp) "" else "Release"} shut up all members")
    }

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

    override fun toString(): String {
        return id.toString()
    }
}