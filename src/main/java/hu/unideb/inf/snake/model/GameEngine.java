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
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
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
    private Timeline gameLoop;
    private boolean paused = true;
    private Snake snake;
    private SimpleBooleanProperty game;
    private IntegerProperty score;
    private Position food;
    private SnakeEngine engine;
    private GridPane map;
    /**
     * A fal állapota, be van e kapcsolva.
     */
    private Boolean wall = true;

    public GameEngine(int frameRate) {
        this.frameRate = frameRate;
        gameLoop = creategameLoop();
    }

    public GameEngine(int frameRate, ArrayList<Position> list, GridPane map, SimpleBooleanProperty game, IntegerProperty score, IntegerProperty frameCount) {
        this.frameRate = frameRate;
        gameLoop = creategameLoop();
        this.snake = new Snake(Direction.RIGHT, list);
        this.map = map;
        this.game = game;
        this.score = score;
        this.engine = new SnakeEngine();
        this.food = new Position();
        this.frameCount = frameCount;
        food.randomize(0, 29);

    }

    public Position getFood() {
        return food;
    }

    public void run() {
        snake.turn();
        snake.setBody(engine.moveToNextPosition(snake.getBody(), snake.getDirection(), food, wall, game));
        score.set(engine.countScore(snake.getBody()));
        frameCount.set(frameCount.getValue() + 1);
    }

    /**
     * Elindítja a játékot.
     */
    public void start() {
        gameLoop.play();
        paused = false;
    }

    /**
     * Szüneteli a játékot.
     */
    public void pause() {
        gameLoop.pause();
        paused = true;
    }

    /**
     * Lestoppolja a játékot.
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
     * Megváltoztatja a játék sebességét.
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

    public IntegerProperty getFrameCount() {
        return frameCount;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public Snake getSnake() {
        return snake;
    }

    public Boolean getWall() {
        return wall;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public void setWall(Boolean wall) {
        this.wall = wall;
    }
}
