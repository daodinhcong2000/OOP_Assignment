package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.enemy.NormalEnemy;
import mrmathami.thegame.entity.enemy.TankerEnemy;

import javax.annotation.Nonnull;

public final class TankerEnemyDrawer implements EntityDrawer {
	private static long i = 0;
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
		graphicsContext.setFill(Color.VIOLET);
		graphicsContext.setFont(Font.font(15));
		graphicsContext.fillText(String.format(" %d", ((TankerEnemy) entity).getHealth()), screenPosX, screenPosY);
		graphicsContext.drawImage(new Image("tankerEnemy (" + String.format("%d", i++ % 6 + 1) + ").png"), screenPosX, screenPosY, screenWidth * 1.2, screenHeight * 1.2);
	}
}
