package mrmathami.thegame;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.stage.WindowEvent;
import mrmathami.thegame.drawer.GameDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.bullet.AbstractBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.tile.Mountain;
import mrmathami.thegame.entity.tile.Road;
import mrmathami.thegame.entity.tile.spawner.AbstractSpawner;
import mrmathami.thegame.entity.tile.tower.*;
import mrmathami.utilities.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * A game controller. Everything about the game should be managed in here.
 */
public final class GameController extends AnimationTimer {
	/**
	 * Advance stuff. Just don't touch me. Google me if you are curious.
	 */
	private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor(
			new ThreadFactoryBuilder()
					.setNamePrefix("Tick")
					.setDaemon(true)
					.setPriority(Thread.NORM_PRIORITY)
					.build()
	);

	/**
	 * The screen to draw on. Just don't touch me. Google me if you are curious.
	 */
	private final GraphicsContext graphicsContext;

	/**
	 * Game field. Contain everything in the current game field.
	 * Responsible to update the field every tick.
	 * Kinda advance, modify if you are sure about your change.
	 */
	private GameField field;

	/**
	 * Game drawer. Responsible to draw the field every tick.
	 * Kinda advance, modify if you are sure about your change.
	 */
	private GameDrawer drawer;

	/**
	 * Beat-keeper Manager. Just don't touch me. Google me if you are curious.
	 */
	private ScheduledFuture<?> scheduledFuture;

	/**
	 * The tick value. Just don't touch me.
	 * Google me if you are curious, especially about volatile.
	 * WARNING: Wall of text.
	 */
	private volatile long tick;

	/**
	 * The constructor.
	 * @param map location to game stage file
	 * @param graphicsContext the screen to draw on
	 */
	public GameController(GraphicsContext graphicsContext, String map) {
		// The screen to draw on
		this.graphicsContext = graphicsContext;

		// Just a few acronyms.
		final long width = Config.TILE_HORIZONTAL;
		final long height = Config.TILE_VERTICAL;

		// The game field. Please consider create another way to load a game field.
		// TODO: I don't have much time, so, spawn some wall then :)
		this.field = new GameField(Objects.requireNonNull(GameStage.load(map)));

		// The drawer. Nothing fun here.
		this.drawer = new GameDrawer(graphicsContext, field);

		// Field view region is a rectangle region
		// [(posX, posY), (posX + SCREEN_WIDTH / zoom, posY + SCREEN_HEIGHT / zoom)]
		// that the drawer will select and draw everything in it in an self-defined order.
		// Can be modified to support zoom in / zoom out of the map.
		drawer.setFieldViewRegion(0.0, 0.0, Config.TILE_SIZE);
	}

    /**
     * Draw Mountain, Taget, Spawn and road, which don't need to draw again in game
     */
	void drawMap(Canvas canvas) {
		// Draw all Tiles
		GraphicsContext graphicsContext1 = canvas.getGraphicsContext2D();
		graphicsContext1.setFontSmoothingType(FontSmoothingType.LCD);

		GameDrawer drawer1 = new GameDrawer(graphicsContext1, field);
		drawer1.setFieldViewRegion(0.0, 0.0, Config.TILE_SIZE);
		List<GameEntity> tiles = new ArrayList<>(field.getEntities(Mountain.class));
		tiles.addAll(field.getEntities(Road.class));
		tiles.addAll(field.getEntities(AbstractSpawner.class));
		tiles.add(field.getTarget());
		drawer1.render(tiles);

		graphicsContext1.setFill(Color.CADETBLUE);
		graphicsContext1.fillRect(0, field.getHeight() * Config.TILE_SIZE, field.getWidth() * Config.TILE_SIZE, Config.SCREEN_HEIGHT);
	}

	/**
	 * @return current pane
	 */
	AnchorPane getPane(){
		return (AnchorPane) (this.graphicsContext.getCanvas().getParent());
	}

	/**
	 * Beat-keeper. Just don't touch me.
	 */
	private void tick() {
		//noinspection NonAtomicOperationOnVolatileField
		this.tick += 1;
	}

	/**
	 * A JavaFX loop.
	 * Kinda advance, modify if you are sure about your change.
	 *
	 * @param now not used. It is a timestamp in nanosecond.
	 */
	@Override
	public void handle(long now) {
		// don't touch me.
		final long currentTick = tick;
		final long startNs = System.nanoTime();

		// do a tick, as fast as possible
		field.tick();

//		// if it's too late to draw a new frame, skip it.
//		// make the game feel really laggy, so...
//		if (currentTick != tick) return;

		// draw a new frame, as fast as possible.
		List<GameEntity> entities = new ArrayList<>();
		entities.addAll(field.getEntities(AbstractEnemy.class));
		entities.addAll(field.getEntities(AbstractBullet.class));
		entities.addAll(field.getEntities(AbstractTower.class));
		drawer.render(entities);

		// Fixed MSPT
		while (System.nanoTime() - startNs <= 70000000);

		// MSPT display. MSPT stand for Milliseconds Per Tick.
		// It means how many ms your game spend to update and then draw the game once.
		// Draw it out mostly for debug
		final double mspt = (System.nanoTime() - startNs) / 1000000.0;
		graphicsContext.setFont(Font.font(10));
		graphicsContext.setFill(Color.BLACK);
		graphicsContext.fillText(String.format("MSPT: %3.2f", mspt), 0, 12);

		// Display coin from kill ememy
		graphicsContext.setFont(Font.font("Cooper Black", 20));
		graphicsContext.fillText(String.format("%d", field.getCoin()), 908, 600);

		// Display Health and handle if target was destroyed
		graphicsContext.setFill(Color.DEEPPINK);
		if ( field.getTarget() != null) graphicsContext.fillText(String.format("%d", field.getTarget().getHealth()), 810, 600);
		else {
			try {
				this.pause();
				Main.onGameOver((AnchorPane) (graphicsContext.getCanvas().getParent()));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Handle when all enemies was destroyed, display win pane
		Collection<AbstractSpawner> spawners = GameEntities.getFilteredOverlappedEntities(field.getAllEntities(), AbstractSpawner.class, 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		boolean canSpawn = true;
		for (AbstractSpawner spawner : spawners) {
			if (!spawner.canSpawn()) {
				canSpawn = false;
				break;
			}
		}
		if (!canSpawn) {
			if (GameEntities.getFilteredOverlappedEntities(field.getAllEntities(), AbstractEnemy.class, 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT).size() <= 0) {
				this.pause();
				try {
					Main.onWin((AnchorPane) (graphicsContext.getCanvas().getParent()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}


		// if we have time to spend, do a spin
		while (currentTick == tick) Thread.onSpinWait();
	}

	/**
	 * Start rendering and ticking. Just don't touch me.
	 * Anything that need to initialize should be in the constructor.
	 */
	@Override
	public void start() {
		// Start the beat-keeper. Start to call this::tick at a fixed rate.
		this.scheduledFuture = SCHEDULER.scheduleAtFixedRate(this::tick, 0, Config.GAME_NSPT, TimeUnit.NANOSECONDS);
		// start the JavaFX loop.
		super.start();
	}

	/**
	 * On window close request.
	 * Kinda advance, modify if you are sure about your change.
	 *
	 * @param windowEvent currently not used
	 */
	final void closeRequestHandler(WindowEvent windowEvent) {
		scheduledFuture.cancel(true);
		stop();
		Platform.exit();
		System.exit(0);
	}

	/**
	 * Pause/continue game
	 */
    void pause() {
		this.stop();
	}
	void continue_() {
		super.start();
	}

	/**
	 * Create Tower to position where mouse drag if enough coin
	 * @param mouseEvent
	 */
    void buyTower(MouseEvent mouseEvent, String towerName) {
		int fieldPosX = (int) (mouseEvent.getSceneX() / Config.TILE_SIZE);
		int fieldPosY = (int) (mouseEvent.getSceneY() / Config.TILE_SIZE);
		System.out.println("(PosX,PosY) = (" + fieldPosX + "," + fieldPosY + ")");
		if (fieldPosX >= Config.TILE_HORIZONTAL || fieldPosY >= Config.TILE_VERTICAL) {
			System.out.println("Position is out of screen !!");
			System.out.println(towerName + " purchase failed !!");
			return;
		}

		List<GameEntity> entities = (ArrayList<GameEntity>) GameEntities.getContainedEntities(this.field.getAllEntities(),fieldPosX, fieldPosY, 1, 1);
		boolean canBuy = true;
		for (GameEntity entity : entities) if (!(entity instanceof AbstractTower) && !(entity instanceof Mountain)) canBuy = false;

		if (canBuy) {
			AbstractTower newTower;
			switch (towerName) {
				case "normal tower": {
					newTower = new NormalTower(0, fieldPosX, fieldPosY);
					break;
				}
				case "machine gun tower": {
					newTower = new MachineGunTower(0, fieldPosX, fieldPosY);
					break;
				}
				case "sniper tower": {
					newTower = new SniperTower(0, fieldPosX, fieldPosY);
					break;
				}
				case "super tower": {
					newTower = new SuperTower(0, fieldPosX, fieldPosY);
					break;
				}
				default:
					newTower = new NormalTower(0, fieldPosX, fieldPosY);
					System.out.println(towerName + "is not exist, buy normal tower !!");
			}
			if (newTower.getCost() <= this.field.getCoin()) {
				for (GameEntity entity : entities) {
					if (entity instanceof AbstractTower) {
						this.field.removeEntity(entity);
						this.field.updateCoin(((AbstractTower) entity).getCost());
					}
				}
				this.field.doSpawn(newTower);
				this.field.updateCoin(-newTower.getCost());

				System.out.println("Successfully bought " + towerName + " !!");
				return;
			}
			else System.out.println("Not enough coin to buy " + towerName);
		}
		System.out.println(towerName + " purchase failed !!");
	}
}
