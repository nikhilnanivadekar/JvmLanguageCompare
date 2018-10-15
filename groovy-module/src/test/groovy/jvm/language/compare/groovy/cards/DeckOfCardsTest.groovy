package jvm.language.compare.groovy.cards

import jvm.language.compare.java.cards.JavaDeckOfCards
import org.eclipse.collections.api.list.ListIterable
import org.eclipse.collections.api.set.MutableSet
import org.eclipse.collections.impl.factory.Sets
import org.eclipse.collections.impl.set.mutable.SetAdapter
import org.eclipse.collections.impl.utility.Iterate
import org.junit.Assert
import org.junit.Test

import java.util.stream.Collectors

class DeckOfCardsTest {
    private groovyDeck = new GroovyDeckOfCards()
    private jdkDeck = new JavaDeckOfCards()

    @Test
    void allCards() {
        assert 52 == this.groovyDeck.getCards().size()
        assert 52 == Sets.mutable.withAll(this.groovyDeck.getCards()).size()
        javaGroovyCardEquals(this.jdkDeck.cards, this.groovyDeck.cards)
    }

    @Test
    void diamonds() {
        assert 13 == this.groovyDeck.diamonds().size()
        assert this.groovyDeck.diamonds().every { it.isDiamonds() }
        javaGroovyCardEquals(this.jdkDeck.diamonds(), this.groovyDeck.diamonds())
    }

    @Test
    void hearts() {
        assert 13 == this.groovyDeck.hearts().size()
        assert this.groovyDeck.hearts().every { it.isHearts() }
        javaGroovyCardEquals(this.jdkDeck.hearts(), this.groovyDeck.hearts())
    }

    @Test
    void spades() {
        assert 13 == this.groovyDeck.spades().size()
        assert this.groovyDeck.spades().every { it.isSpades() }
        javaGroovyCardEquals(this.jdkDeck.spades(), this.groovyDeck.spades())
    }

    @Test
    void clubs() {
        assert 13 == this.groovyDeck.clubs().size()
        assert this.groovyDeck.clubs().every { it.isClubs() }
        javaGroovyCardEquals(this.jdkDeck.clubs(), this.groovyDeck.clubs())
    }

    @Test
    void deal() {
        this.groovyDeck.shuffle(new Random(1))
        this.jdkDeck.shuffle(new Random(1))

        def groovyHand = this.groovyDeck.deal(5)
        def jdkHand = this.jdkDeck.deal(5)

        assert 5 == groovyHand.size()
        javaGroovyCardEquals(jdkHand, groovyHand)
    }

    @Test
    void shuffleAndDealHands() {
        def groovyHands = this.groovyDeck.shuffleAndDeal(new Random(1), 5, 5)
        def jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5)
        assert 5 == groovyHands.size()
        assert groovyHands.every({ it.size() == 5 })

        groovyHands.eachWithIndex { Set<Card> groovyHand, int index ->
            def jdkHand = SetAdapter.adapt(jdkHands.get(index))
            javaGroovyCardEquals(jdkHand, groovyHand)
        }
    }

    @Test
    void dealHands() {
        this.groovyDeck.shuffle(new Random(1))
        this.jdkDeck.shuffle(new Random(1))

        def groovyHands = this.groovyDeck.dealHands(5, 5)
        def jdkHands = this.jdkDeck.dealHands(5, 5)

        assert 5 == groovyHands.size()
        assert groovyHands.every({ it.size() == 5 })

        groovyHands.eachWithIndex { Set<Card> groovyHand, int index ->
            def jdkHand = SetAdapter.adapt(jdkHands.get(index))
            javaGroovyCardEquals(jdkHand, groovyHand)
        }
    }

    @Test
    void cardsBySuit() {
        def groovyCardsBySuit = this.groovyDeck.getCardsBySuit()
        def jdkCardsBySuit = this.jdkDeck.cardsBySuit

        javaGroovyCardEquals(
                jdkCardsBySuit.get(jvm.language.compare.java.cards.Suit.CLUBS),
                groovyCardsBySuit.get(Suit.CLUBS))
        javaGroovyCardEquals(
                jdkCardsBySuit.get(jvm.language.compare.java.cards.Suit.DIAMONDS),
                groovyCardsBySuit.get(Suit.DIAMONDS))
        javaGroovyCardEquals(
                jdkCardsBySuit.get(jvm.language.compare.java.cards.Suit.HEARTS),
                groovyCardsBySuit.get(Suit.HEARTS))
        javaGroovyCardEquals(
                jdkCardsBySuit.get(jvm.language.compare.java.cards.Suit.SPADES),
                groovyCardsBySuit.get(Suit.SPADES))
    }

    @Test
    void countsBySuit() {
        assert Suit.values().every { this.groovyDeck.countsBySuit().get(it) == 13 }
        assert jvm.language.compare.java.cards.Suit.values().every {
            this.jdkDeck.countsBySuit().occurrencesOf(it) == 13
        }
    }

    @Test
    void countsByRank() {
        assert Rank.values().every { this.groovyDeck.countsByRank().get(it) == 4 }
        assert jvm.language.compare.java.cards.Rank.values().every {
            this.jdkDeck.countsByRank().occurrencesOf(it) == 4
        }
    }

    @Test
    void dealOneCard() {
        this.groovyDeck.shuffle(new Random(1))
        def card1groovy = this.groovyDeck.dealOneCard()
        assert 51 == this.groovyDeck.cardsLeftInDeck()
        def card2groovy = this.groovyDeck.dealOneCard()
        assert 50 == this.groovyDeck.cardsLeftInDeck()
        assert card1groovy != card2groovy

        this.jdkDeck.shuffle(new Random(1))
        def card1jdk = this.jdkDeck.dealOneCard()
        assert 51 == this.jdkDeck.cardsLeftInDeck()
        def card2jdk = this.jdkDeck.dealOneCard()
        assert 50 == this.jdkDeck.cardsLeftInDeck()
        assert card1jdk != card2jdk

        javaGroovyCardEquals(this.jdkDeck.cards, this.groovyDeck.cards)
    }

    void javaGroovyCardEquals(ListIterable<jvm.language.compare.java.cards.Card> javaCards, List<Card> groovyCards) {
        Assert.assertEquals(javaCards.toString(), groovyCards.toString())
    }

    void javaGroovyCardEquals(MutableSet<jvm.language.compare.java.cards.Card> javaCards, Set<Card> groovyCards) {
        assert javaCards.size() == groovyCards.size()

        // Cannot use collect directly from EC because it invokes the Groovy collect function which returns a List.
        def javaSet = Iterate.collect(javaCards, { card -> card.toString() })

        def groovySet = groovyCards.stream()
                .map { card -> card.toString() }
                .collect(Collectors.toSet())

        Assert.assertEquals(javaSet, groovySet)
    }
}
