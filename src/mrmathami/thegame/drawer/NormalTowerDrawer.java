package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mrmathami.thegame.entity.GameEntity;

import javax.annotation.Nonnull;

public final class NormalTowerDrawer implements EntityDrawer {
	private static long i = 0;
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
//		graphicsContext.setFill(Color.RED);
//		graphicsContext.fillOval(screenPosX, screenPosY, screenWidth, screenHeight);
		graphicsContext.drawImage(new Image("normalTower (" + String.format("%d", i++ % 8 + 1) + ").png"), screenPosX, screenPosY, screenWidth, screenHeight);
	}
}
