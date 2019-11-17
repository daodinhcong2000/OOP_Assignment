package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import mrmathami.thegame.entity.GameEntity;

import javax.annotation.Nonnull;

public class SuperTowerDrawer implements EntityDrawer {
    private static long i = 0;
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
//      graphicsContext.setFill(Color.YELLOW);
//      graphicsContext.fillOval(screenPosX, screenPosY, screenWidth, screenHeight);
        graphicsContext.drawImage(new Image("superTower (" + String.format("%d", i++ % 8 + 1) + ").png"), screenPosX, screenPosY, screenWidth * 1, screenHeight);
    }
}
