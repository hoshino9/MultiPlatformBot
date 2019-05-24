package org.hoshino9.robot.message

import org.hoshino9.robot.dialog.Group
import org.hoshino9.robot.dialog.Member

interface Message {
    fun sendGroup(group: Group)
    fun sendMember(member: Member)
}