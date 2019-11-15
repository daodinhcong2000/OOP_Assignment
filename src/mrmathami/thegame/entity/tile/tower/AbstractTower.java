package mrmathami.thegame.entity.tile.tower;

import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.UpdatableEntity;
import mrmathami.thegame.entity.bullet.AbstractBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.tile.AbstractTile;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class AbstractTower<E extends AbstractBullet> extends AbstractTile implements UpdatableEntity {
	private final double range;
	private final long speed;
	private final int cost;

	private long tickDown;

	protected AbstractTower(long createdTick, long posX, long posY, double range, long speed, int cost) {
		super(createdTick, posX, posY, 1L, 1L);
		this.range = range;
		this.speed = speed;
		this.cost = cost;
		this.tickDown = 0;
	}

	public int getCost() {
		return cost;
	}

	@Override
	public final void onUpdate(@Nonnull GameField field) {
		this.tickDown -= 1;
		if (tickDown <= 0) {
			// TODO: Find a target and spawn a bullet to that direction.(done !!)
			// Use GameEntities.getFilteredOverlappedEntities to find target in range
			// Remember to set this.tickDown back to this.speed after shooting something.
			// this.tickDown = speed;

			List<AbstractEnemy> enemyList = (List<AbstractEnemy>) GameEntities.getFilteredOverlappedEntities(field.getEntities(), AbstractEnemy.class, getPosX() - range, getPosY() - range, 2 * range, 2 * range);
			if (enemyList.size() > 0) {
				AbstractEnemy firstEnemy = enemyList.remove(0);
				double x = getPosX() + getWidth() / 2;
				double y = getPosY() + getHeight() / 2;
				double deltaX = (firstEnemy.getPosX() + firstEnemy.getWidth() / 2) - x;
				double deltaY = (firstEnemy.getPosY() + firstEnemy.getHeight() / 2) - y;
				field.doSpawn(this.doSpawn(0, x, y, deltaX , deltaY));
				this.tickDown = speed;
			}
		}
	}

	/**
	 * Create a new bullet. Each tower spawn different type of bullet.
	 * Override this method and return the type of bullet that your tower shot out.
	 * See NormalTower for example.
	 *
	 * @param createdTick createdTick
	 * @param posX posX
	 * @param posY posY
	 * @param deltaX deltaX
	 * @param deltaY deltaY
	 * @return the bullet entity
	 */
	@Nonnull
	protected abstract E doSpawn(long createdTick, double posX, double posY, double deltaX, double deltaY);
}
