package mrmathami.thegame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
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
	private final String [] listMap = {"/stage/level1.txt", "/stage/level2.txt", "/stage/level3.txt", "/stage/level4.txt", "/stage/level5.txt"};
	private static int level = 0;

	/**
	 * Display game stage
	 * @param actionEvent
	 * @param map link to file.txt which contain game stage
	 * @throws IOException
	 */
	private void newGame(ActionEvent actionEvent, String map) throws IOException {
		try {
			GameMusic.stopAllMusic();
			GameMusic.playClickMusic();
			GameMusic.playInGameMusic();
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
	    GameMusic.playClickMusic();
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
		GameMusic.stopAllMusic();
		GameMusic.playLoseMusic();
		AnchorPane gameOver = FXMLLoader.load(Main.class.getResource("GameOver.fxml"));
		pane.getChildren().add(gameOver);
	}
	static void onWin(AnchorPane pane) throws IOException {
		GameMusic.stopAllMusic();
		GameMusic.playWinMusic();
        AnchorPane victory = FXMLLoader.load(Main.class.getResource("Win.fxml"));
        pane.getChildren().add(victory);
    }

	/**
	 * The methods below handle some event in game
	 */
	public void setSFX(ActionEvent actionEvent) {
		GameMusic.playClickMusic();
		Button button = (Button) actionEvent.getSource();
		if (GameMusic.SFXOn) {
			button.setText("SFX: OFF");
			GameMusic.SFXOn = false;
		}
		else {
			button.setText("SFX: ON");
			GameMusic.SFXOn = true;
		}
	}
	public void setMute(ActionEvent actionEvent) {
		GameMusic.playClickMusic();
		Button button = (Button) actionEvent.getSource();

		if (GameMusic.isAllMute()) {
			button.setText("Mute: OFF");
			GameMusic.setAllMute(false);
		}
		else {
			button.setText("Mute: ON");
			GameMusic.setAllMute(true);
		}
	}
	public void backToMenu(ActionEvent actionEvent) {
		GameMusic.playClickMusic();
		start((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
	}
	public void nextLevel(ActionEvent actionEvent) throws IOException {
		newGame(actionEvent, listMap[++level]);
	}
	public void retry(ActionEvent actionEvent) throws IOException {
		newGame(actionEvent, listMap[level]);
	}
	public void pause() throws IOException {
	    GameMusic.playClickMusic();
	    gameController.pause();
	    AnchorPane pane = gameController.getPane();
        AnchorPane gamePause = FXMLLoader.load(Main.class.getResource("Pause.fxml"));
        pane.getChildren().add(gamePause);
	}
	public void continue_() {
		GameMusic.playClickMusic();
		List list = gameController.getPane().getChildren();
		list.remove(list.size() - 1);
		gameController.continue_();
	}

	/**
	 * quit game, close window
	 */
	public void quit() {
	    GameMusic.playClickMusic();
		Platform.exit();
		System.exit(0);
	}

	/**
	 * Buy tower
	 * @param mouseEvent
	 * @param towerName
	 */
	private void buyTower(MouseEvent mouseEvent, String towerName) {
		System.out.println("Start buying tower");
		Circle currentTower = (Circle) mouseEvent.getSource();
		Circle copyTower = new Circle(currentTower.getRadius());
		copyTower.setFill(currentTower.getFill());
		gameController.getPane().getChildren().add(copyTower);

		currentTower.setOnMouseDragged(mouseEvent1 -> {
			System.out.println("Choosing location");
			copyTower.setCenterX(mouseEvent1.getSceneX());
			copyTower.setCenterY(mouseEvent1.getSceneY());
		});
		currentTower.setOnMouseReleased(mouseevent2 -> {
			gameController.getPane().getChildren().remove(copyTower);
			gameController.buyTower(mouseevent2, towerName);
		});
	}
	public void buyNormalTower(MouseEvent mouseEvent) {buyTower(mouseEvent, "normal tower");}
	public void buyMachineGunTower(MouseEvent mouseEvent) {buyTower(mouseEvent, "machine gun tower");}
	public void buySniperTower(MouseEvent mouseEvent) {buyTower(mouseEvent, "sniper tower");}
	public void buySuperTower(MouseEvent mouseEvent) {buyTower(mouseEvent, "super tower");}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setResizable(true);
		primaryStage.setTitle(Config.GAME_NAME);

		try {
			AnchorPane root = FXMLLoader.load(this.getClass().getResource("StartMenu.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			GameMusic.stopAllMusic();
			GameMusic.playStartMusic();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


