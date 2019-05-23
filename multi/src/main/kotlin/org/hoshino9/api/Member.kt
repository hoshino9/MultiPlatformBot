package org.hoshino9.api

import com.google.gson.JsonObject
import org.hoshino9.api.Api.root
import org.hoshino9.robot.dialog.Group
import org.hoshino9.utils.json
import java.io.File
import java.util.*
import kotlin.random.Random
import org.hoshino9.robot.dialog.Member as DialogMember

open class MemberException(val member: Member, message: String?) : Exception(message)

open class SignInException(member: Member, message: String) : MemberException(member, message)

data class SignInInfo(val money: Long)

open class Member(val group: Group, id: Long) : DialogMember(id) {
    private val infoPath = "$root/$group/$id/config.json"
    private val property = JsonProperty(File(infoPath), defaultProperty)

    private var signInDate: String by property

    var money: Long by property

    /**
     * 签到
     *
     * @throws SignInException
     */
    fun signIn(): SignInInfo {
        val money = Random.Default.nextLong(0, 500)

        return SignInInfo(money).apply(::signIn)
    }

    /**
     * 根据传入的 [info] 进行签到
     *
     * @throws SignInException
     */
    fun signIn(info: SignInInfo) {
        val calendar = Calendar.getInstance()
        val dateString = "${calendar[Calendar.YEAR]}:${calendar[Calendar.MONTH]}:${calendar[Calendar.DAY_OF_MONTH]}"
        val record = signInDate

        if (dateString == record) throw SignInException(this, "您已经签到过了") else {
            this.money += info.money
            signInDate = dateString
        }
    }

    companion object {
        val defaultProperty: JsonObject = json {
            "money" to 0
            "signInDate" to "1970:01:01"
        }
    }
}

fun DialogMember.asApiMember(group: Group): Member {
    return Member(group, id)
}