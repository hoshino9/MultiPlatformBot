package org.hoshino9.handle

import org.hoshino9.game.sharpchess.Pos
import org.hoshino9.game.sharpchess.SharpChessException
import org.hoshino9.game.sharpchess.SharpChessGame
import org.hoshino9.game.sharpchess.SharpChessPlayer
import org.hoshino9.message.ErrorMessage
import org.hoshino9.message.errorMessage
import org.hoshino9.robot.dialog.Group
import org.hoshino9.robot.dialog.Member
import org.hoshino9.robot.handle.GroupHandler
import org.hoshino9.robot.handle.HandlerContainer
import org.hoshino9.robot.handle.MessageReceiveHandler
import org.hoshino9.robot.message.RawStringMessage
import kotlin.reflect.KClass

@Suppress("unused")
class SharpChess(context: MessageReceiveHandler.Context) : GroupHandler(context) {
    private val noSuchMatch = ErrorMessage("该群不存在对局")
    private val noStart = ErrorMessage("对局尚未开始")
    private val noGamingPlayer = ErrorMessage("您并非游戏中玩家")

    private val menu: List<String> = listOf(
        "创建井字棋对局()",
        "加入井字棋对局()",
        "开始井字棋对局()",
        "井字棋盘()",
        "井字棋下棋(行: 数字(0-2), 列: 数字(0-2))"
    )

    @Name("井字棋局")
    fun menu() {
        buildString {
            appendln("井字棋局")
            menu.forEach {
                appendln(it)
            }

            deleteCharAt(lastIndex)
        }.run(::RawStringMessage).run(group::send)
    }

    @Name("创建井字棋对局")
    fun createGame() {
        try {
            newMatch(group)
            RawStringMessage("创建成功")
        } catch (e: SharpChessException) {
            e.errorMessage
        }.run(group::send)
    }

    @Name("加入井字棋对局")
    fun joinGame() {
        val match = findMatch(group)
        if (match == null) noSuchMatch else {
            if (match.has(sender.asPlayer(group, match))) ErrorMessage("您已是游戏中玩家") else {
                try {
                    sender.asPlayer(group, match).run {
                        join()
                    }

                    RawStringMessage("加入成功, 您是第 ${match.size} 位玩家")
                } catch (e: SharpChessException) {
                    e.errorMessage
                }
            }
        }.run(group::send)
    }

    @Name("开始井字棋对局")
    fun startGame() {
            val match = findMatch(group)
            if (match == null) noSuchMatch else {
                if (match.has(sender.asPlayer(group, match))) {
                    try {
                        match.start()

                        RawStringMessage("开始成功")
                    } catch (e: SharpChessException) {
                        e.errorMessage
                    }
                } else noGamingPlayer
            }.run(group::send)
    }

    @Name("井字棋棋盘")
    fun showMap() {
            val match = findMatch(group)

            if (match == null) noSuchMatch else {
                if (!match.isStart) noStart else {
                    match.map.joinToString(separator = "\n") {
                        it.joinToString(separator = " ") { chess ->
                            when (chess) {
                                SharpChessGame.Chess.BLACK -> "○"
                                SharpChessGame.Chess.WHITE -> "×"
                                SharpChessGame.Chess.EMPTY -> "□"
                            }
                        }
                    }.run(::RawStringMessage)
                }
            }.run(group::send)
    }

    @Name("井字棋下棋")
    fun chess(x: Int, y: Int) {
            val match = findMatch(group)

            if (match == null) noSuchMatch else {
                if (!match.isStart) noStart else {
                    if (match.has(sender.asPlayer(group, match))) {
                        try {
                            val winner = sender.asPlayer(group, match).chess(Pos(x, y))

                            showMap()

                            when {
                                match.remain == 0 -> {
                                    finishMatch(group)

                                    RawStringMessage("平局")
                                }

                                winner != SharpChessGame.Chess.EMPTY -> {
                                    finishMatch(group)

                                    RawStringMessage("${match.current.id} 获得了胜利")
                                }

                                else -> RawStringMessage("下棋成功")
                            }
                        } catch (e: SharpChessException) {
                            e.errorMessage
                        }
                    } else noGamingPlayer
                }
            }.run(group::send)
    }

    companion object : Factory {
        override val instanceClass: KClass<*>
            get() = SharpChess::class

        override fun newInstance(ctx: MessageReceiveHandler.Context): HandlerContainer? {
            return SharpChess(ctx)
        }

        private val matches: MutableMap<Group, SharpChessGame> = HashMap()

        private fun Member.asPlayer(group: Group, game: SharpChessGame): SharpChessPlayer =
            SharpChessPlayer(group, id, game)

        private fun findMatch(group: Group): SharpChessGame? {
            return matches[group]
        }

        private fun newMatch(group: Group): SharpChessGame {
            if (group in matches) throw SharpChessException("该群已有对局")

            return SharpChessGame().also { matches[group] = it }
        }

        private fun finishMatch(group: Group) {
            matches.remove(group)
        }
    }
}