package idatx2001v.obligthree.cardgame.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A Card Game application. The application lets the player draw 5 or more playing cards.
 * Based on the cards on hand, the player can:
 * <ul>
 *   <li>Find the sum of all cards (the values)</li>
 *   <li>Identify all the cards of Hearts</li>
 *   <li>Check for the presence of Queen of Spades</li>
 *   <li>Check if the hand has a five-card-flush (five cards with same suit)</li>
 * </ul>
 */
public class CardGameApp extends Application {

  /**
   * Main starting point for the application.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    try {
      // The standard way to load an FXML-file and initialize JavaFX, is by:
      //
      //  Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
      //
      // The problem with this method, is that you are not able to get access to
      // the Controller instance, if you need to share som common objects between multiple
      // parts of your GUI (like sharing a common model/business logic.
      // An alternative way, is as follows:
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
      Parent root = fxmlLoader.load();
      // NB! You MUST load the FXML-file BEFORE you ask for the controller.
      MainWindowController mainWindowController = fxmlLoader.getController();
      // Now you have access to the controller instance, and can transfer objects to the controller
      // through method calls to the controller. (This is not demonstrated here)
      Scene scene = new Scene(root, 700, 400);
      primaryStage.setTitle("A simple card game");
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (Exception e) {
      System.out.println("ERROR: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
