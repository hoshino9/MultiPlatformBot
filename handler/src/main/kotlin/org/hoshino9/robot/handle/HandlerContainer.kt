package org.hoshino9.robot.handle

import org.hoshino9.robot.dialog.Dialog
import org.hoshino9.robot.dialog.Member

@Suppress("MemberVisibilityCanBePrivate", "unused", "CanBeParameter")
abstract class HandlerContainer(val context: MessageReceiveHandler.Context) {
    protected open val dialog: Dialog = context.dialog
    protected open val sender: Member = context.sender!!

    @Retention
    @Target(AnnotationTarget.FUNCTION)
    annotation class Name(val name: String)

    interface Factory {
        fun newInstance(ctx: MessageReceiveHandler.Context): HandlerContainer?
    }
}