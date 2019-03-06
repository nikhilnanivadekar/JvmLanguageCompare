package jvm.language.compare.groovy.cards

class GroovyDeckOfCards {
    private List<Card> cards = Card.cards().sort()
    private Map<Suit, List<Card>> cardsBySuit = this.cards.groupBy { it.getSuit() }
    private List<Card> deck = []

    GroovyDeckOfCards() {
    }

    void shuffle(Random random) {
        def cardCopy = new ArrayList<>(this.cards)
        3.times { Collections.shuffle(cardCopy, random) }
        // The list has to be reversed to behave like a Stack similar to other decks
        // The pop() method in Groovy prior to 2.5 removed elements from the end of the List.
        // Starting 2.5+ the pop() removes from the front of the list.
        this.deck = cardCopy.reverse()
    }

    Set<Card> deal(int count) {
        (1..count).collect(new HashSet<>(), { this.deck.pop() }) as Set<Card>
    }

    Card dealOneCard() {
        return this.deck.pop()
    }

    int cardsLeftInDeck() {
        this.deck.size()
    }

    List<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand) {
        this.shuffle(random)
        this.dealHands(hands, cardsPerHand)
    }

    List<Set<Card>> dealHands(int count, int cardsPerHand) {
        (1..count).collect({ this.deal(cardsPerHand) })
    }

    List<Card> diamonds() {
        this.cardsBySuit[Suit.DIAMONDS]
    }

    List<Card> hearts() {
        this.cardsBySuit[Suit.HEARTS]
    }

    List<Card> spades() {
        this.cardsBySuit[Suit.SPADES]
    }

    List<Card> clubs() {
        this.cardsBySuit[Suit.CLUBS]
    }

    Map<Suit, Integer> countsBySuit() {
        this.cards.countBy { it.suit }
    }

    Map<Rank, Integer> countsByRank() {
        this.cards.countBy { it.rank }
    }

    List<Card> getCards() {
        this.cards
    }

    Map<Suit, List<Card>> getCardsBySuit() {
        this.cardsBySuit
    }
}
