package idatx2001v.obligthree.cardgame.ui;

import idatx2001v.obligthree.cardgame.DeckOfCards;
import idatx2001v.obligthree.cardgame.HandOfCards;
import idatx2001v.obligthree.cardgame.PlayingCard;
import java.util.HashMap;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;

/**
 * The Controller-class of the main window. Linked to the FXML-file representing
 * the view in the MVC of JavaFX FXML.
 */
public class MainWindowController {
  // Non-JavaFX/FXML-fields
  private DeckOfCards deckOfCards;
  private HandOfCards handOfCards;
  private HashMap<String, ImageView> cardFaceImages;
  private boolean performCheckOnDeal = false;

  // Fields mapped to the FXML-file.
  @FXML
  private CheckBox autoCheckCheckBox;
  @FXML
  private Button checkButton;
  @FXML
  private Spinner<Integer> numOfCardsSpinner;
  @FXML
  private TextField sumOfFacesField;
  @FXML
  private TextField queenOfSpacedField;
  @FXML
  private TextField cardsOfHeartsField;
  @FXML
  private TextField fiveCardFlushField;
  @FXML
  private FlowPane cardViewPane;

  /**
   * Initialize the controller. Avoid using a constructor for controller classes,
   * since the FXML-loader decides the order of initialization. To be sure that
   * all the GUI components have been instantiated before the controller is initialized,
   * all initialization should be done in this method instead of in a constructor.
   */
  @FXML
  public void initialize() {
    this.deckOfCards = new DeckOfCards();
    this.cardFaceImages = new HashMap<>();
    this.loadCardFaceImages();
    this.checkButton.setDisable(true);
  }

  /**
   * Deals a hand of cards upon pressing the "Deal"-button.
   *
   * @param actionEvent the event sent from the Deal-button.
   */
  public void dealHandAction(ActionEvent actionEvent) {
    int numberOfCards = this.numOfCardsSpinner.getValue();
    this.deckOfCards.reset();
    this.handOfCards = new HandOfCards();
    this.handOfCards.addAllCards(this.deckOfCards.dealHand(numberOfCards));
    this.displayHandOfCards();
    if (this.performCheckOnDeal) {
      // Perform check directly
      this.checkWinningsAction(null);
    } else {
      this.checkButton.setDisable(false);
    }
  }

  /**
   * Checks the hand against the criteria defined in the assignment.
   *
   * @param actionEvent the event sent by the Check-button.
   */
  public void checkWinningsAction(ActionEvent actionEvent) {
    // Get the sum of all faces
    this.sumOfFacesField.setText(
        String.valueOf(this.handOfCards.getSumOfFaces()));
    // Get all the cards of hearts
    this.cardsOfHeartsField.setText(this.handOfCards.getCardsOfSuitAsString('H'));
    // Check if hand holds Queen of Spades
    if (this.handOfCards.hasQueenOfSpades()) {
      this.queenOfSpacedField.setText("YES :-)");
    } else {
      this.queenOfSpacedField.setText("NO :-(");
    }
    // Check if hand holds a five-card-flush
    if (this.handOfCards.hasFiveCardFlush()) {
      this.fiveCardFlushField.setText("YES :-)");
    } else {
      this.fiveCardFlushField.setText("NO :-(");
    }
  }

  /**
   * Displays the hand of cards on the GUI.
   */
  private void displayHandOfCards() {
    this.cardViewPane.getChildren().clear();
    for (PlayingCard card : this.handOfCards) {
      ImageView cardView = this.cardFaceImages.get(card.getAsString());
      this.addFadeTransition(cardView);
      this.cardViewPane.getChildren().add(cardView);
    }
  }

  /**
   * Adds a fade-transition to the node provided as a parameter.
   * The fade-transition is set up to "fade in" the given node
   * over a timespan of 1000ms (1s).
   * The code here was copied from a tutorial in Internet:
   * <a href=https://www.javatpoint.com/javafx-fade-transition>
   *   https://www.javatpoint.com/javafx-fade-transition</a>
   *
   * @param cardView the Image-node to apply the fade transition to.
   */
  private void addFadeTransition(ImageView cardView) {
    //Instantiating FadeTransition class
    FadeTransition fade = new FadeTransition();

    //setting the duration for the Fade transition
    fade.setDuration(Duration.millis(1000));

    //setting the initial and the target opacity value for the transition
    fade.setFromValue(0);
    fade.setToValue(1);

    //setting cycle count for the Fade transition
    fade.setCycleCount(1);

    //the transition will set to be auto reversed by setting this to true
    fade.setAutoReverse(false);

    //setting ImageView as the node onto which the transition will be applied
    fade.setNode(cardView);

    //playing the transition
    fade.play();
  }


  /**
   * Creates the images representing a playing card for all the 52 cards.
   * The images are collected from one PNG-file holding all 52 cards.
   * Also, the 52 images are stored in an HashMap using the string-representation
   * of a PlayingCard (returned by the method getAsString()) as a key. Alternatively,
   * the individual images could have been stored in the PlayingCard-class itself,
   * but that would really mix user interface specifics (the image) with the
   * underlying business logic.
   */
  private void loadCardFaceImages() {
    char[] suits = {'H', 'D', 'C', 'S'};

    Image image = new Image(getClass().getResource("/images/playingcards.png").toExternalForm());

    double imageSizeW = image.getWidth();
    double imageSizeH = image.getHeight();
    double cardSizeW = imageSizeW / 13;
    double cardSizeH = imageSizeH / 4;

    for (int suitIdx = 0; suitIdx < suits.length; suitIdx++) {
      for (int valueIdx = 0; valueIdx < 52; valueIdx++) {

        // Define a 2D Rectangle identifying a speci
        Rectangle2D viewport = new Rectangle2D(cardSizeW * valueIdx,
            cardSizeH * suitIdx,
            cardSizeW, cardSizeH);

        ImageView imageView = new ImageView(image);
        imageView.setViewport(viewport);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        // Add the image of the card to the HashMap
        String cardKey = String.valueOf(suits[suitIdx]) + (valueIdx + 1);
        this.cardFaceImages.put(cardKey, imageView);

      }
    }

  }

  /**
   * Enables/disables auto checking. Auto checking enabled will trigger automatic
   * check of the hand of cards after the hand has been dealt.
   *
   * @param actionEvent the event causing the action.
   */
  public void setAutoCheck(ActionEvent actionEvent) {
    if (this.autoCheckCheckBox.isSelected()) {
      this.performCheckOnDeal = true;
    } else {
      this.performCheckOnDeal = false;
    }
    this.checkButton.setDisable(this.performCheckOnDeal);
  }
}
