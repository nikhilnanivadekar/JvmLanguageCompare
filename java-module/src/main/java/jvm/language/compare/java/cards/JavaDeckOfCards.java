package jvm.language.compare.java.cards;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.multimap.list.ListMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Stacks;
import org.eclipse.collections.impl.list.primitive.IntInterval;

public class JavaDeckOfCards
{
    private MutableList<Card> cards;
    private ListMultimap<Suit, Card> cardsBySuit;
    private MutableStack<Card> deck = Stacks.mutable.empty();

    public JavaDeckOfCards()
    {
        this.cards = Card.getCards().toSortedList();
        this.cardsBySuit = this.cards.groupBy(Card::getSuit);
    }

    public void shuffle(Random random)
    {
        this.deck = this.cards.toList()
                .shuffleThis(random)
                .shuffleThis(random)
                .shuffleThis(random).toStack();
    }

    public MutableSet<Card> deal(int count)
    {
        return this.deck.pop(count).toSet();
    }

    public Card dealOneCard()
    {
        return this.deck.pop();
    }

    public int cardsLeftInDeck()
    {
        return this.deck.size();
    }

    public List<Set<Card>> shuffleAndDeal(Random random, int hands, int cardsPerHand)
    {
        this.shuffle(random);
        return this.dealHands(hands, cardsPerHand);
    }

    public List<Set<Card>> dealHands(int hands, int cardsPerHand)
    {
        return IntInterval.oneTo(hands).collect(i -> this.deal(cardsPerHand), Lists.mutable.empty());
    }

    public ListIterable<Card> diamonds()
    {
        return this.cardsBySuit.get(Suit.DIAMONDS);
    }

    public ListIterable<Card> hearts()
    {
        return this.cardsBySuit.get(Suit.HEARTS);
    }

    public ListIterable<Card> spades()
    {
        return this.cardsBySuit.get(Suit.SPADES);
    }

    public ListIterable<Card> clubs()
    {
        return this.cardsBySuit.get(Suit.CLUBS);
    }

    public Bag<Suit> countsBySuit()
    {
        return this.cards.countBy(Card::getSuit);
    }

    public Bag<Rank> countsByRank()
    {
        return this.cards.countBy(Card::getRank);
    }

    public ListIterable<Card> getCards()
    {
        return this.cards;
    }

    public ListMultimap<Suit, Card> getCardsBySuit()
    {
        return this.cardsBySuit;
    }
}
