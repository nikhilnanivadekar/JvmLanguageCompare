package jvm.language.compare.kotlin.cards

import jvm.language.compare.java.cards.JavaDeckOfCards
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.impl.set.mutable.SetAdapter
import org.junit.Assert
import org.junit.Test
import java.util.*

class DeckOfCardsTest {
    private val kotlinDeck = KotlinDeckOfCards()
    private val jdkDeck = JavaDeckOfCards()

    @Test
    fun allCards() {
        javaKotlinCardEquals(this.jdkDeck.cards, this.kotlinDeck.cards)
    }

    @Test
    fun diamonds() {
        javaKotlinCardEquals(this.jdkDeck.diamonds(), this.kotlinDeck.diamonds())
    }

    @Test
    fun hearts() {
        javaKotlinCardEquals(this.jdkDeck.hearts(), this.kotlinDeck.hearts())
    }

    @Test
    fun spades() {
        javaKotlinCardEquals(this.jdkDeck.spades(), this.kotlinDeck.spades())
    }

    @Test
    fun clubs() {
        javaKotlinCardEquals(this.jdkDeck.clubs(), this.kotlinDeck.clubs())
    }

    @Test
    fun deal() {
        this.kotlinDeck.shuffle(Random(1))
        this.jdkDeck.shuffle(Random(1))

        val kotlinHand = this.kotlinDeck.deal(5)
        val jdkHand = this.jdkDeck.deal(5)
        javaKotlinCardEquals(jdkHand, kotlinHand)
    }

    @Test
    fun shuffleAndDealHands() {
        val kotlinHands = this.kotlinDeck.shuffleAndDeal(Random(1), 5, 5)
        val jdkHands = this.jdkDeck.shuffleAndDeal(Random(1), 5, 5)
        Assert.assertEquals(kotlinHands.size, jdkHands.size)

        kotlinHands.forEachIndexed { index, kotlinHand ->
            var jdkHand = SetAdapter.adapt(jdkHands.get(index))
            javaKotlinCardEquals(jdkHand, kotlinHand)
        }
    }

    @Test
    fun dealHands() {
        this.kotlinDeck.shuffle(Random(1))
        this.jdkDeck.shuffle(Random(1))

        val kotlinHands = this.kotlinDeck.dealHands(5, 5)
        val jdkHands = this.jdkDeck.dealHands(5, 5)

        Assert.assertEquals(kotlinHands.size, jdkHands.size)

        kotlinHands.forEachIndexed { index, kotlinHand ->
            var jdkHand = SetAdapter.adapt(jdkHands.get(index))
            javaKotlinCardEquals(jdkHand, kotlinHand)
        }
    }

    @Test
    fun cardsBySuit() {
        val kotlinCardsBySuit = this.kotlinDeck.cardsBySuit
        val jdkCardsBySuit = this.jdkDeck.cardsBySuit

        javaKotlinCardEquals(
                jdkCardsBySuit.get(jvm.language.compare.java.cards.Suit.CLUBS),
                kotlinCardsBySuit.get(Suit.CLUBS))
    }

    @Test
    fun countsBySuit() {
        Assert.assertEquals(13,
                this.kotlinDeck.countsBySuit().get(Suit.CLUBS))
    }

    @Test
    fun countsByRank() {
        Assert.assertEquals(4, this.kotlinDeck.countsByRank().get(Rank.SEVEN))
    }

    companion object {
        fun javaKotlinCardEquals(javaCards: ListIterable<jvm.language.compare.java.cards.Card>,
                                 kotlinCards: Iterable<Card>?) {
            Assert.assertEquals(javaCards.toString(), kotlinCards.toString())
        }

        fun javaKotlinCardEquals(javaCards: org.eclipse.collections.api.set.MutableSet<jvm.language.compare.java.cards.Card>,
                                 kotlinCards: MutableSet<Card>) {
            Assert.assertEquals(javaCards.size, kotlinCards.size)

            // Must collect to Set as order is not guaranteed so, the toString() should be called on each element.
            var javaSet = javaCards.collect { card -> card.toString() }
            var kotlinSet = kotlinCards.map { card -> card.toString() }.toSet()
            Assert.assertEquals(javaSet, kotlinSet)
        }
    }

}
