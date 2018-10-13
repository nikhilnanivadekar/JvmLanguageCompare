package jvm.language.compare.java.cards;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.eclipse.collections.api.multimap.list.ListMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class DeckOfCardsTest
{
    private JavaDeckOfCards jdkDeck = new JavaDeckOfCards();
    private JavaStreamDeckOfCards streamsDeck = new JavaStreamDeckOfCards();

    @Test
    public void allCards()
    {
        Verify.assertSize(52, this.jdkDeck.getCards());
        Assert.assertEquals(this.jdkDeck.getCards(), this.streamsDeck.getCards());
    }

    @Test
    public void diamonds()
    {
        Verify.assertSize(13, this.jdkDeck.diamonds());
        Verify.assertAllSatisfy(this.jdkDeck.diamonds(), Card::isDiamonds);
        Assert.assertEquals(this.jdkDeck.diamonds(), this.streamsDeck.diamonds());
    }

    @Test
    public void hearts()
    {
        Verify.assertSize(13, this.jdkDeck.hearts());
        Verify.assertAllSatisfy(this.jdkDeck.hearts(), Card::isHearts);
        Assert.assertEquals(this.jdkDeck.hearts(), this.streamsDeck.hearts());
    }

    @Test
    public void spades()
    {
        Verify.assertSize(13, this.jdkDeck.spades());
        Verify.assertAllSatisfy(this.jdkDeck.spades(), Card::isSpades);
        Assert.assertEquals(this.jdkDeck.spades(), this.streamsDeck.spades());
    }

    @Test
    public void clubs()
    {
        Verify.assertSize(13, this.jdkDeck.clubs());
        Verify.assertAllSatisfy(this.jdkDeck.clubs(), Card::isClubs);
        Assert.assertEquals(this.jdkDeck.clubs(), this.streamsDeck.clubs());
    }

    @Test
    public void deal()
    {
        this.jdkDeck.shuffle(new Random(1));
        this.streamsDeck.shuffle(new Random(1));

        MutableSet<Card> jdkHand = this.jdkDeck.deal(5);
        Set<Card> streamsHand = this.streamsDeck.deal(5);

        Verify.assertSize(5, jdkHand);
        Assert.assertEquals(jdkHand, streamsHand);
    }

    @Test
    public void shuffleAndDealHands()
    {
        List<Set<Card>> jdkHands = this.jdkDeck.shuffleAndDeal(new Random(1), 5, 5);
        List<Set<Card>> streamsHands = this.streamsDeck.shuffleAndDeal(new Random(1), 5, 5);
        Verify.assertSize(5, jdkHands);
        Verify.assertAllSatisfy(jdkHands, each -> each.size() == 5);
        Assert.assertEquals(jdkHands, streamsHands);
    }

    @Test
    public void dealHands()
    {
        this.jdkDeck.shuffle(new Random(1));
        this.streamsDeck.shuffle(new Random(1));

        List<Set<Card>> jdkHands = this.jdkDeck.dealHands(5, 5);
        List<Set<Card>> streamsHands = this.streamsDeck.dealHands(5, 5);

        Verify.assertSize(5, jdkHands);
        Verify.assertAllSatisfy(jdkHands, each -> each.size() == 5);
        Assert.assertEquals(jdkHands, streamsHands);
    }

    @Test
    public void cardsBySuit()
    {
        ListMultimap<Suit, Card> jdkCardsBySuit = this.jdkDeck.getCardsBySuit();
        Map<Suit, List<Card>> streamsCardsBySuit = this.streamsDeck.getCardsBySuit();

        Assert.assertEquals(jdkCardsBySuit.get(Suit.CLUBS), streamsCardsBySuit.get(Suit.CLUBS));
        Assert.assertEquals(jdkCardsBySuit.get(Suit.DIAMONDS), streamsCardsBySuit.get(Suit.DIAMONDS));
        Assert.assertEquals(jdkCardsBySuit.get(Suit.HEARTS), streamsCardsBySuit.get(Suit.HEARTS));
        Assert.assertEquals(jdkCardsBySuit.get(Suit.SPADES), streamsCardsBySuit.get(Suit.SPADES));
    }

    @Test
    public void countsBySuit()
    {
        Verify.assertAllSatisfy(EnumSet.allOf(Suit.class), suit -> this.jdkDeck.countsBySuit().occurrencesOf(suit) == 13);
        Verify.assertAllSatisfy(EnumSet.allOf(Suit.class), suit -> this.streamsDeck.countsBySuit().get(suit) == 13);
    }

    @Test
    public void countsByRank()
    {
        Verify.assertAllSatisfy(EnumSet.allOf(Rank.class), rank -> this.jdkDeck.countsByRank().occurrencesOf(rank) == 4);
        Verify.assertAllSatisfy(EnumSet.allOf(Rank.class), rank -> this.streamsDeck.countsByRank().get(rank) == 4);
    }

    @Test
    public void dealOneCard()
    {
        this.jdkDeck.shuffle(new Random(1));
        Card card1jdk = this.jdkDeck.dealOneCard();
        Assert.assertEquals(51, this.jdkDeck.cardsLeftInDeck());
        Card card2jdk = this.jdkDeck.dealOneCard();
        Assert.assertEquals(50, this.jdkDeck.cardsLeftInDeck());
        Assert.assertNotEquals(card1jdk, card2jdk);

        this.streamsDeck.shuffle(new Random(1));
        Card card1streams = this.streamsDeck.dealOneCard();
        Assert.assertEquals(51, this.streamsDeck.cardsLeftInDeck());
        Card card2streams = this.streamsDeck.dealOneCard();
        Assert.assertEquals(50, this.streamsDeck.cardsLeftInDeck());
        Assert.assertNotEquals(card1streams, card2streams);
    }
}
