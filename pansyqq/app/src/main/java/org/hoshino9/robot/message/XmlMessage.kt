package org.hoshino9.robot.message

import com.example.plugin.APP
import org.hoshino9.robot.dialog.Group
import org.hoshino9.robot.dialog.Member

open class XmlMessage(val xml: String) : Message {
    override fun sendGroup(group: Group) {
        APP.pansy.sendGroupXml(group.id, xml)
    }

    override fun sendMember(member: Member) {
        APP.pansy.sendFriendXml(member.id, xml)
    }

    override fun toString(): String {
        return xml
    }
}