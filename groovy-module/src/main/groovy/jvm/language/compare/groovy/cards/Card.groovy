package jvm.language.compare.groovy.cards

import groovy.transform.Sortable

@Sortable(includes = ['suit', 'rank'])
class Card {
    private final Rank rank
    private final Suit suit

    Card(Rank rank, Suit suit) {
        this.rank = rank
        this.suit = suit
    }

    static List<Card> cards() {
        Set<Rank> ranks = EnumSet.allOf(Rank.class)
        Set<Suit> suits = EnumSet.allOf(Suit.class)
        ranks.collectMany { rank ->
            suits.collect { suit -> new Card(rank, suit) }
        }
    }

    Rank getRank() {
        this.rank
    }

    Suit getSuit() {
        this.suit
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

    boolean equals(Object object) {
        if (this == object) {
            return true
        }
        if (!(object instanceof Card)) {
            return false
        }
        Card card = (Card) object
        this.isSameRank(card.rank) && this.isSameSuit(card.suit)
    }

    int hashCode() {
        int result = 31 + this.rank.hashCode()
        return 31 * result + this.suit.hashCode()
    }

    @Override
    String toString() {
        this.rank.toString() + " of " + this.suit.toString()
    }
}

