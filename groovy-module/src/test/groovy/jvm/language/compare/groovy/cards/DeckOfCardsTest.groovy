package jvm.language.compare.groovy.cards

import org.junit.Test

class DeckOfCardsTest {
    private groovyDeck = new GroovyDeckOfCards()

    @Test
    void allCards() {
        assert 52 == this.groovyDeck.getCards().size()
        println(this.groovyDeck.getCards())
    }

    @Test
    void diamonds() {
        assert 13 == this.groovyDeck.diamonds().size()
        assert this.groovyDeck.diamonds().every { it.isDiamonds() }
    }

    @Test
    void hearts() {
        assert 13 == this.groovyDeck.hearts().size()
        assert this.groovyDeck.hearts().every { it.isHearts() }
    }

    @Test
    void spades() {
        assert 13 == this.groovyDeck.spades().size()
        assert this.groovyDeck.spades().every { it.isSpades() }
    }

    @Test
    void clubs() {
        assert 13 == this.groovyDeck.clubs().size()
        assert this.groovyDeck.clubs().every { it.isClubs() }
    }

    @Test
    void deal() {
        def groovyShuffle1 = this.groovyDeck.shuffle(new Random(1))
        def groovyShuffle2 = this.groovyDeck.shuffle(new Random(1))
        assert groovyShuffle1.toList() == groovyShuffle2.toList()
        def groovyShuffle3 = this.groovyDeck.shuffle(new Random(100))
        assert groovyShuffle1.toList() != groovyShuffle3.toList()
        def groovyHand1 = this.groovyDeck.deal(groovyShuffle1, 5)
        def groovyHand2 = this.groovyDeck.deal(groovyShuffle2, 5)
        assert groovyHand1 == groovyHand2
        def groovyHand3 = this.groovyDeck.deal(groovyShuffle3, 5)
        assert groovyHand1 != groovyHand3
    }

    @Test
    void shuffleAndDealHands() {
        def groovyHands = this.groovyDeck.shuffleAndDeal(new Random(1), 5, 5)
        assert 5 == groovyHands.size()
        assert groovyHands.every({ it.size() == 5 })
    }

    @Test
    void dealHands() {
        def groovyShuffled = this.groovyDeck.shuffle(new Random(1))
        def groovyHands = this.groovyDeck.dealHands(groovyShuffled, 5, 5)
        assert 5 == groovyHands.size()
        assert groovyHands.every({ it.size() == 5 })
    }

    @Test
    void cardsBySuit() {
        def scalaCardsBySuit = this.groovyDeck.getCardsBySuit()
        assert scalaCardsBySuit.get(Suit.CLUBS) == scalaCardsBySuit.get(Suit.CLUBS)
        assert scalaCardsBySuit.get(Suit.DIAMONDS) != scalaCardsBySuit.get(Suit.CLUBS)
    }

    @Test
    void countsBySuit() {
        assert Suit.values().every { this.groovyDeck.countsBySuit().get(it) == 13 }
    }

    @Test
    void countsByRank() {
        assert Rank.values().every { this.groovyDeck.countsByRank().get(it) == 4 }
    }

    @Test
    void dealOneCard() {
        def groovyShuffle1 = this.groovyDeck.shuffle(new Random(1))
        def card1 = this.groovyDeck.dealOneCard(groovyShuffle1)
        assert 51 == groovyShuffle1.size()
        def card2 = this.groovyDeck.dealOneCard(groovyShuffle1)
        assert 50 == groovyShuffle1.size()
        assert card1 != card2
    }
}
