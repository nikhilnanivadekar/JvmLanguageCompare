package jvm.language.compare.kotlin.cards

data class Card constructor(val rank: Rank, val suit: Suit) : Comparable<Card> {

    companion object {
        fun getCards(): List<Card> {
            return Rank.values()
                    .flatMap { first ->
                        Suit.values()
                                .map { second ->
                                    Card(first, second)
                                }
                    }
        }
    }

    override fun toString(): String {
        return rank.toString() + " of " + suit.toString()
    }

    override fun compareTo(other: Card): Int {
        return Comparator.comparing<Card, Suit>(Card::suit)
                .thenBy(Card::rank)
                .compare(this, other)
    }

    fun isDiamonds(): Boolean {
        return this.suit == Suit.DIAMONDS
    }

    fun isHearts(): Boolean {
        return this.suit == Suit.HEARTS
    }

    fun isSpades(): Boolean {
        return this.suit == Suit.SPADES
    }

    fun isClubs(): Boolean {
        return this.suit == Suit.CLUBS
    }

    fun isSameRank(rank: Rank): Boolean {
        return this.rank == rank
    }

    fun isSameSuit(suit: Suit): Boolean {
        return this.suit == suit
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other is Card) {
            val card = other as Card?
            return this.isSameSuit(card!!.suit) && this.isSameRank(card.rank)
        }
        return false
    }

    override fun hashCode(): Int {
        return 31 * rank.hashCode() + suit.hashCode()
    }
}
