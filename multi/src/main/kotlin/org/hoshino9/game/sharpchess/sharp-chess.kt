@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package org.hoshino9.game.sharpchess

import org.hoshino9.api.Member
import org.hoshino9.robot.dialog.Group
import java.util.*
import kotlin.collections.ArrayList

data class Pos(val x: Int, val y: Int)

class SharpChessException(message: String) : Exception(message)

class SharpChessPlayer(group: Group, id: Long, val game: SharpChessGame) : Member(group, id) {
    fun join() {
        game.joinIn(this)
    }

    fun chess(pos: Pos): SharpChessGame.Chess {
        return game.chess(this, pos)
    }
}

class SharpChessGame {
    enum class Chess {
        BLACK, WHITE, EMPTY
    }

    private val players: MutableList<SharpChessPlayer> = ArrayList(2)
    private var who: Chess = Chess.BLACK

    val history: MutableList<Pos> = LinkedList()

    val map: Array<Array<Chess>> = Array(3) {
        Array(3) { Chess.EMPTY }
    }

    val size: Int get() = players.size

    var remain = 9
        private set(value) {
            field = value
        }

    var isStart: Boolean = false
        private set(value) {
            field = value
        }

    val current: SharpChessPlayer
        get() {
            return players[who.ordinal]
        }

    fun joinIn(player: SharpChessPlayer) {
        if (isStart) throw SharpChessException("游戏已经开始")

        if (players.size < 2) {
            players.add(player)
        } else throw SharpChessException("游戏人数已满")
    }

    fun has(player: SharpChessPlayer): Boolean {
        return players.contains(player)
    }

    fun start() {
        if (players.size == 2) {
            isStart = true
        } else throw SharpChessException("玩家不足: ${players.size}")
    }

    fun chess(player: SharpChessPlayer, pos: Pos): Chess {
        if (pos.x < 0 || pos.y < 0 || pos.x > 2 || pos.y > 2) throw SharpChessException("超出棋盘界限")

        val chess = getPlayerChess(player)

        if (chess != who) throw SharpChessException("还未轮到你下棋")

        val (x, y) = pos

        if (map[x][y] != Chess.EMPTY) throw SharpChessException("目标格子不为空: ($x, $y)")
        map[x][y] = chess
        history.add(pos)

        --remain

        return checkWin(pos).also {
            if (it == Chess.EMPTY) {
                who = if (who == Chess.BLACK) Chess.WHITE else Chess.BLACK
            }
        }
    }

    private fun checkWin(pos: Pos): Chess {
        val (x, y) = pos

        // horizontal
        run {
            var last = map[x][0]

            for (j in 1 until 3) if (last == map[x][j]) last = map[x][j] else return@run

            return last
        }

        // vertical
        run {
            var last = map[0][y]

            for (j in 1 until 3) if (last == map[j][y]) last = map[j][y] else return@run

            return last
        }

        if (x == y) {
            run {
                var last = map[0][0]

                for (i in 1 until 3) if (last == map[i][i]) last = map[i][i] else return@run

                return last
            }
        }

        if (x + y == 2) {
            run {
                var last = map[0][2]

                for (i in 1 until 3) if (last == map[i][2 - i]) last = map[i][2 - i] else return@run

                return last
            }
        }

        return Chess.EMPTY
    }

    private fun getPlayerChess(player: SharpChessPlayer): Chess {
        return players.indexOf(player).takeIf { it != -1 }?.let {
            Chess.values()[it]
        } ?: throw SharpChessException("玩家没有参加该对局")
    }
}