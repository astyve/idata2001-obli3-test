package idatx2001v.obligthree.cardgame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit-tests of the hand of cards class.
 * The following tests are performed:
 * <ul>
 *   <li>Successful creation of an instance with valid suit and face (positive)</li>
 *   <li>Successful handling of creation of an instance with invalid suit or face (negative)</li>
 *   <li>Test five-card-flush - on both a winning hand and a non-winning hand (both test are positive)</li>
 *   <li>Test card of hearts present - on both a winning hand and a non-winning hand</li>
 *   <li>Test Queen of Spades present - on both a winning hand and a non-winning hand</li>
 *   <li>Test sum of faces</li>
 *   <li>Test all 4 on an empty hand (still a positive test)</li>
 * </ul>
 */
class HandOfCardsTest {

  private HandOfCards handOfCardsNoWin;

  @BeforeEach
  void setupHandOfCardNoWin() {
    this.handOfCardsNoWin = new HandOfCards();
    this.handOfCardsNoWin.addCard( new PlayingCard('S', 10));
    this.handOfCardsNoWin.addCard( new PlayingCard('C', 5));
    this.handOfCardsNoWin.addCard( new PlayingCard('D', 11));
    this.handOfCardsNoWin.addCard( new PlayingCard('D', 7));
    this.handOfCardsNoWin.addCard( new PlayingCard('C', 3));
  }

  /**
   * Test creation of a PlayingCard instance with valid input.
   */
  @Test
  void createCardValidInput() {
    PlayingCard playingCard = new PlayingCard('S', 12);
    assertEquals('S', playingCard.getSuit());
    assertEquals(12, playingCard.getFace());
  }

  /**
   * Test creation of PlayingCard instance with invalid suit.
   */
  @Test
  void createCardInvalidSuit() {
    assertThrows(IllegalArgumentException.class, () ->
    {
        PlayingCard playingCard = new PlayingCard('P', 12);
    });
  }

  /**
   * Test creation of PlayingCard instance with invalid face.
   */
  @Test
  void createCardInvalidFace() {
    assertThrows(IllegalArgumentException.class, () -> {
      PlayingCard playingCard = new PlayingCard('H', 0);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      PlayingCard playingCard = new PlayingCard('H', 14);
    });
  }

  /**
   * Test the five-card-flush check on a hand that has a five-card-flush.
   */
  @Test
  void handHasFiveFlush() {
      HandOfCards handOfCards = new HandOfCards();
      handOfCards.addCard(new PlayingCard('S', 5));
      handOfCards.addCard(new PlayingCard('S', 3));
      handOfCards.addCard(new PlayingCard('S', 12));
      handOfCards.addCard(new PlayingCard('S', 10));
      handOfCards.addCard(new PlayingCard('S', 1));
      
      assertTrue( handOfCards.hasFiveCardFlush() );
  }

  /**
   * Test the five-card-flush check on a hand that does not have a five-card-flush.
   */
  @Test
  void handDoesNotHaveFiveFlush() {
     assertFalse( this.handOfCardsNoWin.hasFiveCardFlush() );
  }

  /**
   * Test get all hearts of a hand containing hearts.
   */
  @Test
  void handHasCardsOfHearts() {
    HandOfCards handOfCards = new HandOfCards();
    handOfCards.addCard(new PlayingCard('S', 5));
    handOfCards.addCard(new PlayingCard('H', 3));
    handOfCards.addCard(new PlayingCard('C', 12));
    handOfCards.addCard(new PlayingCard('S', 10));
    handOfCards.addCard(new PlayingCard('H', 1));

    String result = handOfCards.getCardsOfSuitAsString('H');
    assertTrue(result.contains("H1"));
    assertTrue(result.contains("H3"));
  }

  /**
   * Test get all hearts of a hand that does not contain hearts.
   */
  @Test
  void handDoesNotHaveHearts() {
    assertEquals(0, this.handOfCardsNoWin.getCardsOfSuitAsString('H').length());
  }

  /**
   * Test has Queen of spades on a hand where the Queen is present.
   */
  @Test
  void hasQueenOfSpadesQueenPresent() {
    HandOfCards handOfCards = new HandOfCards();
    handOfCards.addCard(new PlayingCard('S',12));
    handOfCards.addCard(new PlayingCard('C',13));
    handOfCards.addCard(new PlayingCard('S',2));

    assertTrue(handOfCards.hasQueenOfSpades());
  }

  /**
   * Test has Queen of spades on a hand where the Queen is not present.
   */
  @Test
  void hasQueenOfSpadesQueenNotPresent() {
    HandOfCards handOfCards = new HandOfCards();
    handOfCards.addCard(new PlayingCard('D',12));
    handOfCards.addCard(new PlayingCard('C',13));
    handOfCards.addCard(new PlayingCard('S',2));

    assertFalse(handOfCards.hasQueenOfSpades());
  }

  /**
   * Test get sum of faces.
   */
  @Test
  void testSumOfFaces() {
    HandOfCards handOfCards = new HandOfCards();
    handOfCards.addCard(new PlayingCard('S', 5));
    handOfCards.addCard(new PlayingCard('H', 3));
    handOfCards.addCard(new PlayingCard('C', 12));
    handOfCards.addCard(new PlayingCard('S', 10));
    handOfCards.addCard(new PlayingCard('H', 1));

    assertEquals(31,handOfCards.getSumOfFaces());
  }

  /**
   * Test all 4 checks on an empty hand.
   */
  @Test
  void performChecksOnEmptyHand() {
    HandOfCards handOfCards = new HandOfCards();

    assertFalse(handOfCards.hasQueenOfSpades());
    assertFalse(handOfCards.hasFiveCardFlush());
    assertEquals(0, handOfCards.getCardsOfSuitAsString('H').length());
    assertEquals(0, handOfCards.getSumOfFaces());
  }
}
