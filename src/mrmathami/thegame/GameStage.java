package mrmathami.thegame;

import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.bullet.MachineGunBullet;
import mrmathami.thegame.entity.bullet.NormalBullet;
import mrmathami.thegame.entity.bullet.SniperBullet;
import mrmathami.thegame.entity.bullet.SuperBullet;
import mrmathami.thegame.entity.enemy.BossEnemy;
import mrmathami.thegame.entity.enemy.NormalEnemy;
import mrmathami.thegame.entity.enemy.SmallerEnemy;
import mrmathami.thegame.entity.enemy.TankerEnemy;
import mrmathami.thegame.entity.tile.Mountain;
import mrmathami.thegame.entity.tile.Road;
import mrmathami.thegame.entity.tile.Target;
import mrmathami.thegame.entity.tile.spawner.*;
import mrmathami.thegame.entity.tile.tower.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public final class GameStage {
	private final long width;
	private final long height;
	@Nonnull
	private final List<GameEntity> entities;

	private GameStage(long width, long height, @Nonnull List<GameEntity> entities) {
		this.width = width;
		this.height = height;
		this.entities = List.copyOf(entities);
	}

	@Nullable
	static GameStage load(@Nonnull String name) {
		try (final InputStream stream = GameStage.class.getResourceAsStream(name)) {
			if (stream == null) throw new IOException("Resource not found! Resource name: " + name);
			final Scanner scanner = new Scanner(stream);
			try {
				final int width = scanner.nextInt();
				final int height = scanner.nextInt();
				final int numOfTiles = scanner.nextInt();
				final List<GameEntity> entities = new ArrayList<>(width * height + numOfTiles);
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						final int value = scanner.nextInt();
						if (value == 2) {
							entities.add(new Mountain(0, x, y));
							entities.add(new NormalTower(0, x, y));
						}
						else
							if (value == 3) {
								entities.add(new Mountain(0, x, y));
								entities.add(new MachineGunTower(0, x, y));
							}
						else
							if (value == 4) {
								entities.add(new Mountain(0, x, y));
								entities.add(new SniperTower(0, x, y));
							}
						else
							if (value == 5) {
								entities.add(new Mountain(0, x, y));
								entities.add(new SuperTower(0, x, y));
							}
						else
							if (value == 0) entities.add(new Road(0, x, y));
						else
							if (value == 1) entities.add(new Mountain(0, x, y));
						else {
							throw new InputMismatchException("Unexpected value! Input value: " + value);
						}
					}
				}
				// path finding
				final Queue<Road> roadQueue = new LinkedList<>();

				//for (int i = 0; i < numOfTiles; i++) {
				while (scanner.hasNext()) {
					final String value = scanner.next();
					if ("NormalBullet".equals(value)) {
						final int x = scanner.nextInt();
						final int y = scanner.nextInt();
						final double deltaX = Double.parseDouble(scanner.next());
						final double deltaY = Double.parseDouble(scanner.next());
						entities.add(new NormalBullet(0, x, y, deltaX, deltaY));
					} else if ("MachineGunBullet".equals(value)) {
						final int x = scanner.nextInt();
						final int y = scanner.nextInt();
						final double deltaX = Double.parseDouble(scanner.next());
						final double deltaY = Double.parseDouble(scanner.next());
						entities.add(new MachineGunBullet(0, x, y, deltaX, deltaY));
					} else if ("SniperBullet".equals(value)) {
						final int x = scanner.nextInt();
						final int y = scanner.nextInt();
						final double deltaX = Double.parseDouble(scanner.next());
						final double deltaY = Double.parseDouble(scanner.next());
						entities.add(new SniperBullet(0, x, y, deltaX, deltaY));
					} else if ("SuperBullet".equals(value)) {
						final int x = scanner.nextInt();
						final int y = scanner.nextInt();
						final double deltaX = Double.parseDouble(scanner.next());
						final double deltaY = Double.parseDouble(scanner.next());
						entities.add(new SuperBullet(0, x, y, deltaX, deltaY));
					} else if ("NormalEnemy".equals(value)) {
						final int x = scanner.nextInt();
						final int y = scanner.nextInt();
						final int health = scanner.nextInt();
						entities.add(new NormalEnemy(0, x, y, health));
					} else if ("SmallerEnemy".equals(value)) {
						final int x = scanner.nextInt();
						final int y = scanner.nextInt();
						final int health = scanner.nextInt();
						entities.add(new SmallerEnemy(0, x, y, health));
					} else if ("TankerEnemy".equals(value)) {
						final int x = scanner.nextInt();
						final int y = scanner.nextInt();
						final int health = scanner.nextInt();
						entities.add(new TankerEnemy(0, x, y, health));
					} else if ("BossEnemy".equals(value)) {
						final int x = scanner.nextInt();
						final int y = scanner.nextInt();
						final int health = scanner.nextInt();
						entities.add(new BossEnemy(0, x, y, health));
					} else if ("NormalSpawner".equals(value)) {
						final int x = scanner.nextInt();
						final int y = scanner.nextInt();
						final int w = scanner.nextInt();
						final int h = scanner.nextInt();
						final int spawnInterval = scanner.nextInt();
						final int initialDelay = scanner.nextInt();
						final int numOfSpawn = scanner.nextInt();
						entities.add(new NormalSpawner(0, x, y, w, h, spawnInterval, initialDelay, numOfSpawn));
					} else if ("SmallerSpawner".equals(value)) {
						final int x = scanner.nextInt();
						final int y = scanner.nextInt();
						final int w = scanner.nextInt();
						final int h = scanner.nextInt();
						final int spawnInterval = scanner.nextInt();
						final int initialDelay = scanner.nextInt();
						final int numOfSpawn = scanner.nextInt();
						entities.add(new SmallerSpawner(0, x, y, w, h, spawnInterval, initialDelay, numOfSpawn));
					} else if ("TankerSpawner".equals(value)) {
						final int x = scanner.nextInt();
						final int y = scanner.nextInt();
						final int w = scanner.nextInt();
						final int h = scanner.nextInt();
						final int spawnInterval = scanner.nextInt();
						final int initialDelay = scanner.nextInt();
						final int numOfSpawn = scanner.nextInt();
						entities.add(new TankerSpawner(0, x, y, w, h, spawnInterval, initialDelay, numOfSpawn));
					} else if ("BossSpawner".equals(value)) {
						final int x = scanner.nextInt();
						final int y = scanner.nextInt();
						final int w = scanner.nextInt();
						final int h = scanner.nextInt();
						final int spawnInterval = scanner.nextInt();
						final int initialDelay = scanner.nextInt();
						final int numOfSpawn = scanner.nextInt();
						entities.add(new BossSpawner(0, x, y, w, h, spawnInterval, initialDelay, numOfSpawn));
					} else if ("Target".equals(value)) {
						final int x = scanner.nextInt();
						final int y = scanner.nextInt();
						final int w = scanner.nextInt();
						final int h = scanner.nextInt();
						final int health = scanner.nextInt();
						entities.add(new Target(0, x, y, w, h, health));

						final Collection<GameEntity> overlappedEntities = GameEntities.getOverlappedEntities(entities, x, y, w, h);
						for (GameEntity entity : overlappedEntities) {
							if (entity instanceof Road) {
								final Road road = (Road) entity;
								roadQueue.add(road);
								road.setDistance(0.0);
							}
						}
					} else {
						System.out.println("Unexpected value! Input value: " + value);
						scanner.nextLine();
//						throw new InputMismatchException("Unexpected value! Input value: " + value);
					}
				}

				final Set<Road> roadSet = new HashSet<>(roadQueue);
				while (!roadQueue.isEmpty()) {
					final Road road = roadQueue.poll();
					roadSet.add(road);
					final Collection<Road> overlappedEntities = GameEntities.getFilteredOverlappedEntities(entities, Road.class,
							road.getPosX() - 0.5, road.getPosY() - 0.5, 2.0, 2.0);
					for (Road destRoad : overlappedEntities) {
						if (!roadSet.contains(destRoad)) {
							if (!roadQueue.contains(destRoad)) roadQueue.add(destRoad);
							final double deltaX = road.getPosX() - destRoad.getPosX();
							final double deltaY = road.getPosY() - destRoad.getPosY();
							final Collection<Road> destOverlappedRoads = GameEntities.getFilteredOverlappedEntities(entities, Road.class,
									destRoad.getPosX() - 0.5, destRoad.getPosY() - 0.5, 2.0, 2.0);
							final double destDistance = road.getDistance() + Math.sqrt(deltaX * deltaX + deltaY * deltaY) / destOverlappedRoads.size();
							if (Double.isNaN(destRoad.getDistance()) || destRoad.getDistance() > destDistance) {
								destRoad.setDistance(destDistance);
							}
						}
					}
				}

				return new GameStage(width, height, entities);
			} catch (NoSuchElementException e) {
				throw new IOException("Resource invalid! Resource name: " + name, e);
			}
			// width height numOfRemainingTiles
			// (width*height matrix with 1 for Mountain, 0 for Road, 2 - Normal tower, 3 - Machine gun tower, 4 - Sniper tower, 5 - Super tower)
			// <SpawnerName> x y w h spawnInterval initialDelay numOfSpawn
			//<EnemyName> x y health
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public final long getWidth() {
		return width;
	}

	public final long getHeight() {
		return height;
	}

	@Nonnull
	final List<GameEntity> getEntities() {
		return entities;
	}
}
