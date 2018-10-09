package jvm.language.compare.scala.cards

import org.junit.Test

import scala.util.Random

class DeckOfCardsTest {
  private val scalaDeck = new ScalaDeckOfCards()

  @Test def allCards(): Unit = {
    assert(52 == this.scalaDeck.getCards.size)
    println(this.scalaDeck.getCards)
  }

  @Test def diamonds(): Unit = {
    assert(13 == this.scalaDeck.diamonds.size)
    assert(this.scalaDeck.diamonds.forall(_.isDiamonds))
  }

  @Test def hearts(): Unit = {
    assert(13 == this.scalaDeck.hearts.size)
    assert(this.scalaDeck.hearts.forall(_.isHearts))
  }

  @Test def spades(): Unit = {
    assert(13 == this.scalaDeck.spades.size)
    assert(this.scalaDeck.spades.forall(_.isSpades))
  }

  @Test def clubs(): Unit = {
    assert(13 == this.scalaDeck.clubs.size)
    assert(this.scalaDeck.clubs.forall(_.isClubs))
  }

  @Test def deal(): Unit = {
    val scalaShuffle1 = this.scalaDeck.shuffle(new Random(1))
    val scalaShuffle2 = this.scalaDeck.shuffle(new Random(1))
    assert(scalaShuffle1 == scalaShuffle2)
    val scalaShuffle3 = this.scalaDeck.shuffle(new Random(100))
    assert(scalaShuffle1 != scalaShuffle3)
    val scalaHand1 = this.scalaDeck.deal(scalaShuffle1, 5)
    val scalaHand2 = this.scalaDeck.deal(scalaShuffle2, 5)
    assert(scalaHand1 == scalaHand2)
    val scalaHand3 = this.scalaDeck.deal(scalaShuffle3, 5)
    assert(scalaHand1 != scalaHand3)
  }

  @Test def shuffleAndDealHands(): Unit = {
    val scalaHands = this.scalaDeck.shuffleAndDeal(new Random(1), 5, 5)
    assert(5 == scalaHands.size)
    assert(scalaHands.forall(_.size == 5))
  }

  @Test def dealHands(): Unit = {
    val scalaShuffled = this.scalaDeck.shuffle(new Random(1))
    val scalaHands = this.scalaDeck.dealHands(scalaShuffled, 5, 5)
    assert(5 == scalaHands.size)
    assert(scalaHands.forall(_.size == 5))
  }

  @Test def cardsBySuit(): Unit = {
    val scalaCardsBySuit = this.scalaDeck.getCardsBySuit
    assert(scalaCardsBySuit(Suit.CLUBS) == scalaCardsBySuit(Suit.CLUBS))
    assert(scalaCardsBySuit(Suit.DIAMONDS) != scalaCardsBySuit(Suit.CLUBS))
  }

  @Test def countsBySuit(): Unit = {
    assert(Suit.values.forall(this.scalaDeck.countsBySuit(_) == 13))
  }

  @Test def countsByRank(): Unit = {
    assert(Rank.values.forall(this.scalaDeck.countsByRank(_) == 4))
  }

  @Test def dealOneCard(): Unit = {
    val scalaShuffle1 = this.scalaDeck.shuffle(new Random(1))
    val card1 = this.scalaDeck.dealOneCard(scalaShuffle1)
    assert(51 == scalaShuffle1.size)
    val card2 = this.scalaDeck.dealOneCard(scalaShuffle1)
    assert(50 == scalaShuffle1.size)
    assert(card1 != card2)
  }
}
