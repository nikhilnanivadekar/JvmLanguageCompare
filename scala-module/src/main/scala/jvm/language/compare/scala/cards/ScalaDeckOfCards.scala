package jvm.language.compare.scala.cards

import jvm.language.compare.scala.cards.Rank.Rank
import jvm.language.compare.scala.cards.Suit.Suit

import scala.collection.mutable
import scala.util.Random

class ScalaDeckOfCards() {
  val cards: Seq[Card] = Card.getCards.toBuffer.sorted
  val cardsBySuit: Map[Suit, Seq[Card]] = this.cards.groupBy(_.suit)
  var deck: mutable.ArrayStack[Card] = mutable.ArrayStack()

  def shuffle(random: Random) {
    var list: List[Card] = random.shuffle(this.cards.toList)
    list = random.shuffle(list)
    list = random.shuffle(list)
    list.foreach((card: Card) => this.deck.push(card))
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
