package hu.unideb.inf.snake.model;

/*
 * #%L
 * Snake
 * %%
 * Copyright (C) 2016 University of Debrecen, Faculty of Informatics
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * A játék motorja.
 *
 * @author Kokas István
 */
public class GameEngine {

    /**
     * Az eddig lefutott {@link KeyFrame}-ek száma.
     */
    private IntegerProperty frameCount;
    /**
     * A másodpercenként lefutó {@link KeyFrame}-ek száma.
     */
    private int frameRate;
    /**
     * A játék ciklus.
     */
    private Timeline gameLoop;
    /**
     * Szünetel e épp a játék.
     */
    private boolean paused = true;
    /**
     * Egy új {@link Snake}.
     */
    private Snake snake;
    /**
     * Fut e még a játék.
     */
    private SimpleBooleanProperty game;
    /**
     * Elért pontszám.
     */
    private int score;
    /**
     * Az étel pozíciója.
     */
    private Position food;
    /**
     * A Snake játék alapvető funkciót leíró osztály egy példánya.
     */
    private SnakeEngine engine;
    /**
     * A fal állapota, be van e kapcsolva.
     */
    private Boolean wall = true;

    /**
     * Készít egy új <code>GameEngine</code>-t, adott <code>frameRate</code>
     * alapján.
     *
     * @param frameRate a másodpercenként lefutó <code>Frame</code>-ek száma
     */
    public GameEngine(int frameRate) {
        this.frameRate = frameRate;
        gameLoop = creategameLoop();
    }

    /**
     * Készít egy új <code>GameEngine</code>-t, adott <code>frameRate</code>
     * alapján, létrehoz egy {@link Snake}-et {@link Direction#RIGHT}
     * kezdőíránnyal és a <code>list</code> listában található kezdőpozíciókkal.
     *
     * @param frameRate a másodpercenként lefutó <code>Frame</code>-ek száma
     * @param list a <code>Snake</code> kezdőpozícióit tartalmazó lista
     * @param game a játék állapota
     * @param frameCount a lefutott <code>Frame</code>-ek száma
     */
    public GameEngine(int frameRate, List<Position> list, SimpleBooleanProperty game, IntegerProperty frameCount) {
        this.frameRate = frameRate;
        gameLoop = creategameLoop();
        this.snake = new Snake(Direction.RIGHT, list);
        this.game = game;
        this.score = 0;
        this.engine = new SnakeEngine();
        this.food = new Position();
        this.frameCount = frameCount;
        food.randomize(0, 29);

    }

    /**
     * Visszaadja az étel pozícióját.
     *
     * @return az étel pozíciója
     */
    public Position getFood() {
        return food;
    }

    /**
     * A játék mozgásához tartozó funkciókat hajtja végre. A {@link Snake}
     * irányát a következő irányra állítja a {@link Snake#turn()} metódussal. A
     * {@link Snake}-et a következő lépésre lépteti. Lekéri a pontszámot a
     * <code>score</code> változóba a {@link SnakeEngine#countScore(java.util.List)} metódus
     * használatával. Egyel növeli a lefutott <code>Frame</code>-ek számát.
     */
    public void run() {
        snake.turn();
        snake.setBody(engine.moveToNextPosition(snake.getBody(), snake.getDirection(), food, wall, game));
        score = engine.countScore(snake.getBody());
        frameCount.set(frameCount.getValue() + 1);
    }

    /**
     * Elindítja a játékot. A <code>paused</code> változót <code>hamis</code>
     * értékre állítja.
     */
    public void start() {
        gameLoop.play();
        paused = false;
    }

    /**
     * Szüneteli a játékot. A <code>paused</code> változót <code>igaz</code>
     * értékre állítja.
     */
    public void pause() {
        gameLoop.pause();
        paused = true;
    }

    /**
     * Lestoppolja a játékot. A <code>paused</code> változót <code>igaz</code>
     * értékre állítja.
     */
    public void stop() {
        gameLoop.stop();
        paused = true;
    }

    /**
     * Megváltoztatja a játék sebességét.
     *
     * @param speed a játék sebességének neve
     */
    public void changefps(String speed) {
        switch (speed) {
            case "SLOW":
                changefps(10);
                break;
            case "MEDIUM":
                changefps(15);
                break;
            case "FAST":
                changefps(25);
                break;
            case "EXTRA":
                changefps(40);
                break;
        }
    }

    /**
     * Megváltoztatja a játék sebességét. Új játékciklust hoz létre az új
     * <code>frameRate</code> értékével.
     *
     * @param fps a játék sebességének értéke
     */
    public void changefps(int fps) {
        this.frameRate = fps;
        gameLoop.stop();
        gameLoop = creategameLoop();
        if (!paused) {
            gameLoop.play();
        }
    }

    /**
     * Létrehozza a játékciklust. A másodpercenként lefutó <code>Frame</code>-ek
     * számát a <code>frameRate</code> határozza meg és minden
     * <code>Frame</code> végrehajtásakor meghívja a {@link #run()} metódust. A
     * játék leállításig fog futni.
     *
     * @return a játékciklus
     */
    private Timeline creategameLoop() {
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                run();
            }
        };
        Duration duration = Duration.millis(1000 / frameRate);
        KeyFrame frame = new KeyFrame(duration, event);
        Timeline a = new Timeline(frameRate, frame);
        a.setCycleCount(Timeline.INDEFINITE);
        return a;
    }

    /**
     * Visszaadja a lefutott <code>Frame</code>-ek számát.
     *
     * @return a lefutott <code>Frame</code>-ek száma
     */
    public IntegerProperty getFrameCount() {
        return frameCount;
    }

    /**
     * Visszaadja az elért pontszámot.
     *
     * @return az elért pontszám
     */
    public int getScore() {
        return score;
    }

    /**
     * Visszaadja a másodpercenként lefutó <code>Frame</code>-ek számát.
     *
     * @return a másodpercenként lefutó <code>Frame</code>-ek száma
     */
    public int getFrameRate() {
        return frameRate;
    }

    /**
     * Visszaadja a létrehozzott {@link Snake}-et.
     *
     * @return a létrehozott {@link Snake}
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * Visszaadja a fal állapotát.
     *
     * @return <code>igaz</code>, ha a fal be van kapcsolva, egyébként
     * <code>hamis</code>
     */
    public Boolean getWall() {
        return wall;
    }

    /**
     * Beállítja a <code>snake</code> változó értékét.
     *
     * @param snake az új {@link Snake}
     */
    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    /**
     * Beállítja a fal állapotát.
     *
     * @param wall a fal új állapota
     */
    public void setWall(Boolean wall) {
        this.wall = wall;
    }
}
