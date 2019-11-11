package mrmathami.thegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.text.FontSmoothingType;
import javafx.stage.Stage;

/**
 * Main class. Entry point of the game.
 */
public final class Main extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		final Canvas canvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		final GameController gameController = new GameController(graphicsContext);

		canvas.setFocusTraversable(true);
		graphicsContext.setFontSmoothingType(FontSmoothingType.LCD);


		// keyboard and mouse events to catch. Add if you need more
		canvas.setOnKeyPressed(gameController::keyDownHandler);
		canvas.setOnKeyReleased(gameController::keyUpHandler);
//		canvas.setOnKeyTyped(...);

		canvas.setOnMousePressed(gameController::mouseDownHandler);
		canvas.setOnMouseReleased(gameController::mouseUpHandler);
//		canvas.setOnMouseClicked(...);
//		canvas.setOnMouseMoved(...);


		primaryStage.setResizable(false);
		primaryStage.setTitle(Config.GAME_NAME);
		primaryStage.setOnCloseRequest(gameController::closeRequestHandler);

		// Display background
		graphicsContext.drawImage(new Image("startmenu.png"), 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

		// Create start Button
		Button startButton = new Button("");
		startButton.setBackground(Background.EMPTY);
		startButton.setMinSize(210, 70);

		AnchorPane anchorPane = new AnchorPane(canvas);
		anchorPane.getChildren().add(startButton);
		AnchorPane.setBottomAnchor(startButton, 90.0);
		AnchorPane.setLeftAnchor(startButton, 370.0);

		primaryStage.setScene(new Scene(anchorPane));
		primaryStage.show();

		startButton.setOnMouseClicked(event -> gameController.start());
	}
}