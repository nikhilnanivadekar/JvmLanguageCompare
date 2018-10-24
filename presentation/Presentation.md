JVM Language Compare: Java, Kotlin, Groovy, Scala
-------------------------------

Agenda
------

1. Domain Diagram 
2. Properties and Fields
3. Constructors
4. Getters and Setters
5. Enums
6. Ranges
7. Equals and HashCode
8. Cartesian Product
9. Sorting
10. Group-By

Domain Diagram
--------------


Properties and Fields
---------------------
* Java
Card:
```java
private final Rank rank;
private final Suit suit;
```
JavaDeckOfCards:
```java
private MutableList<Card> cards;
private ListMultimap<Suit, Card> cardsBySuit;
private MutableStack<Card> deck = Stacks.mutable.empty();
```
* Kotlin
Card:
```kotlin
val rank: Rank 
val suit: Suit
```
KotlinDeckOfCards:
```kotlin
val cards: List<Card>
val cardsBySuit: Map<Suit, List<Card>>
var deck: MutableStack<Card>
```
* Groovy
Card:
```groovy
private final Rank rank
private final Suit suit
```
GroovyDeckOfCards:
```groovy
private List<Card> cards
private Map<Suit, List<Card>> cardsBySuit
private List<Card> deck = new ArrayList<>()
```
* Scala
Card:
```scala
val rank: Rank 
val suit: Suit
```
ScalaDeckOfCards:
```scala
val cards: Seq[Card]
val cardsBySuit: Map[Suit, Seq[Card]]
var deck: mutable.ArrayStack[Card]
```


Constructors
-----------
* Java
```java
public Card(Rank rank, Suit suit) {
    this.rank = rank;
    this.suit = suit;
}

public JavaDeckOfCards() {
    this.cards = Card.getCards().toSortedList();
    this.cardsBySuit = this.cards.groupBy(Card::getSuit);
}
```
* Kotlin
```kotlin
data class Card constructor(val rank: Rank, val suit: Suit)

data class KotlinDeckOfCards(
    val cards: List<Card> = Card.getCards().sorted(),
    val cardsBySuit: Map<Suit, List<Card>> = cards.groupBy { card -> card.suit },
    var deck: MutableStack<Card> = Stacks.mutable.empty())

```
* Groovy
```groovy
Card(Rank rank, Suit suit) {
    this.rank = rank
    this.suit = suit
}

GroovyDeckOfCards() {
    this.cards = Card.cards().sort()
    this.cardsBySuit = this.cards.groupBy { it.getSuit() }
    }
```
* Scala
```scala
class Card(val rank: Rank, val suit: Suit)

val cards: Seq[Card] = Card.getCards.toBuffer.sorted
val cardsBySuit: Map[Suit, Seq[Card]] = this.cards.groupBy(_.suit)
var deck: mutable.ArrayStack[Card] = mutable.ArrayStack()

```

Getters and Setters
-------------------
* Java
```java
public ListIterable<Card> getCards() {
    return this.cards;
}

public ListMultimap<Suit, Card> getCardsBySuit() {
    return this.cardsBySuit;
}
```
```java
jdkDeck = JavaDeckOfCards()
jdkDeck.getCards()
jdkDeck.getCardsBySuit()
```
* Kotlin
For data classes accessors need not be mentioned, they are implicitly available

```kotlin
kotlinDeck = KotlinDeckOfCards()
kotlinDeck.cards
kotlinDeck.cardsBySuit
```
* Groovy
```groovy
List<Card> getCards() {
    this.cards
}

Map<Suit, List<Card>> getCardsBySuit() {
    this.cardsBySuit
}
```
```groovy
groovyDeck = new GroovyDeckOfCards()
groovyDeck.getCards()
groovyDeck.getCardsBySuit()

```
* Scala
```scala
def getCards: Seq[Card] = this.cards

def getCardsBySuit: Map[Suit, Seq[Card]] = this.cardsBySuit
```
```scala
scalaDeck = new ScalaDeckOfCards()
scalaDeck.getCards
scalaDeck.getCardsBySuit
```


Enums
-----
* Java
```java
public enum Rank
public enum Suit
```
```java
Sets.cartesianProduct(EnumSet.allOf(Rank.class), EnumSet.allOf(Suit.class), Card::new)
```
* Kotlin
```kotlin
enum class Rank
enum class Suit
```
```kotlin
Rank.values().flatMap { 
    first -> Suit.values().map { 
        second -> Card(first, second)
        }
    }
```
* Groovy
```groovy
enum Rank
enum Suit 
```
```groovy
Set<Rank> ranks = EnumSet.allOf(Rank.class)
Set<Suit> suits = EnumSet.allOf(Suit.class)
ranks.collectMany { rank ->
    suits.collect { suit -> new Card(rank, suit) }
}
```
* Scala
```scala
object Rank extends Enumeration 
object Suit extends Enumeration {
  type Suit = Value
  val SPADES, DIAMONDS, HEARTS, CLUBS = Value
}
```
```scala
Rank.values.flatMap(rank => Suit.values.map(suit => new Card(rank, suit)))
```


Ranges
------
* Java
```java
public List<Set<Card>> dealHands(int hands, int cardsPerHand) {
    return IntInterval.oneTo(hands).collect(i -> this.deal(cardsPerHand), Lists.mutable.empty());
}
```

* Kotlin
```kotlin
fun dealHands(hands: Int, cardsPerHand: Int): List<MutableSet<Card>> {
    return IntRange(1, hands).map { i -> this.deal(cardsPerHand) }
}
```

* Groovy
```groovy
Set<Card> deal(int count) {
    (1..count).collect(new HashSet<>(), { this.deck.pop() }) as Set<Card>
}
```

* Scala
```scala
def deal(count: Int): mutable.HashSet[Card] = {
    val set: mutable.HashSet[Card] = mutable.HashSet()
    1.to(count).foreach(_ => set.add(this.deck.pop()))
    set
}
```


Equals and HashCode
-------------------
* Java
```java
public boolean equals(Object object)
{
    if (this == object)
    {
        return true;
    }
    if (!(object instanceof Card))
    {
        return false;
    }
    Card card = (Card) object;
    return this.isSameRank(card.rank) && this.isSameSuit(card.suit);
}

public int hashCode()
{
    int result = 31 + this.rank.hashCode();
    result = 31 * result + this.suit.hashCode();
    return result;
}
```

* Kotlin
Data classes implicitly get equals() and hashCode() implementation.
```kotlin
override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Card

    if (rank != other.rank) return false
    if (suit != other.suit) return false

    return true
}

override fun hashCode(): Int {
    var result = rank.hashCode()
    result = 31 * result + suit.hashCode()
    return result
}

```

* Groovy
```groovy
boolean equals(Object object) {
    if (this == object) {
        return true
    }
    if (!(object instanceof Card)) {
        return false
    }
    Card card = (Card) object
    this.isSameRank(card.rank) && this.isSameSuit(card.suit)
}

int hashCode() {
    int result = 31 + this.rank.hashCode()
    return 31 * result + this.suit.hashCode()
}

```

* Scala
```scala
def canEqual(other: Any): Boolean = other.isInstanceOf[Card]

override def equals(other: Any): Boolean = other match {
  case that: Card =>
    (that canEqual this) &&
      rank == that.rank &&
      suit == that.suit
  case _ => false
}

override def hashCode(): Int = {
  val state = Seq(rank, suit)
  state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
}
```

Cartesian Product
-----------------
* Java
```java
Sets.cartesianProduct(
    EnumSet.allOf(Rank.class), 
    EnumSet.allOf(Suit.class), 
    Card::new);
```

* Kotlin
```kotlin
Rank.values()
 .flatMap { first ->
     Suit.values().map { second ->
         Card(first, second)
     }
}
```

* Groovy
```groovy
Set<Rank> ranks = EnumSet.allOf(Rank.class)
Set<Suit> suits = EnumSet.allOf(Suit.class)
ranks.collectMany { rank ->
    suits.collect { suit -> new Card(rank, suit) }
}
```

* Scala
```scala
Rank.values
  .flatMap(rank => 
    Suit.values.map(suit => 
      new Card(rank, suit)))
```

Sorting
-------
* Java
```java
MutableList<Card> cards = Card.getCards().toSortedList();
```
```java
@Override
public int compareTo(Card o)
{
    return Comparator.comparing(Card::getSuit)
            .thenComparing(Card::getRank)
            .compare(this, o);
}
```

* Kotlin
```kotlin
cards: List<Card> = Card.getCards().sorted()
```
```kotlin
override fun compareTo(other: Card): Int {
    return Comparator.comparing<Card, Suit>(Card::suit)
            .thenBy(Card::rank)
            .compare(this, other)
}
```

* Groovy
```groovy
List<Card> cards = Card.cards().sort()
```
```groovy
@Sortable(includes = ['suit', 'rank'])
```

* Scala
```scala
cards: Seq[Card] = Card.getCards.toBuffer.sorted
```
```scala
override def compare(that: Card): Int = 
    (this.suit, this.rank) compare (that.suit, that.rank)
```

Group-By
-------
* Java
```java
ListMultimap<Suit, Card> cardsBySuit = this.cards.groupBy(Card::getSuit);
```

* Kotlin
```kotlin
cardsBySuit: Map<Suit, List<Card>> = cards.groupBy { card -> card.suit }
```

* Groovy
```groovy
Map<Suit, List<Card>> cardsBySuit = this.cards.groupBy { it.getSuit() }
```

* Scala
```scala
cardsBySuit: Map[Suit, Seq[Card]] = this.cards.groupBy(_.suit)
```
