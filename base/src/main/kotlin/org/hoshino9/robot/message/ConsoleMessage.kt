package org.hoshino9.robot.message

import org.hoshino9.robot.dialog.Group
import org.hoshino9.robot.dialog.Member

abstract class ConsoleMessage : Message {
    override fun sendGroup(group: Group) {
        println("[${this::class.simpleName}][To Group ${group.id}]\n$this")
    }

    override fun sendMember(member: Member) {
        println("[${this::class.simpleName}][To Member ${member.id}]\n$this")
    }
}