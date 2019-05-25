package org.hoshino9.handle

import org.hoshino9.message.ErrorMessage
import org.hoshino9.robot.dialog.Member
import org.hoshino9.robot.handle.GroupHandler
import org.hoshino9.robot.handle.HandlerContainer
import org.hoshino9.robot.handle.MessageReceiveHandler
import org.hoshino9.robot.message.RawStringMessage
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions

@Suppress("unused")
class Main(context: MessageReceiveHandler.Context) : GroupHandler(context) {
    private val menu: List<String> = listOf("井字期局")

    @Name("菜单")
    fun menu() {
//        group.send(RawStringMessage("什么都没有"))
        buildMessage {
            append("什么都没有2")
        }.run(dialog::send)
    }

    @Name("搜索")
    fun info(name: String) {
        lateinit var methods: List<KFunction<*>>
        (context.handlers.map { it.instanceClass }.firstOrNull { `class` ->
            //            `class`.functions.firstOrNull { method ->
//                method.findAnnotation<Name>()?.name == name
//            }?.apply { method = this } != null
            `class`.functions.filter { method ->
                method.findAnnotation<Name>()?.name == name
            }.takeIf { it.isNotEmpty() }?.apply { methods = this } != null
        }?.let { _ ->
//            method.parameters.drop(1).joinToString(transform = ::typeTrans).let {
//                RawStringMessage("$name($it)")
//            }
            methods.joinToString(separator = "\n") { method ->
                method.parameters.drop(1).joinToString(transform = ::typeTrans).let {
                    "$name($it)"
                }
            }.run(::RawStringMessage)
        } ?: ErrorMessage("没有找到这个指令: $name")).run(dialog::send)
    }

    companion object : Factory {
        override val instanceClass: KClass<*> get() = Main::class

        override fun newInstance(ctx: MessageReceiveHandler.Context): HandlerContainer? {
            return Main(ctx)
        }

        val containers = listOf(
            Main::class,
            Money::class,
            SharpChess::class
        )

        private fun typeTrans(param: KParameter): String {
            return "${param.name}: " + when (param.type.classifier as? KClass<*>) {
                Int::class -> "数字"
                String::class -> "字符串"
                Member::class -> "艾特"

                else -> "未知"
            }
        }
    }
}