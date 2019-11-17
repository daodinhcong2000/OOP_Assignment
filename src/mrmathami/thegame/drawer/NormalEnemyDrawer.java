package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.enemy.NormalEnemy;
import mrmathami.thegame.entity.tile.tower.AbstractTower;

import javax.annotation.Nonnull;

public final class NormalEnemyDrawer implements EntityDrawer {
	private static long i = 0;
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
		graphicsContext.setFill(Color.DARKMAGENTA);
		graphicsContext.setFont(Font.font(15));
		graphicsContext.fillText(String.format(" %d", ((NormalEnemy) entity).getHealth()), screenPosX, screenPosY);
		graphicsContext.drawImage(new Image("normalEnemy (" + String.format("%d", i++ % 8 + 1) + ").png"), screenPosX, screenPosY, screenWidth * 1.2, screenHeight * 1.2);
		//graphicsContext.fillRoundRect(screenPosX, screenPosY, screenWidth, screenHeight, 4, 4);
	}
}
