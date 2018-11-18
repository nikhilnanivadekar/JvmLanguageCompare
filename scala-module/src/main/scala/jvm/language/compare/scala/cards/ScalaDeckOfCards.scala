package jvm.language.compare.scala.cards

import jvm.language.compare.scala.cards.Rank.Rank
import jvm.language.compare.scala.cards.Suit.Suit

import scala.collection.mutable
import scala.util.Random

class ScalaDeckOfCards() {
  val cards: Seq[Card] = Card.getCards.toBuffer.sorted
  val cardsBySuit: Map[Suit, Seq[Card]] = cards.groupBy(_.suit)
  val deck: mutable.ArrayStack[Card] = mutable.ArrayStack()

  def shuffle(random: Random) {
    val cardsShuffled: Seq[Card] =
      random.shuffle(
        random.shuffle(
          random.shuffle(cards)
        )
      )
    cardsShuffled.foreach((card: Card) => deck.push(card))
    // More advanced but easier to write &read when you get the hang of it.
    //    cardsShuffled.foreach(deck.push)
  }

  def deal(count: Int): mutable.HashSet[Card] = {
    val set: mutable.HashSet[Card] = mutable.HashSet()
    1.to(count).foreach(_ => set.add(deck.pop()))
    set
  }

  def dealOneCard(): Card = deck.pop

  def cardsLeftInDeck(): Int = deck.size

  def shuffleAndDeal(random: Random, hands: Int, cardsPerHand: Int): Seq[mutable.HashSet[Card]] = {
    shuffle(random)
    dealHands(hands, cardsPerHand)
  }

  def dealHands(hands: Int, cardsPerHand: Int): Seq[mutable.HashSet[Card]] = {
    1.to(hands).map(_ => deal(cardsPerHand)).toList
  }

  def diamonds: Seq[Card] = cardsBySuit(Suit.DIAMONDS)

  def hearts: Seq[Card] = cardsBySuit(Suit.HEARTS)

  def spades: Seq[Card] = cardsBySuit(Suit.SPADES)

  def clubs: Seq[Card] = cardsBySuit(Suit.CLUBS)

  def countsBySuit: Map[Suit, Int] = cards.groupBy(_.suit).mapValues(_.length)

  def countsByRank: Map[Rank, Int] = cards.groupBy(_.rank).mapValues(_.length)

  def getCards: Seq[Card] = cards

  def getCardsBySuit: Map[Suit, Seq[Card]] = cardsBySuit
}
