package org.hoshino9.game.sharpchess

import org.hoshino9.handle.SharpChess
import org.hoshino9.propertyInit
import org.hoshino9.robot.dialog.Group
import org.hoshino9.robot.dialog.Member
import org.hoshino9.robot.handle.MessageHandler
import org.hoshino9.robot.handle.MessageReceiveHandler
import org.hoshino9.robot.message.Message
import org.hoshino9.robot.message.RawStringMessage
import org.hoshino9.robot.parser.internal.InternalMessageParser

class SharpChessTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            propertyInit()

            val group = Group(999)
            val player0 = Member(123)
            val player1 = Member(456)

            val msg = listOf(
                player0 to "菜单()",
                player0 to "井字棋局()",
                player0 to "创建井字棋对局()",
                player0 to "加入井字棋对局()",
                player0 to "加入井字棋对局()",
                player1 to "加入井字棋对局()",
                player1 to "加入井字棋对局()",
                player0 to "开始井字棋对局()",
                player1 to "井字棋棋盘()",
                player1 to "井字棋下棋(1, 1)",
                player0 to "井字棋下棋(1, 1)",
                player1 to "井字棋下棋(0, 0)",
                player0 to "井字棋下棋(0, 1)",
                player1 to "井字棋下棋(2, 1)",
                player0 to "井字棋下棋(0, 2)",
                player1 to "井字棋下棋(2, 0)",
                player0 to "井字棋下棋(1, 0)",
                player1 to "井字棋下棋(1, 2)",
                player0 to "井字棋下棋(2, 2)",
                player0 to "井字棋下棋(2, 2)"
            )

            val newCtx = fun(member: Member, message: Message): MessageReceiveHandler.Context {
                return MessageReceiveHandler.Context(InternalMessageParser, listOf(SharpChess), group, member, message)
            }

            msg.forEach { (sender, msg) ->
                MessageHandler.handle(newCtx(sender, RawStringMessage(msg)))
            }
        }
    }
}
