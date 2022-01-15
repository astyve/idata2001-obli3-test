package idatx2001v.obligthree.cardgame;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a hand of playing cards.
 * This class also contains methods to check for the following:
 * <ul>
 *   <li>Sum of all faces</li>
 *   <li>Queen of Spades present</li>
 *   <li>All the cwards of Hearts</li>
 *   <li>A Five-card-Flush (minimum 5 cards of the same suit)</li>
 * </ul>
 */
public class HandOfCards implements Iterable<PlayingCard> {
  private HashSet<PlayingCard> cards;

  /**
   * Creates an instance of HandOfCards.
   */
  public HandOfCards() {
    this.cards = new HashSet<>();
  }

  /**
   * Returns the number of cards on hand.
   *
   * @return the number of cards on hand.
   */
  public int getNumberOfCards() {
    return this.cards.size();
  }

  /**
   * Add a playing card to the hand.
   *
   * @param card the playing card to be added
   */
  public void addCard(PlayingCard card) {
    if (card == null) {
      throw new IllegalArgumentException("Card cannot be null");
    }
    this.cards.add(card);
  }

  /**
   * Add all the cards from the provided List of cards to the hand.
   *
   * @param allCards the collection of cards to add to the hand
   */
  public void addAllCards(List<PlayingCard> allCards) {
    if (allCards == null) {
      throw new IllegalArgumentException("allCards cannot be null");
    }
    this.cards.addAll(allCards);
  }

  /**
   * Empty the hand of cards.
   */
  public void clearHand() {
    this.cards.clear();
  }

  /**
   * Calculates the sum of the faces of all the cards on hand.
   *
   * @return the sum of the faces of all the cards on hand.
   */
  public int getSumOfFaces() {
    return this.cards
        .stream() // Get the stream of the collection
        .map(PlayingCard::getFace) // Map from stream of PlayingCard to stream of Integer
        .reduce(0, (subtotal, face) -> subtotal + face); // Sum the Integers
  }

  /**
   * Returns all the cards of the given suit on hand, as a String.
   *
   * @param suit the suit of the cards to return
   * @return all the cards of the given suit on hand s a String on the format "H1 C11"
   */
  public String getCardsOfSuitAsString(char suit) {
    return this.cards
        .stream()
        .filter(card -> card.getSuit() == suit)
        .map(PlayingCard::getAsString)
        .reduce("", (returnString, card) -> returnString + " " + card).strip();
  }

  /**
   * Checks the hand of cards for the Queen of spades. If the hand holds the
   * Queen of spades, {@code true} is returned. If not, {@code false} is returned.
   *
   * @return {@code true} if the hand holds Queen of spades. {@code false} if not.
   */
  public boolean hasQueenOfSpades() {
    return this.cards
        .stream()
        .anyMatch(card -> card.getAsString().equals("S12"));
  }

  /**
   * Checks the hand of cards for a five-card-flush.
   * If the hand holds a five-card-flush (minimum 5 cards of the same suit),
   * {@code true} is returned. If not, {@code false} is returned.
   *
   * @return {@code true} if the hand holds a five-card-flush. {@code false} if not.
   */
  public boolean hasFiveCardFlush() {
    boolean result = false;

    long largestNumberOfSameSuit = cards
        .stream()
        // Først, hent ut bare fargene fra kortene (Stream<Character>)
        .map((PlayingCard card) -> card.getSuit())
        // Lag så en HashMap der farge er nøkkel, og antallet av fargen er verdi.
        // Det er metoden groupingBy() som er metoden som lager en Map<Character, Long> av en Stream
        .collect(Collectors.groupingBy((Character c) -> c.charValue(), Collectors.counting()))
        // Henter vi ut values-delen av HashMap'en nå, så får vi 4 tall, der hver av tallene
        // er antallet av hver farge du har på hånd
        .values()
        // Gjør om samlingen til en Stream
        .stream()
        // Finn ut hvilke av de 4 tallene som er størst ved å bruke funksjonen max()
        // i matematikk biblioteket Math. max() returnerer det største tallet
        .reduce(0L,(Long largestNumberSoFar, Long nextNumber)
            -> Math.max(largestNumberSoFar, nextNumber));

    if (largestNumberOfSameSuit >= 5) {
      result = true;
    } else {
      result = false;
    }
    return result;
  }

  @Override
  public Iterator<PlayingCard> iterator() {
    return this.cards.iterator();
  }
}
