package jvm.language.compare.groovy.cards

import groovy.transform.Sortable

@Sortable(includes = ['suit', 'rank'])
class Card {
    final Rank rank
    final Suit suit

    Card(Rank rank, Suit suit) {
        this.rank = rank
        this.suit = suit
    }

    static List<Card> cards() {
        Rank.values().collectMany { rank ->
            Suit.values().collect { suit -> new Card(rank, suit) }
        }
    }

    boolean isDiamonds() {
        this.suit == Suit.DIAMONDS
    }

    boolean isHearts() {
        this.suit == Suit.HEARTS
    }

    boolean isSpades() {
        this.suit == Suit.SPADES
    }

    boolean isClubs() {
        this.suit == Suit.CLUBS
    }

    boolean isSameRank(Rank rank) {
        this.rank == rank
    }

    boolean isSameSuit(Suit suit) {
        this.suit == suit
    }

    boolean equals(object) {
        if (this.is(object)) return true
        if (this.getClass() != object.class) return false

        Card card = (Card) object

        this.isSameSuit(card.suit) && this.isSameRank(card.rank)
    }

    int hashCode() {
        31 * rank.hashCode() + suit.hashCode()
    }

    @Override
    String toString() {
        this.rank.toString() + " of " + this.suit.toString()
    }
}

