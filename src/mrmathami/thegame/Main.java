package mrmathami.thegame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.FontSmoothingType;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Main class. Entry point of the game.
 */
public final class Main extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	public void newGame(MouseEvent mouseEvent) throws IOException {
		try {
			Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene() .getWindow();
			AnchorPane pane = FXMLLoader.load(this.getClass().getResource("GameScreen.fxml"));
			stage.setScene(new Scene(pane));
			stage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	Canvas canvas;
	static boolean isStarted = true;
	static boolean isPause = false;
	static GameController gameController;

	public void startGame() throws IOException {
		if (isStarted) {
			isStarted = !isStarted;
			GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
			gameController = new GameController(graphicsContext);
			canvas.setFocusTraversable(true);

			graphicsContext.setFontSmoothingType(FontSmoothingType.LCD);
			gameController.start();
		}
	}

	public void pause(){
		if (isPause) {
			gameController.continue_();
		} else {
			gameController.stop();
		}
		isPause = !isPause;
	}

	public void buyTower(MouseEvent mouseEvent) {

	}
	@Override
	public void start(Stage primaryStage) {
//		final Canvas canvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
//		final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
//		final GameController gameController = new GameController(graphicsContext);
//
//		canvas.setFocusTraversable(true);
//		graphicsContext.setFontSmoothingType(FontSmoothingType.LCD);
//
//
//		// keyboard and mouse events to catch. Add if you need more
//		canvas.setOnKeyPressed(gameController::keyDownHandler);
//		canvas.setOnKeyReleased(gameController::keyUpHandler);
////		canvas.setOnKeyTyped(...);
//
//		canvas.setOnMousePressed(gameController::mouseDownHandler);
//		canvas.setOnMouseReleased(gameController::mouseUpHandler);
////		canvas.setOnMouseClicked(...);
////		canvas.setOnMouseMoved(...);
//
//
		primaryStage.setResizable(true);
		primaryStage.setTitle(Config.GAME_NAME);
//		primaryStage.setOnCloseRequest(gameController::closeRequestHandler);
//
//		// Display Start menu
//		graphicsContext.drawImage(new Image("startmenu.png"), 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
//
//		// Create start Button
//		Button startButton = new Button("");
//		startButton.setBackground(Background.EMPTY);
//		startButton.setMinSize(210, 70);
//
//		AnchorPane anchorPane = new AnchorPane(canvas);
//		anchorPane.getChildren().add(startButton);
//		AnchorPane.setBottomAnchor(startButton, 90.0);
//		AnchorPane.setLeftAnchor(startButton, 370.0);
//
//		primaryStage.setScene(new Scene(anchorPane));
//		primaryStage.show();
//
//		startButton.setOnMouseClicked(event -> gameController.start());


		try {
			AnchorPane root = FXMLLoader.load(this.getClass().getResource("StartMenu.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


