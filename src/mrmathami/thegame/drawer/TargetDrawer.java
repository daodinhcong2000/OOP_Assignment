package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import mrmathami.thegame.entity.GameEntity;

import javax.annotation.Nonnull;

public final class TargetDrawer implements EntityDrawer {
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
//		graphicsContext.setStroke(Color.DARKRED);
//		graphicsContext.setLineWidth(4);
//		graphicsContext.strokeRect(screenPosX, screenPosY, screenWidth, screenHeight);
		graphicsContext.drawImage(new Image("spawner.png"), screenPosX, screenPosY, screenWidth, screenHeight);
	}
}
