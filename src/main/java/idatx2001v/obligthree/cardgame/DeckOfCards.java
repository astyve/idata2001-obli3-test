package idatx2001v.obligthree.cardgame;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * A deck of cards contains 52 playing cards. The class has a method
 * returning a string representation of the deck, and a method
 * for dealing cards from the deck.
 *
 * @author ntnu
 * @version 2021-03-13
 */
public class DeckOfCards implements Iterable<PlayingCard> {
  private ArrayList<PlayingCard> cards;
  private Random randomGen;

  private static final Logger LOGGER = Logger.getLogger(DeckOfCards.class.getName());

  /**
   * Creates an instance of DeckOfCards holding 52 playing cards.
   */
  public DeckOfCards() {
    this.cards = new ArrayList<>();
    try {
      this.randomGen = SecureRandom.getInstanceStrong();
    } catch (NoSuchAlgorithmException e) {
      LOGGER.severe(e.getMessage());
    }
    this.fillDeckWithCards();
  }

  /**
   * Fills the deck with 52 playing cards.
   */
  private void fillDeckWithCards() {
    char[] suits = {'H', 'D', 'C', 'S'};
    for (int suit = 0; suit < 4; suit++) {
      for (int face = 1; face < 14; face++) {
        this.cards.add(new PlayingCard(suits[suit], face));
      }
    }
  }

  /**
   * Resets the deck, by clearing all cards left, and filling the deck again.
   */
  public void reset() {
    this.cards.clear();
    this.fillDeckWithCards();
  }

  /**
   * Returns the deck of cards as a string on the form "H1 H2 H3 C11" etc.
   *
   * @return the deck of cards as a string
   */
  public String getAsString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Deck:");
    for (int i = 0; i < 52; i++) {
      sb.append(" ");
      sb.append(cards.get(i).getAsString());
    }
    return sb.toString();
  }

  /**
   * Deals a hand of cards by picking cards by random from the deck.
   *
   * @param numberOfCards the number of cards to deal.
   * @return the collection of playing cards making up a hand
   */
  public List<PlayingCard> dealHand(int numberOfCards) {
    List<PlayingCard> cardsOnHand = new ArrayList<>();
    for (int i = 0; i < numberOfCards; i++) {
      // Pull a random card from the deck and put it in the hand.
      PlayingCard card = this.cards.remove(this.randomGen.nextInt(this.cards.size()));
      cardsOnHand.add(card);
    }
    return cardsOnHand;
  }

  @Override
  public Iterator<PlayingCard> iterator() {
    return this.cards.iterator();
  }
}
