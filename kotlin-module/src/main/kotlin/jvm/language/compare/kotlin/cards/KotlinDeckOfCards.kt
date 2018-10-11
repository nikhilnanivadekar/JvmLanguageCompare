package jvm.language.compare.kotlin.cards

import org.eclipse.collections.api.stack.MutableStack
import org.eclipse.collections.impl.factory.Stacks
import java.util.*

data class KotlinDeckOfCards(
        val cards: List<Card> = Card.getCards().sorted(),
        val cardsBySuit: Map<Suit, List<Card>> =
                cards.groupBy { card -> card.suit }) {

    // Using MutableStack in Kotlin from EC, because as of 10/10/2018 Kotlin does not have a Stack or a Deque.
    // It uses these data structures from JDK.
    fun shuffle(random: Random): MutableStack<Card> {
        var cardCopy = this.cards.toMutableList()
        cardCopy.shuffle(random)
        cardCopy.shuffle(random)
        cardCopy.shuffle(random)
        return Stacks.mutable.withAll(cardCopy)
    }

    fun deal(stack: MutableStack<Card>, count: Int): MutableSet<Card> {
        var pop = stack.pop(count)

        var outputSet: MutableSet<Card> = mutableSetOf()
        outputSet.addAll(pop)
        return outputSet
    }

    fun shuffleAndDeal(random: Random, hands: Int, cardsPerHand: Int): List<MutableSet<Card>> {
        val shuffled = this.shuffle(random)
        return this.dealHands(shuffled, hands, cardsPerHand)
    }

    fun dealHands(shuffled: MutableStack<Card>, hands: Int, cardsPerHand: Int): List<MutableSet<Card>> {
        return IntRange(1, hands).map { i -> this.deal(shuffled, cardsPerHand) }
    }

    fun diamonds(): List<Card>? {
        return this.cardsBySuit.get(Suit.DIAMONDS)
    }

    fun hearts(): List<Card>? {
        return this.cardsBySuit.get(Suit.HEARTS)
    }

    fun spades(): List<Card>? {
        return this.cardsBySuit.get(Suit.SPADES)
    }

    fun clubs(): List<Card>? {
        return cardsBySuit.get(Suit.CLUBS)
    }

    fun countsBySuit(): Map<Suit, Int> {
        return this.cards.groupingBy { card: Card -> card.suit }.eachCount()
    }

    fun countsByRank(): Map<Rank, Int> {
        return this.cards.groupingBy { card: Card -> card.rank }.eachCount()
    }
}
