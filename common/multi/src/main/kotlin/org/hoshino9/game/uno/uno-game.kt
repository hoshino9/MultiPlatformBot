package org.hoshino9.game.uno

import org.hoshino9.api.Member
import org.hoshino9.robot.dialog.Group
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

// TODO

class UNOException(message: String) : Exception(message)

data class UNORule @JvmOverloads constructor(
    val maxPlayer: Int = 4,                     // 最大游戏人数
    val allowGamingJoin: Boolean = false,       // 是否允许游戏中加入玩家
    val allowSkip: Boolean = true               // 是否允许跳过回合 (无牌可出时，仅摸一张)
)

class UNOPlayer(group: Group, id: Long, val game: UNOGame) : Member(group, id) {
    var isStopping: Boolean = false

    val card: MutableList<UNOGame.Card> = ArrayList()

    fun draw() {
        game.draw(this)
    }

    fun use(index: Int) {
        game.use(this, index)
    }

    fun join() {
        game.join(this)
    }
}

@Suppress("MemberVisibilityCanBePrivate")
class UNOGame(val rule: UNORule = UNORule()) {
    /**
     * number 0 ~ 9 are number card
     *
     * 10 is STOP
     *
     * 11 is draw two
     *
     * 12 is reverse
     *
     * 13 is wild
     *
     * 14 is wild draw four
     */
    sealed class Card {
        enum class Color {
            RED,
            GREEN,
            BLUE,
            YELLOW,
            BLACK,
        }

        var isResolved: Boolean = false
        abstract val color: Color

        /**
         * 玩家使用该牌时调用
         */
        open fun use(game: UNOGame, target: UNOPlayer) {

        }

        /**
         * 该牌结算时调用
         */
        open fun resolve(game: UNOGame, target: UNOPlayer) {
            isResolved = true
        }

        open fun match(other: Card): Boolean {
            return other.color == color
        }

        data class Number(override val color: Color, val number: Int) : Card() {
            override fun match(other: Card): Boolean {
                return super.match(other) || if (other is Number) {
                    other.number == number
                } else false
            }
        }

        data class Stop(override val color: Color) : Card() {
            override fun use(game: UNOGame, target: UNOPlayer) {
                target.isStopping = true
            }

            override fun match(other: Card): Boolean {
                return super.match(other) || other is Stop
            }
        }

        data class Reverse(override val color: Color) : Card() {
            override fun use(game: UNOGame, target: UNOPlayer) {
                game.direct = -1
            }

            override fun match(other: Card): Boolean {
                return super.match(other) || other is Reverse
            }
        }

        data class DrawTwo(override val color: Color, val times: Int = 1) : Card() {
            override fun resolve(game: UNOGame, target: UNOPlayer) {
                super.resolve(game, target)

                repeat(2 * times) {
                    target.draw()
                }
            }

            override fun match(other: Card): Boolean {
                return super.match(other) || other is DrawTwo
            }
        }

        data class Wild(override val color: Color) : Card() {
            override fun match(other: Card): Boolean {
                return true
            }
        }

        data class WildDrawFour(override val color: Color, val times: Int = 1) : Card() {
            override fun resolve(game: UNOGame, target: UNOPlayer) {
                super.resolve(game, target)

                repeat(4 * times) {
                    target.draw()
                }

                target.isStopping = true
            }

            override fun match(other: Card): Boolean {
                return true
            }
        }
    }

    // 加入游戏的玩家
    private val players: MutableList<UNOPlayer> = LinkedList()

    // 牌堆
    private val cardStack: MutableList<Card> = ArrayList(108)

    // 栈顶下标
    private var stackTop: Int = 0

    private var who: Int = 0

    var direct: Int = 1
        private set(value) {
            field = value
        }

    lateinit var topCard: Card

    val current: UNOPlayer get() = players[who]

    val size: Int get() = players.size
    var isStarted: Boolean = false
        private set(value) {
            field = value
        }

    private fun assertPlayer(player: UNOPlayer) {
        if (current != player) throw UNOException("还未轮到 ${player.id}, 当前玩家: ${current.id}")
    }

    private fun nextPlayer() {
        who += direct

        when {
            direct == 1 && who == players.size -> who = 0
            direct == -1 && who == -1 -> who = players.lastIndex
        }
    }

    private fun newCardStack() {
        cardStack.clear()
        Card.Color.values().forEach { color ->
            if (color != Card.Color.BLACK) {
                cardStack += Card.Number(color, 0)
                (1..9).forEach { number ->
                    repeat(2) {
                        cardStack += Card.Number(color, number)
                    }
                }

                repeat(2) {
                    cardStack += Card.Stop(color)
                }

                repeat(2) {
                    cardStack += Card.Reverse(color)
                }

                repeat(2) {
                    cardStack += Card.DrawTwo(color, 1)
                }
            } else {
                repeat(4) {
                    cardStack += Card.Wild(Card.Color.BLACK)
                }

                repeat(4) {
                    cardStack += Card.WildDrawFour(Card.Color.BLACK)
                }
            }
        }
    }

    private fun random() {
        cardStack.forEachIndexed { index, _ ->
            Collections.swap(cardStack, index, Random.Default.nextInt(0, cardStack.size))
        }
    }

    private fun divide() {
        players.forEach { player ->
            repeat(7) {
                player.draw()
            }
        }
    }

    internal fun draw(player: UNOPlayer) {
        player.card.add(cardStack[stackTop++])
    }

    /**
     * 指定颜色
     *
     * 用于王牌和转色牌的指定颜色
     */
    fun specify(player: UNOPlayer, color: Card.Color) {
        assertPlayer(player)
        if (topCard.color != Card.Color.BLACK) throw UNOException("堆顶卡牌非黑色牌，无需指定颜色")
        if (color == Card.Color.BLACK) throw UNOException("不能指定卡牌颜色为 $color")

        when (topCard) {
            is Card.Wild -> topCard = Card.Wild(color)
            is Card.WildDrawFour -> topCard = Card.WildDrawFour(color)
        }

        nextPlayer()
    }

    fun use(player: UNOPlayer, index: Int) {
        assertPlayer(player)
        if (index < 0 || index > player.card.size) throw UNOException("下标超出边界")
        if (topCard.color == Card.Color.BLACK) throw UNOException("牌堆顶还有未处理完毕的黑色卡牌")

        val topCard = topCard
        val card = player.card[index]

        if (card.match(topCard)) throw UNOException("卡牌 $card 与 $topCard 不匹配")

        val newCard = when (card) {
            is Card.DrawTwo -> {
                if (topCard is Card.DrawTwo && topCard.isResolved.not()) {
                    card.copy(times = topCard.times + 1)
                } else card
            }

            is Card.WildDrawFour -> {
                if (topCard is Card.DrawTwo && topCard.isResolved.not()) {
                    card.copy(times = topCard.times + 1)
                } else card
            }

            else -> card
        }

        this.topCard = newCard

        newCard.use(this, current)

        if (card.color != Card.Color.BLACK) nextPlayer()
    }

    fun join(player: UNOPlayer) {
        if (find(player.id) != null) throw UNOException("${player.id} 已在游戏中")
        if (players.size == rule.maxPlayer) throw UNOException("游戏已达最大人数: ${rule.maxPlayer}")

        if (isStarted.not() || rule.allowGamingJoin) {
            players.add(player)
        } else throw UNOException("游戏已经开始")
    }

    fun start() {
        if (isStarted) throw UNOException("游戏已经开始")
        if (players.size < 2) throw UNOException("游戏人数不足: 2")

        isStarted = true

        topCard = cardStack[stackTop++]
        newCardStack()
        random()
        divide()
    }

    fun find(id: Long): UNOPlayer? {
        return players.firstOrNull { it.id == id }
    }
}