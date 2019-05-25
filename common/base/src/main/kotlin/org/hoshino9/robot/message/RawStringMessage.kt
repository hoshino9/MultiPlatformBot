package org.hoshino9.robot.message

import org.hoshino9.robot.dialog.Group
import org.hoshino9.robot.dialog.Member

open class RawStringMessage(val raw: String) : Message {
    override fun sendGroup(group: Group) {
        println("[RawStringMessage][To Group ${group.id}]\n$this")
    }

    override fun sendMember(member: Member) {
        println("[RawStringMessage][To Member ${member.id}]\n$this")
    }

    override fun toString(): String {
        return raw
    }
}