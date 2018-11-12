package jvm.language.compare.scala.cards

import jvm.language.compare.scala.cards.Rank.Rank
import jvm.language.compare.scala.cards.Suit.Suit

object Card {
  def getCards: Set[Card] =
    Rank.values
      .flatMap(rank =>
        Suit.values.map(suit =>
          new Card(rank, suit)))
}

// case classes are very frequently used because they are so convienient.
// case clases have hashCode, equals and toString methods all defined.
// Kotlin borrowed this idea and calls it "data class".
case class Card(rank: Rank, suit: Suit) extends Ordered[Card] {

  def isDiamonds: Boolean = this.suit eq Suit.DIAMONDS

  def isHearts: Boolean = this.suit eq Suit.HEARTS

  def isSpades: Boolean = this.suit eq Suit.SPADES

  def isClubs: Boolean = this.suit eq Suit.CLUBS

  def isSameRank(rank: Rank): Boolean = this.rank eq rank

  def isSameSuit(rank: Suit): Boolean = this.suit eq suit

  override def compare(that: Card): Int =
    (this.suit, this.rank) compare (that.suit, that.rank)

  override def toString: String = this.rank + " of " + this.suit
}
