package org.hoshino9.robot.message

import org.hoshino9.robot.dialog.Group
import org.hoshino9.robot.dialog.Member

open class XmlMessage(val xml: String) : Message {
    override fun sendGroup(group: Group) {
        println("[XmlMessage][To Group ${group.id}]\n$this")
    }

    override fun sendMember(member: Member) {
        println("[XmlMessage][To Member ${member.id}]\n$this")
    }
    override fun toString(): String {
        return xml
    }
}