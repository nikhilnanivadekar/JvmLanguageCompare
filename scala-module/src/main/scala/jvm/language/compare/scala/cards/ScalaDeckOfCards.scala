package jvm.language.compare.scala.cards

import jvm.language.compare.scala.cards.Rank.Rank
import jvm.language.compare.scala.cards.Suit.Suit

import scala.collection.immutable.Seq
import scala.collection.mutable
import scala.util.Random

class ScalaDeckOfCards() {
  val cards: List[Card] = Card.getCards.toList.sorted
  val cardsBySuit: Map[Suit, Seq[Card]] = cards.groupBy(_.suit)
  var deck: mutable.ArrayStack[Card] = mutable.ArrayStack()

  def shuffle(random: Random) {
    val cardsShuffled: List[Card] = random.shuffle(cards)
    cardsShuffled.foreach((card: Card) => deck.push(card))
// More advanced but easier to write &read when you get the hang of it.
//    cardsShuffled.foreach(deck.push)
  }

  def deal(count: Int): mutable.HashSet[Card] = {
    val set: mutable.HashSet[Card] = mutable.HashSet()
    1.to(count).foreach(_ => set.add(this.deck.pop()))
    set
  }

  def dealOneCard(): Card = this.deck.pop

  def cardsLeftInDeck(): Int = this.deck.size

  def shuffleAndDeal(random: Random, hands: Int, cardsPerHand: Int): Seq[mutable.HashSet[Card]] = {
    this.shuffle(random)
    this.dealHands(hands, cardsPerHand)
  }

  def dealHands(hands: Int, cardsPerHand: Int): Seq[mutable.HashSet[Card]] = {
    1.to(hands).map(_ => this.deal(cardsPerHand)).toList
  }

  def diamonds: Seq[Card] = this.cardsBySuit(Suit.DIAMONDS)

  def hearts: Seq[Card] = this.cardsBySuit(Suit.HEARTS)

  def spades: Seq[Card] = this.cardsBySuit(Suit.SPADES)

  def clubs: Seq[Card] = this.cardsBySuit(Suit.CLUBS)

  def countsBySuit: Map[Suit, Int] = this.cards.groupBy(_.suit).mapValues(_.length)

  def countsByRank: Map[Rank, Int] = this.cards.groupBy(_.rank).mapValues(_.length)

  def getCards: Seq[Card] = this.cards

  def getCardsBySuit: Map[Suit, Seq[Card]] = this.cardsBySuit
}
