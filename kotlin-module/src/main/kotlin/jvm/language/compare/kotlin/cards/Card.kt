package jvm.language.compare.kotlin.cards

data class Card (val rank: Rank, val suit: Suit) : Comparable<Card> {

    companion object {
        fun getCards() = Rank.values()
                .flatMap { first ->
                    Suit.values().map {
                        Card(first, it)
                    }
                }
    }

    override fun toString() = rank.toString() + " of " + suit.toString()

    override fun compareTo(other: Card) = Comparator.comparing<Card, Suit>(Card::suit)
                .thenBy(Card::rank)
                .compare(this, other)

    fun isDiamonds() = this.suit == Suit.DIAMONDS

    fun isHearts() = this.suit == Suit.HEARTS

    fun isSpades() = this.suit == Suit.SPADES

    fun isClubs() = this.suit == Suit.CLUBS

    fun isSameRank(rank: Rank) = this.rank == rank

    fun isSameSuit(suit: Suit) = this.suit == suit
}
