package org.hoshino9.robot.handle

import org.hoshino9.robot.dialog.Event
import org.hoshino9.robot.dialog.Group
import org.hoshino9.robot.dialog.Member

abstract class GroupHandler(context: MessageReceiveHandler.Context) : HandlerContainer(context) {
    protected open val group: Group = context.dialog as Group
}

abstract class MemberHandler(context: MessageReceiveHandler.Context) : HandlerContainer(context) {
    protected open val member: Member = context.dialog as Member
}

abstract class EventHandler(context: MessageReceiveHandler.Context) : HandlerContainer(context) {
    protected open val event: Event = context.dialog as Event
}