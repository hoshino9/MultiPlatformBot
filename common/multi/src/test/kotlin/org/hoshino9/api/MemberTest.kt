package org.hoshino9.api

import org.hoshino9.propertyInit
import org.hoshino9.robot.dialog.Group
import org.junit.Test
import kotlin.test.assertEquals

class MemberTest {
    init {
        propertyInit()
    }

    private val member: Member get() = Member(Group(999), 123)
    private val other: Member get() = Member(Group(999), 321)

    @Test
    fun money() {
        println(member.money)
        member.money = 999
        assertEquals(999, member.money.apply(::println))
    }

    @Test
    fun signIn() {
        val money = member.money
        try {
            other.signIn()
            val info = member.signIn()

            assertEquals(money + info.money, member.money)

            println(money)
            println(info)
            println(member.money)
        } catch (e: SignInException) {
            System.err.println(e.message)
        }
    }
}