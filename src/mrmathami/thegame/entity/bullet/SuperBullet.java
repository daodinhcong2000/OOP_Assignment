package mrmathami.thegame.entity.bullet;

import mrmathami.thegame.Config;

public class SuperBullet extends AbstractBullet {

    public SuperBullet(long createdTick, double posX, double posY, double deltaX, double deltaY) {
        super(createdTick, posX, posY, deltaX, deltaY, Config.SUPER_BULLET_SPEED, Config.SUPER_BULLET_STRENGTH, Config.SUPER_BULLET_TTL);
    }
}
