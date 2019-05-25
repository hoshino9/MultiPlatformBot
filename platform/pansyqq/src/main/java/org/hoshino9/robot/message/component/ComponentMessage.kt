package org.hoshino9.robot.message.component

import org.hoshino9.robot.dialog.Group
import org.hoshino9.robot.dialog.Member
import org.hoshino9.robot.message.Message
import org.hoshino9.robot.message.RawStringMessage

abstract class ComponentMessage : Message {
    override fun sendGroup(group: Group) {
        RawStringMessage(this.toString()).sendGroup(group)
    }

    override fun sendMember(member: Member) {
        RawStringMessage(this.toString()).sendMember(member)
    }
}