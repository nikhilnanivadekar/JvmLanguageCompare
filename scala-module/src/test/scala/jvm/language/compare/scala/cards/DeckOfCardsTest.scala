package jvm.language.compare.scala.cards

import java.util

import jvm.language.compare.java.cards
import jvm.language.compare.java.cards.JavaDeckOfCards
import org.eclipse.collections.api.list.ListIterable
import org.junit.{Assert, Test}

import scala.collection.mutable
import scala.util.Random

class DeckOfCardsTest {
  private val scalaDeck = new ScalaDeckOfCards()
  private val jdkDeck = new JavaDeckOfCards()

  @Test def allCards(): Unit = {
    assert(52 == this.scalaDeck.getCards.size)
    assert(52 == this.scalaDeck.getCards.toSet.size)
    javaScalaCardEquals(this.jdkDeck.getCards, this.scalaDeck.cards)
  }

  @Test def diamonds(): Unit = {
    assert(13 == this.scalaDeck.diamonds.size)
    assert(this.scalaDeck.diamonds.forall(_.isDiamonds))
    javaScalaCardEquals(this.jdkDeck.diamonds(), this.scalaDeck.diamonds)
  }

  @Test def hearts(): Unit = {
    assert(13 == this.scalaDeck.hearts.size)
    assert(this.scalaDeck.hearts.forall(_.isHearts))
    javaScalaCardEquals(this.jdkDeck.hearts(), this.scalaDeck.hearts)
  }

  @Test def spades(): Unit = {
    assert(13 == this.scalaDeck.spades.size)
    assert(this.scalaDeck.spades.forall(_.isSpades))
    javaScalaCardEquals(this.jdkDeck.spades(), this.scalaDeck.spades)
  }

  @Test def clubs(): Unit = {
    assert(13 == this.scalaDeck.clubs.size)
    assert(this.scalaDeck.clubs.forall(_.isClubs))
    javaScalaCardEquals(this.jdkDeck.clubs(), this.scalaDeck.clubs)
  }

  @Test def deal(): Unit = {
    this.scalaDeck.shuffle(new Random(1))
    this.jdkDeck.shuffle(new java.util.Random(1))

    val scalaHand = this.scalaDeck.deal(5)
    val jdkHand = this.jdkDeck.deal(5)

    assert(scalaHand.size == 5)
    javaScalaCardEquals(jdkHand, scalaHand)
  }


  @Test def shuffleAndDealHands(): Unit = {
    val scalaHands = this.scalaDeck.shuffleAndDeal(new Random(1), 5, 5)
    val jdkHands = this.jdkDeck.shuffleAndDeal(new java.util.Random(1), 5, 5)
    assert(5 == scalaHands.size)
    assert(scalaHands.forall(_.size == 5))

    scalaHands.zipWithIndex foreach { case (scalaHand, index) =>
      val jdkHand = jdkHands.get(index)
      javaScalaCardEquals(jdkHand, scalaHand)
    }
  }

  @Test def dealHands(): Unit = {
    this.scalaDeck.shuffle(new Random(1))
    this.jdkDeck.shuffle(new java.util.Random(1))

    val scalaHands = this.scalaDeck.dealHands(5, 5)
    val jdkHands = this.jdkDeck.dealHands(5, 5)

    assert(5 == scalaHands.size)
    assert(scalaHands.forall(_.size == 5))

    scalaHands.zipWithIndex foreach { case (scalaHand, index) =>
      val jdkHand = jdkHands.get(index)
      javaScalaCardEquals(jdkHand, scalaHand)
    }
  }

  @Test def cardsBySuit(): Unit = {
    val scalaCardsBySuit = this.scalaDeck.getCardsBySuit
    val jdkCardsBySuit = this.jdkDeck.getCardsBySuit

    javaScalaCardEquals(
      jdkCardsBySuit.get(jvm.language.compare.java.cards.Suit.CLUBS),
      scalaCardsBySuit(Suit.CLUBS))
    javaScalaCardEquals(
      jdkCardsBySuit.get(jvm.language.compare.java.cards.Suit.DIAMONDS),
      scalaCardsBySuit(Suit.DIAMONDS))
    javaScalaCardEquals(
      jdkCardsBySuit.get(jvm.language.compare.java.cards.Suit.HEARTS),
      scalaCardsBySuit(Suit.HEARTS))
    javaScalaCardEquals(
      jdkCardsBySuit.get(jvm.language.compare.java.cards.Suit.SPADES),
      scalaCardsBySuit(Suit.SPADES))
  }

  @Test def countsBySuit(): Unit = {
    assert(Suit.values.forall(this.scalaDeck.countsBySuit(_) == 13))
    assert(cards.Suit.values.forall(this.jdkDeck.countsBySuit.occurrencesOf(_) == 13))
  }

  @Test def countsByRank(): Unit = {
    assert(Rank.values.forall(this.scalaDeck.countsByRank(_) == 4))
    assert(cards.Rank.values.forall(this.jdkDeck.countsByRank.occurrencesOf(_) == 4))
  }

  @Test def dealOneCard(): Unit = {
    this.scalaDeck.shuffle(new Random(1))
    val card1scala = this.scalaDeck.dealOneCard()
    assert(51 == this.scalaDeck.cardsLeftInDeck())
    val card2scala = this.scalaDeck.dealOneCard()
    assert(50 == this.scalaDeck.cardsLeftInDeck())
    assert(card1scala != card2scala)

    this.jdkDeck.shuffle(new java.util.Random(1))
    val card1jdk = this.jdkDeck.dealOneCard()
    assert(51 == this.jdkDeck.cardsLeftInDeck())
    val card2jdk = this.jdkDeck.dealOneCard()
    assert(50 == this.jdkDeck.cardsLeftInDeck())
    assert(card1jdk != card2jdk)

    javaScalaCardEquals(this.jdkDeck.getCards, this.scalaDeck.cards)
  }

  def javaScalaCardEquals(javaCards: ListIterable[cards.Card], scalaCards: Seq[Card]): Unit = {
    Assert.assertEquals(javaCards.toString, "[" + scalaCards.mkString(", ") + "]")
  }

  def javaScalaCardEquals(javaCards: util.Set[cards.Card], scalaCards: mutable.HashSet[Card]): Unit = {
    assert(javaCards.size() == scalaCards.size)

    val javaSet: mutable.HashSet[String] = mutable.HashSet()
    javaCards.forEach((card: cards.Card) => javaSet.add(card.toString))

    val scalaSet = scalaCards.map(card => card.toString)

    Assert.assertEquals(javaSet, scalaSet)
  }
}
