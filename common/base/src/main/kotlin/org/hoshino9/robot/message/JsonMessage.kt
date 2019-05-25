package org.hoshino9.robot.message

import org.hoshino9.robot.dialog.Group
import org.hoshino9.robot.dialog.Member

open class JsonMessage(val json: String) : Message {
    override fun sendGroup(group: Group) {
        println("[JsonMessage][To Group ${group.id}]\n$this")
    }

    override fun sendMember(member: Member) {
        println("[JsonMessage][To Member ${member.id}]\n$this")
    }

    override fun toString(): String {
        return json
    }
}