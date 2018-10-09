package jvm.language.compare.groovy.cards

class GroovyDeckOfCards {
    private List<Card> cards
    private Map<Suit, List<Card>> cardsBySuit

    GroovyDeckOfCards() {
        this.cards = Card.cards().sort()
        this.cardsBySuit = this.cards.groupBy { it.getSuit() }
    }

    Deque<Card> shuffle(Random random) {
        List<Card> shuffled = new ArrayList<>(this.cards)
        Collections.shuffle(shuffled, random)
        Collections.shuffle(shuffled, random)
        Collections.shuffle(shuffled, random)
        new ArrayDeque<Card>(shuffled)
    }

    Set<Card> deal(Deque<Card> deque, int count) {
        (1..count).inject(
                new HashSet<>(),
                { set, each -> set.add(deque.pop()); set })
    }

    Card dealOneCard(Deque<Card> deque) {
        return deque.pop()
    }

    List<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand) {
        Deque<Card> shuffled = this.shuffle(random)
        this.dealHands(shuffled, hands, cardsPerHand)
    }

    List<Set<Card>> dealHands(Deque<Card> shuffled, int count, int cardsPerHand) {
        (1..count).inject(
                new ArrayList<>(),
                { list, each -> list.add(this.deal(shuffled, cardsPerHand)); list })
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

    Map<Suit, Long> countsBySuit() {
        this.cards.countBy { it.getSuit() }
    }

    Map<Rank, Long> countsByRank() {
        this.cards.countBy { it.getRank() }
    }

    List<Card> getCards() {
        this.cards
    }

    Map<Suit, List<Card>> getCardsBySuit() {
        this.cardsBySuit
    }
}
