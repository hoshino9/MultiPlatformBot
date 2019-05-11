package org.hoshino9.robot.handle

import org.hoshino9.robot.dialog.Dialog
import org.hoshino9.robot.dialog.Member

@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class HandlerContainer(val dialog: Dialog, val sender: Member, val center: HandlerCenter) {
    @Retention
    @Target(AnnotationTarget.FUNCTION)
    annotation class Name(val name: String)
}