package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.enemy.NormalEnemy;
import mrmathami.thegame.entity.enemy.TankerEnemy;

import javax.annotation.Nonnull;

public final class TankerEnemyDrawer implements EntityDrawer {
	private static long i = 0;
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
		graphicsContext.drawImage(new Image("tankerEnemy (" + String.format("%d", i++ % 6 + 1) + ").png"), screenPosX, screenPosY, screenWidth * 1.2, screenHeight * 1.2);

		graphicsContext.setStroke(Color.BLACK);
		graphicsContext.setLineWidth(1);
		graphicsContext.strokeRect(screenPosX, screenPosY - 6, screenWidth * 1.2, 5);

		double hpRate = (double) ((AbstractEnemy) entity).getHealth() / Config.TANKER_ENEMY_HEALTH;
		if (hpRate >= 0.7) graphicsContext.setFill(Color.GREEN);
		else
			if (hpRate >= 0.3) graphicsContext.setFill(Color.DODGERBLUE);
		else
			graphicsContext.setFill(Color.RED);
		graphicsContext.fillRect(screenPosX, screenPosY - 6, screenWidth * hpRate * 1.2, 5);
	}
}
