package mrmathami.thegame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.FontSmoothingType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


/**
 * Main class. Entry point of the game.
 */
public final class Main extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	private static GameController gameController;


	public void newGame(ActionEvent actionEvent) throws IOException {
		try {
			Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

			Canvas canvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
			GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
			gameController = new GameController(graphicsContext);
			canvas.setFocusTraversable(true);
			graphicsContext.setFontSmoothingType(FontSmoothingType.LCD);

			AnchorPane root = new AnchorPane(canvas);
			AnchorPane menuInGame = FXMLLoader.load(this.getClass().getResource("MenuInGame.fxml"));
			root.getChildren().add(menuInGame);
			gameController.start();

			stage.setScene(new Scene(root));
			stage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void onGameOver(AnchorPane pane) throws IOException {
		AnchorPane gameOver = FXMLLoader.load(Main.class.getResource("GameOver.fxml"));
		pane.getChildren().add(gameOver);
	}
	static void onVictory(AnchorPane pane) throws IOException {
        AnchorPane victory = FXMLLoader.load(Main.class.getResource("Win.fxml"));
        pane.getChildren().add(victory);
    }

	public void backToMenu(ActionEvent actionEvent) { start((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()); }
	public void nextLevel(ActionEvent actionEvent) {}
	public void retry(ActionEvent actionEvent) throws IOException {
		newGame(actionEvent);
	}
	public void pause(ActionEvent actionEvent) throws IOException {
	    gameController.pause();
	    AnchorPane pane = gameController.getPane();
        AnchorPane gamePause = FXMLLoader.load(Main.class.getResource("Pause.fxml"));
        pane.getChildren().add(gamePause);
	}
	public void continue_(ActionEvent actionEvent) throws IOException {
		List list = gameController.getPane().getChildren();
		list.remove(list.size() - 1);
		gameController.continue_();
	}
	public void quit() {
		Platform.exit();
		System.exit(0);
	}


	public void buyTower(DragEvent dragEvent) { gameController.buyTower(dragEvent); }


	@Override
	public void start(Stage primaryStage) {

		primaryStage.setResizable(true);
		primaryStage.setTitle(Config.GAME_NAME);

		try {
			AnchorPane root = FXMLLoader.load(this.getClass().getResource("StartMenu.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


