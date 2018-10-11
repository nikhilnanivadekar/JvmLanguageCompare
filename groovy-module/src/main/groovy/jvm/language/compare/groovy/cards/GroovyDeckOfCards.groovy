package jvm.language.compare.groovy.cards

class GroovyDeckOfCards {
    private List<Card> cards
    private Map<Suit, List<Card>> cardsBySuit

    GroovyDeckOfCards() {
        this.cards = Card.cards().sort()
        this.cardsBySuit = this.cards.groupBy { it.getSuit() }
    }

    List<Card> shuffle(Random random) {
        List<Card> shuffled = new ArrayList<>(this.cards)
        3.times { Collections.shuffle(shuffled, random) }
        shuffled
    }

    List<Set<Card>> dealHands(List<Card> shuffled, int count, int cardsPerHand) {
        (1..count).collect({ this.deal(shuffled, cardsPerHand) })
    }

    Set<Card> deal(List<Card> stack, int count) {
        (1..count).collect(new HashSet<>(), { stack.pop() }) as Set<Card>
    }

    Card dealOneCard(List<Card> stack) {
        return stack.pop()
    }

    List<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand) {
        List<Card> shuffled = this.shuffle(random)
        this.dealHands(shuffled, hands, cardsPerHand)
    }

    List<Card> diamonds() {
        this.cardsBySuit.get(Suit.DIAMONDS)
    }

    List<Card> hearts() {
        this.cardsBySuit.get(Suit.HEARTS)
    }

    List<Card> spades() {
        this.cardsBySuit.get(Suit.SPADES)
    }

    List<Card> clubs() {
        this.cardsBySuit.get(Suit.CLUBS)
    }

    Map<Suit, Integer> countsBySuit() {
        this.cards.countBy { it.getSuit() }
    }

    Map<Rank, Integer> countsByRank() {
        this.cards.countBy { it.getRank() }
    }

    List<Card> getCards() {
        this.cards
    }

    Map<Suit, List<Card>> getCardsBySuit() {
        this.cardsBySuit
    }
}
