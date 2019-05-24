package org.hoshino9.robot.message

import com.example.plugin.APP
import org.hoshino9.robot.dialog.Group
import org.hoshino9.robot.dialog.Member

open class JsonMessage(val json: String) : Message {
    override fun sendGroup(group: Group) {
        APP.pansy.sendGroupJson(group.id, json)
    }

    override fun sendMember(member: Member) {
        APP.pansy.sendFriendJson(member.id, json)
    }

    override fun toString(): String {
        return json
    }
}