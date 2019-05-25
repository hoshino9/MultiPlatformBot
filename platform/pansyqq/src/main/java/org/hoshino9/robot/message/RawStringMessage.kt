package org.hoshino9.robot.message

import com.example.plugin.APP
import org.hoshino9.robot.dialog.Group
import org.hoshino9.robot.dialog.Member

open class RawStringMessage(val raw: String) : Message {
    override fun sendGroup(group: Group) {
        APP.pansy.sendGroupMessage(group.id, raw)
    }

    override fun sendMember(member: Member) {
        APP.pansy.sendFriendMessage(member.id, raw)
    }

    override fun toString(): String {
        return raw
    }
}