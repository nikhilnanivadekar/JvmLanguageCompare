package jvm.language.compare.kotlin.cards

import jvm.language.compare.java.cards.JavaDeckOfCards
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.impl.set.mutable.SetAdapter
import org.eclipse.collections.impl.test.Verify
import org.junit.Assert
import org.junit.Test

class DeckOfCardsTest {
    private val kotlinDeck = KotlinDeckOfCards()
    private val jdkDeck = JavaDeckOfCards()

    @Test
    fun allCards() {
        Verify.assertSize(52, this.kotlinDeck.cards)
        Verify.assertSize(52, HashSet(this.kotlinDeck.cards))
        javaKotlinCardEquals(this.jdkDeck.cards, this.kotlinDeck.cards)
    }

    @Test
    fun diamonds() {
        Verify.assertSize(13, this.kotlinDeck.diamonds())
        Verify.assertAllSatisfy(this.kotlinDeck.diamonds(), Card::isDiamonds)
        javaKotlinCardEquals(this.jdkDeck.diamonds(), this.kotlinDeck.diamonds())
    }

    @Test
    fun hearts() {
        Verify.assertSize(13, this.kotlinDeck.hearts())
        Verify.assertAllSatisfy(this.kotlinDeck.hearts(), Card::isHearts)
        javaKotlinCardEquals(this.jdkDeck.hearts(), this.kotlinDeck.hearts())
    }

    @Test
    fun spades() {
        Verify.assertSize(13, this.kotlinDeck.spades())
        Verify.assertAllSatisfy(this.kotlinDeck.spades(), Card::isSpades)
        javaKotlinCardEquals(this.jdkDeck.spades(), this.kotlinDeck.spades())
    }

    @Test
    fun clubs() {
        Verify.assertSize(13, this.kotlinDeck.clubs())
        Verify.assertAllSatisfy(this.kotlinDeck.clubs(), Card::isClubs)
        javaKotlinCardEquals(this.jdkDeck.clubs(), this.kotlinDeck.clubs())
    }

    @Test
    fun deal() {
        this.kotlinDeck.shuffle(java.util.Random(1))
        this.jdkDeck.shuffle(java.util.Random(1))

        val kotlinHand = this.kotlinDeck.deal(5)
        val jdkHand = this.jdkDeck.deal(5)

        Verify.assertSize(5, kotlinHand)
        javaKotlinCardEquals(jdkHand, kotlinHand)
    }

    @Test
    fun shuffleAndDealHands() {
        val kotlinHands = this.kotlinDeck.shuffleAndDeal(java.util.Random(1), 5, 5)
        val jdkHands = this.jdkDeck.shuffleAndDeal(java.util.Random(1), 5, 5)
        Verify.assertSize(5, kotlinHands)
        Assert.assertEquals(kotlinHands.size, jdkHands.size)
        kotlinHands.forEach { kotlinHand -> Verify.assertSize(5, kotlinHand) }


        kotlinHands.forEachIndexed { index, kotlinHand ->
            var jdkHand = SetAdapter.adapt(jdkHands.get(index))
            javaKotlinCardEquals(jdkHand, kotlinHand)
        }
    }

    @Test
    fun dealHands() {
        this.kotlinDeck.shuffle(java.util.Random(1))
        this.jdkDeck.shuffle(java.util.Random(1))

        val kotlinHands = this.kotlinDeck.dealHands(5, 5)
        val jdkHands = this.jdkDeck.dealHands(5, 5)

        Verify.assertSize(5, kotlinHands)
        Assert.assertEquals(kotlinHands.size, jdkHands.size)
        kotlinHands.forEach { kotlinHand -> Verify.assertSize(5, kotlinHand) }

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
        javaKotlinCardEquals(
                jdkCardsBySuit.get(jvm.language.compare.java.cards.Suit.DIAMONDS),
                kotlinCardsBySuit.get(Suit.DIAMONDS))
        javaKotlinCardEquals(
                jdkCardsBySuit.get(jvm.language.compare.java.cards.Suit.HEARTS),
                kotlinCardsBySuit.get(Suit.HEARTS))
        javaKotlinCardEquals(
                jdkCardsBySuit.get(jvm.language.compare.java.cards.Suit.SPADES),
                kotlinCardsBySuit.get(Suit.SPADES))
    }

    @Test
    fun countsBySuit() {
        Verify.assertAllSatisfy(Suit.values().asIterable()) { suit -> this.kotlinDeck.countsBySuit().get(suit) == 13 }
        Verify.assertAllSatisfy(jvm.language.compare.java.cards.Suit.values().asIterable()) { suit -> this.jdkDeck.countsBySuit().occurrencesOf(suit) == 13 }
    }

    @Test
    fun countsByRank() {
        Verify.assertAllSatisfy(Rank.values().asIterable()) { rank -> this.kotlinDeck.countsByRank().get(rank) == 4 }
        Verify.assertAllSatisfy(jvm.language.compare.java.cards.Rank.values().asIterable()) { rank -> this.jdkDeck.countsByRank().occurrencesOf(rank) == 4 }
    }

    @Test
    fun dealOneCard() {
        this.kotlinDeck.shuffle(java.util.Random(1))
        var card1kotlin = this.kotlinDeck.dealOneCard()
        Assert.assertEquals(51, this.kotlinDeck.cardsLeftInDeck())
        var card2kotlin = this.kotlinDeck.dealOneCard()
        Assert.assertEquals(50, this.kotlinDeck.cardsLeftInDeck())
        Assert.assertNotEquals(card1kotlin, card2kotlin)

        this.jdkDeck.shuffle(java.util.Random(1))
        var card1jdk = this.jdkDeck.dealOneCard()
        Assert.assertEquals(51, this.jdkDeck.cardsLeftInDeck())
        var card2jdk = this.jdkDeck.dealOneCard()
        Assert.assertEquals(50, this.jdkDeck.cardsLeftInDeck())
        Assert.assertNotEquals(card1jdk, card2jdk)

        javaKotlinCardEquals(this.jdkDeck.cards, this.kotlinDeck.cards)
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
