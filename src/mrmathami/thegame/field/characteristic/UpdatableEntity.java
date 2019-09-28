package mrmathami.thegame.field.characteristic;

import mrmathami.thegame.field.GameField;
import mrmathami.thegame.field.GameEntity;

import javax.annotation.Nonnull;

/**
 * Marker interface.
 * Mark updatable objects.
 */
public interface UpdatableEntity extends GameEntity {
	void doUpdate(@Nonnull GameField field, int tickCount);
}
