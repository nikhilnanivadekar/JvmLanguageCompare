package jvm.language.compare.java.cards;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JavaStreamDeckOfCards
{
    private List<Card> cards = Card.streamCards().sorted()
            .collect(Collectors.toList());
    private Map<Suit, List<Card>> cardsBySuit = this.cards.stream()
            .collect(Collectors.groupingBy(Card::getSuit));
    private Deque<Card> deck = new ArrayDeque<>();

    public JavaStreamDeckOfCards() {
    }

    public void shuffle(Random random)
    {
        List<Card> shuffled = new ArrayList<>(this.cards);
        Collections.shuffle(shuffled, random);
        Collections.shuffle(shuffled, random);
        Collections.shuffle(shuffled, random);
        this.deck = new ArrayDeque<>();
        shuffled.forEach(this.deck::push);
    }

    public Set<Card> deal(int count)
    {
        Set<Card> hand = new HashSet<>();
        IntStream.range(0, count).forEach(i -> hand.add(this.deck.pop()));
        return hand;
    }

    public Card dealOneCard()
    {
        return this.deck.pop();
    }

    public int cardsLeftInDeck() {
        return this.deck.size();
    }

    public List<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        this.shuffle(random);
        return this.dealHands(hands, cardsPerHand);
    }

    public List<Set<Card>> dealHands(int hands, int cardsPerHand)
    {
        return Collections.unmodifiableList(
                IntStream.range(0, hands)
                        .mapToObj(i -> this.deal(cardsPerHand))
                        .collect(Collectors.toList()));
    }

    public List<Card> diamonds()
    {
        return this.cardsBySuit.get(Suit.DIAMONDS);
    }

    public List<Card> hearts()
    {
        return this.cardsBySuit.get(Suit.HEARTS);
    }

    public List<Card> spades()
    {
        return this.cardsBySuit.get(Suit.SPADES);
    }

    public List<Card> clubs()
    {
        return this.cardsBySuit.get(Suit.CLUBS);
    }

    public Map<Suit, Long> countsBySuit()
    {
        return this.cards.stream()
                .collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));
    }

    public Map<Rank, Long> countsByRank()
    {
        return this.cards.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
    }

    public List<Card> getCards()
    {
        return this.cards;
    }

    public Map<Suit, List<Card>> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
