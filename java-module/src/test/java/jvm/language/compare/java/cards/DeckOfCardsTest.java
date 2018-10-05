package jvm.language.compare.java.cards;

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.multimap.list.ImmutableListMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.collector.Collectors2;
import org.junit.Assert;
import org.junit.Test;

public class DeckOfCardsTest
{
    private EclipseCollectionsDeckOfCards ecDeck1 = new EclipseCollectionsDeckOfCards();
    private JDK8DeckOfCards jdkDeck = new JDK8DeckOfCards();

    @Test
    public void allCards()
    {
        Assert.assertEquals(this.ecDeck1.getCards(), this.jdkDeck.getCards());
    }

    @Test
    public void diamonds()
    {
        Assert.assertEquals(this.ecDeck1.diamonds(), this.jdkDeck.diamonds());
    }

    @Test
    public void hearts()
    {
        Assert.assertEquals(this.ecDeck1.hearts(), this.jdkDeck.hearts());
    }

    @Test
    public void spades()
    {
        Assert.assertEquals(this.ecDeck1.spades(), this.jdkDeck.spades());
    }

    @Test
    public void clubs()
    {
        Assert.assertEquals(this.ecDeck1.clubs(), this.jdkDeck.clubs());
    }

    @Test
    public void deal()
    {
        MutableStack<Card> ec1Shuffle = this.ecDeck1.shuffle(new Random(1));
        Deque<Card> jdkShuffle = this.jdkDeck.shuffle(new Random(1));

        MutableSet<Card> ec1Hand = this.ecDeck1.deal(ec1Shuffle, 5);
        Set<Card> jdkHand = this.jdkDeck.deal(jdkShuffle, 5);

        Assert.assertEquals(ec1Hand, jdkHand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        ImmutableList<Set<Card>> ec1Hands = this.ecDeck1.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        Assert.assertEquals(ec1Hands, jdkHands);
    }

    @Test
    public void dealHands()
    {
        MutableStack<Card> ecShuffled = this.ecDeck1.shuffle(new Random(1));
        Deque<Card> jdkShuffled = this.jdkDeck.shuffle(new Random(1));

        ImmutableList<Set<Card>> ec1Hands = this.ecDeck1.dealHands(ecShuffled, 5, 5);
        List<Set<Card>> jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);

        Assert.assertEquals(ec1Hands, jdkHands);
    }

    @Test
    public void cardsBySuit()
    {
        ImmutableListMultimap<Suit, Card> ecCardsBySuit = this.ecDeck1.getCardsBySuit();
        Map<Suit, List<Card>> jdkCardsBySuit = this.jdkDeck.getCardsBySuit();

        Assert.assertEquals(ecCardsBySuit.get(Suit.CLUBS), jdkCardsBySuit.get(Suit.CLUBS));
    }

    @Test
    public void countsBySuit()
    {
        Assert.assertEquals(13, this.ecDeck1.countsBySuit().occurrencesOf(Suit.CLUBS));
        Assert.assertEquals(Long.valueOf(13), this.jdkDeck.countsBySuit().get(Suit.CLUBS));
    }

    @Test
    public void countsByRank()
    {
        Assert.assertEquals(4, this.ecDeck1.countsByRank().occurrencesOf(Rank.SEVEN));
        Assert.assertEquals(Long.valueOf(4), this.jdkDeck.countsByRank().get(Rank.TEN));
    }

    @Test
    public void goodDeals()
    {
        Random random = new Random();
        Predicate<Set<Card>> pair = each ->
                each.stream().map(Card::getRank).collect(Collectors2.toBag()).sizeDistinct() == 4;
        Supplier<ImmutableList<Set<Card>>> generator = () -> this.ecDeck1.shuffleAndDeal(random, 5, 5);
        Set<Card> pairOrBetter = Stream.generate(generator)
                .filter(hands -> hands.anySatisfy(pair))
                .findFirst()
                .get()
                .detect(pair);

        Predicate<Set<Card>> twoPairs = each ->
                each.stream().map(Card::getRank).collect(Collectors2.toBag()).sizeDistinct() == 3;
        Set<Card> twoPairsOrBetter = Stream.generate(generator)
                .filter(hands -> hands.anySatisfy(twoPairs))
                .findFirst()
                .get()
                .detect(twoPairs);

        Predicate<Set<Card>> fullHouse = each ->
                each.stream().map(Card::getRank).collect(Collectors2.toBag()).sizeDistinct() == 2;
        Set<Card> fullHouseOrBetter = Stream.generate(generator)
                .filter(hands -> hands.anySatisfy(fullHouse))
                .findFirst()
                .get()
                .detect(fullHouse);
    }
}
