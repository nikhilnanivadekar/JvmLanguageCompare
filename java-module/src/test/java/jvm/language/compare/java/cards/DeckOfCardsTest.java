package jvm.language.compare.java.cards;

import org.eclipse.collections.api.multimap.list.ListMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class DeckOfCardsTest
{
    private JavaDeckOfCards ecDeck = new JavaDeckOfCards();
    private JavaStreamDeckOfCards jdkDeck = new JavaStreamDeckOfCards();

    @Test
    public void allCards()
    {
        Verify.assertSize(52, this.ecDeck.getCards());
        Assert.assertEquals(this.ecDeck.getCards(), this.jdkDeck.getCards());
    }

    @Test
    public void diamonds()
    {
        Verify.assertSize(13, this.ecDeck.diamonds());
        Verify.assertAllSatisfy(this.ecDeck.diamonds(), Card::isDiamonds);
        Assert.assertEquals(this.ecDeck.diamonds(), this.jdkDeck.diamonds());
    }

    @Test
    public void hearts()
    {
        Verify.assertSize(13, this.ecDeck.hearts());
        Verify.assertAllSatisfy(this.ecDeck.hearts(), Card::isHearts);
        Assert.assertEquals(this.ecDeck.hearts(), this.jdkDeck.hearts());
    }

    @Test
    public void spades()
    {
        Verify.assertSize(13, this.ecDeck.spades());
        Verify.assertAllSatisfy(this.ecDeck.spades(), Card::isSpades);
        Assert.assertEquals(this.ecDeck.spades(), this.jdkDeck.spades());
    }

    @Test
    public void clubs()
    {
        Verify.assertSize(13, this.ecDeck.clubs());
        Verify.assertAllSatisfy(this.ecDeck.clubs(), Card::isClubs);
        Assert.assertEquals(this.ecDeck.clubs(), this.jdkDeck.clubs());
    }

    @Test
    public void deal()
    {
        MutableStack<Card> ec1Shuffle = this.ecDeck.shuffle(new Random(1));
        Deque<Card> jdkShuffle = this.jdkDeck.shuffle(new Random(1));

        MutableSet<Card> ec1Hand = this.ecDeck.deal(ec1Shuffle, 5);
        Set<Card> jdkHand = this.jdkDeck.deal(jdkShuffle, 5);

        Verify.assertSize(5, ec1Hand);
        Assert.assertEquals(ec1Hand, jdkHand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        List<Set<Card>> ec1Hands = this.ecDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        Verify.assertSize(5, ec1Hands);
        Assert.assertEquals(ec1Hands, jdkHands);
    }

    @Test
    public void dealHands()
    {
        MutableStack<Card> ecShuffled = this.ecDeck.shuffle(new Random(1));
        Deque<Card> jdkShuffled = this.jdkDeck.shuffle(new Random(1));

        List<Set<Card>> ec1Hands = this.ecDeck.dealHands(ecShuffled, 5, 5);
        List<Set<Card>> jdkHands = this.jdkDeck.dealHands(jdkShuffled, 5, 5);

        Verify.assertSize(5, ec1Hands);
        Assert.assertEquals(ec1Hands, jdkHands);
    }

    @Test
    public void cardsBySuit()
    {
        ListMultimap<Suit, Card> ecCardsBySuit = this.ecDeck.getCardsBySuit();
        Map<Suit, List<Card>> jdkCardsBySuit = this.jdkDeck.getCardsBySuit();

        Assert.assertEquals(ecCardsBySuit.get(Suit.CLUBS), jdkCardsBySuit.get(Suit.CLUBS));
        Assert.assertEquals(ecCardsBySuit.get(Suit.DIAMONDS), jdkCardsBySuit.get(Suit.DIAMONDS));
        Assert.assertEquals(ecCardsBySuit.get(Suit.HEARTS), jdkCardsBySuit.get(Suit.HEARTS));
        Assert.assertEquals(ecCardsBySuit.get(Suit.SPADES), jdkCardsBySuit.get(Suit.SPADES));
    }

    @Test
    public void countsBySuit()
    {
        Verify.assertAllSatisfy(EnumSet.allOf(Suit.class), suit -> this.ecDeck.countsBySuit().occurrencesOf(suit) == 13);
        Verify.assertAllSatisfy(EnumSet.allOf(Suit.class), suit -> this.jdkDeck.countsBySuit().get(suit) == 13);
    }

    @Test
    public void countsByRank()
    {
        Verify.assertAllSatisfy(EnumSet.allOf(Rank.class), rank -> this.ecDeck.countsByRank().occurrencesOf(rank) == 4);
        Verify.assertAllSatisfy(EnumSet.allOf(Rank.class), rank -> this.jdkDeck.countsByRank().get(rank) == 4);
    }

    @Test
    public void dealOneCard()
    {
        MutableStack<Card> ecShuffled = this.ecDeck.shuffle(new Random(1));
        Card card1ec = this.ecDeck.dealOneCard(ecShuffled);
        Verify.assertSize(51, ecShuffled);
        Card card2ec = this.ecDeck.dealOneCard(ecShuffled);
        Verify.assertSize(50, ecShuffled);
        Assert.assertNotEquals(card1ec, card2ec);

        Deque<Card> jdkShuffled = this.jdkDeck.shuffle(new Random(1));
        Card card1jdk = this.jdkDeck.dealOneCard(jdkShuffled);
        Verify.assertSize(51, jdkShuffled);
        Card card2jdk = this.jdkDeck.dealOneCard(jdkShuffled);
        Verify.assertSize(50, jdkShuffled);
        Assert.assertNotEquals(card1jdk, card2jdk);
    }
}
