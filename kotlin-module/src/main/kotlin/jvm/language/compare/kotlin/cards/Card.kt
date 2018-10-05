package jvm.language.compare.kotlin.cards

data class Card constructor(val rank: Rank, val suit: Suit) : Comparable<Card> {

    override fun compareTo(other: Card): Int {
        return Comparator.comparing<Card, Suit>(Card::suit)
                .thenBy(Card::rank)
                .compare(this, other)
    }

    companion object {
        fun getCards(): MutableList<Card> {
            var cards: MutableList<Card> = ArrayList(
                    Rank.values()
                            .flatMap { first ->
                                Suit.values()
                                        .map { second ->
                                            Card(first, second)
                                        }
                            })
            cards.sort()
            return cards
        }
    }

    override fun toString(): String {
        return rank.toString() + " of " + suit.toString()
    }
}
