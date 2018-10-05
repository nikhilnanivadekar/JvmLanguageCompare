package jvm.language.compare.kotlin.cards

import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.api.multimap.list.MutableListMultimap
import org.eclipse.collections.api.stack.MutableStack
import org.eclipse.collections.impl.factory.Stacks
import org.eclipse.collections.impl.list.mutable.ListAdapter
import java.util.*

data class KotlinDeckOfCards(
        val cards: MutableList<Card> = Card.getCards(),
        val cardsBySuit: MutableListMultimap<Suit, Card> =
                ListAdapter.adapt(cards)
                        .groupBy { card -> card.suit }) {

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

    fun diamonds(): ListIterable<Card> {
        return this.cardsBySuit.get(Suit.DIAMONDS)
    }

    fun hearts(): ListIterable<Card> {
        return this.cardsBySuit.get(Suit.HEARTS)
    }

    fun spades(): ListIterable<Card> {
        return this.cardsBySuit.get(Suit.SPADES)
    }

    fun clubs(): ListIterable<Card> {
        return this.cardsBySuit.get(Suit.CLUBS)
    }

    fun countsBySuit(): Map<Suit, Int> {
        return this.cards.groupingBy { card: Card -> card.suit }.eachCount()
    }

    fun countsByRank(): Map<Rank, Int> {
        return this.cards.groupingBy { card: Card -> card.rank }.eachCount()
    }
}
