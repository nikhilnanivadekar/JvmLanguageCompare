package jvm.language.compare.scala.cards

import jvm.language.compare.scala.cards.Rank.Rank
import jvm.language.compare.scala.cards.Suit.Suit

object Card {
  def getCards: Set[Card] =
    Rank.values.flatMap(rank => Suit.values.map(suit => new Card(rank, suit)))
}

class Card(val rank: Rank, val suit: Suit) extends Ordered[Card] {
  def getRank: Rank = this.rank

  def getSuit: Suit = this.suit

  def isDiamonds: Boolean = this.suit eq Suit.DIAMONDS

  def isHearts: Boolean = this.suit eq Suit.HEARTS

  def isSpades: Boolean = this.suit eq Suit.SPADES

  def isClubs: Boolean = this.suit eq Suit.CLUBS

  def isSameRank(rank: Rank): Boolean = this.rank eq rank

  def isSameSuit(rank: Suit): Boolean = this.suit eq suit

  override def compare(that: Card): Int =
    (this.suit, this.rank) compare(that.suit, that.rank)

  def canEqual(other: Any): Boolean = other.isInstanceOf[Card]

  override def equals(other: Any): Boolean = other match {
    case that: Card =>
      (that canEqual this) &&
        this.isSameSuit(that.suit) &&
        this.isSameRank(that.rank)
    case _ => false
  }

  override def hashCode(): Int = 31 * rank.hashCode + suit.hashCode

  override def toString: String = this.rank + " of " + this.suit
}
