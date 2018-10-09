package jvm.language.compare.scala.cards

import jvm.language.compare.scala.cards.Rank.Rank
import jvm.language.compare.scala.cards.Suit.Suit

import scala.collection.mutable
import scala.util.Random

class ScalaDeckOfCards() {
  val cards: Seq[Card] = Card.getCards.toBuffer.sorted
  val cardsBySuit: Map[Suit, Seq[Card]] = this.cards.groupBy(_.suit)

  def shuffle(random: Random): mutable.ArrayStack[Card] = {
    var list: List[Card] = random.shuffle(this.cards.toList)
    list = random.shuffle(list)
    list = random.shuffle(list)
    val stack: mutable.ArrayStack[Card] = mutable.ArrayStack()
    list.foreach((card: Card) => stack.push(card))
    stack
  }

  def deal(stack: mutable.ArrayStack[Card], count: Int): mutable.HashSet[Card] = {
    val set: mutable.HashSet[Card] = mutable.HashSet()
    1.to(count).foreach(_ => set.add(stack.pop()))
    set
  }

  def dealOneCard(stack: mutable.ArrayStack[Card]): Card = stack.pop

  def shuffleAndDeal(random: Random, hands: Int, cardsPerHand: Int): Seq[mutable.HashSet[Card]] = {
    val shuffled = this.shuffle(random)
    this.dealHands(shuffled, hands, cardsPerHand)
  }

  def dealHands(shuffled: mutable.ArrayStack[Card], hands: Int, cardsPerHand: Int): Seq[mutable.HashSet[Card]] = {
    1.to(hands).map(_ => this.deal(shuffled, cardsPerHand)).toList
  }

  def diamonds: Seq[Card] = this.cardsBySuit(Suit.DIAMONDS)

  def hearts: Seq[Card] = this.cardsBySuit(Suit.HEARTS)

  def spades: Seq[Card] = this.cardsBySuit(Suit.SPADES)

  def clubs: Seq[Card] = this.cardsBySuit(Suit.CLUBS)

  def countsBySuit: Map[Suit, Int] = this.cards.groupBy(_.getSuit).map{ case (suit, seq) => (suit, seq.size)}

  def countsByRank: Map[Rank, Int] = this.cards.groupBy(_.getRank).map{ case (suit, seq) => (suit, seq.size)}

  def getCards: Seq[Card] = this.cards

  def getCardsBySuit: Map[Suit, Seq[Card]] = this.cardsBySuit
}
