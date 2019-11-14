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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Main class. Entry point of the game.
 */
public final class Main extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	private static GameController gameController;
	private final String [] listMap = {"/stage/level0.txt", "/stage/level1.txt", "/stage/level2.txt", "/stage/level3.txt", "/stage/level4.txt"};
	private static int level = 0;

	/**
	 * Display game stage
	 * @param actionEvent
	 * @param map link to file.txt which contain game stage
	 * @throws IOException
	 */
	public void newGame(ActionEvent actionEvent, String map) throws IOException {
		try {
			Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

			Canvas canvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
			GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
			gameController = new GameController(graphicsContext, map);
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

	/**
	 * Display Select level scene
	 * @param actionEvent
	 * @throws IOException
	 */
	public void selectLevel(ActionEvent actionEvent) throws IOException {
		Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		AnchorPane selectLevel = FXMLLoader.load(this.getClass().getResource("SelectLevel.fxml"));
		stage.setScene(new Scene(selectLevel));
		stage.show();
	}

	/**
	 * Display game stage with corresponding level
	 * @param actionEvent
	 * @throws IOException
	 */
	public void loadLevel1(ActionEvent actionEvent) throws IOException {
		level = 0;
		newGame(actionEvent, listMap[0]);
	}
	public void loadLevel2(ActionEvent actionEvent) throws IOException {
		level = 1;
		newGame(actionEvent, listMap[1]);
	}
	public void loadLevel3(ActionEvent actionEvent) throws IOException {
		level = 2;
		newGame(actionEvent, listMap[2]);
	}
	public void loadLevel4(ActionEvent actionEvent) throws IOException {
		level = 3;
		newGame(actionEvent, listMap[3]);
	}
	public void loadLevel5(ActionEvent actionEvent) throws IOException {
		level = 4;
		newGame(actionEvent, listMap[4]);
	}

	/**
	 * Display game over/win scene
	 * @param pane current pane, game over/pane will cover it
	 * @throws IOException
	 */
	static void onGameOver(AnchorPane pane) throws IOException {
		AnchorPane gameOver = FXMLLoader.load(Main.class.getResource("GameOver.fxml"));
		pane.getChildren().add(gameOver);
	}
	static void onWin(AnchorPane pane) throws IOException {
        AnchorPane victory = FXMLLoader.load(Main.class.getResource("Win.fxml"));
        pane.getChildren().add(victory);
    }

	/**
	 * handle some event in game
	 * @param actionEvent
	 */
	public void backToMenu(ActionEvent actionEvent) {
		start((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
	}
	public void nextLevel(ActionEvent actionEvent) throws IOException {
		newGame(actionEvent, listMap[++level]);
	}
	public void retry(ActionEvent actionEvent) throws IOException {
		newGame(actionEvent, listMap[level]);
	}
	public void pause() throws IOException {
	    gameController.pause();
	    AnchorPane pane = gameController.getPane();
        AnchorPane gamePause = FXMLLoader.load(Main.class.getResource("Pause.fxml"));
        pane.getChildren().add(gamePause);
	}
	public void continue_() throws IOException {
		List list = gameController.getPane().getChildren();
		list.remove(list.size() - 1);
		gameController.continue_();
	}

	/**
	 * quit game, close window
	 */
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


