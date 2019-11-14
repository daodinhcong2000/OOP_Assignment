package mrmathami.thegame.entity.tile.tower;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.bullet.SuperBullet;

import javax.annotation.Nonnull;

public class SuperTower extends AbstractTower<SuperBullet> {
    public SuperTower(long createdTick, long posX, long posY) {
        super(createdTick, posX, posY, Config.SUPER_TOWER_RANGE, Config.SUPER_TOWER_SPEED);
    }

    @Nonnull
    @Override
    protected final SuperBullet doSpawn(long createdTick, double posX, double posY, double deltaX, double deltaY) {
        return new SuperBullet(createdTick, posX, posY, deltaX, deltaY);
    }
}
