package org.hoshino9.message

import org.hoshino9.robot.message.RawStringMessage

open class ErrorMessage(val error: String) : RawStringMessage(error)

val Throwable.errorMessage get() = ErrorMessage(message.toString())