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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Egy Snake adatatait tartalmazó osztály.
 *
 * @author Kokas István
 */
public class Snake {

    private static final Logger logger = LoggerFactory.getLogger(Snake.class);
    
    /**
     * A Snake jelenlegi iránya.
     */
    private Direction direction;
    
    /**
     * A Snake iránya amerre a következő lépésnél fordulni fog.
     */
    private Direction nextDirection;
    
    /**
     * A Snake celláinak pozícióit tartalmazó lista.
     */
    private List<Position> body;

    /**
     * Készít egy új {@link Snake} objektumok a megadott irány és a cellák
     * pozícióit tartalmazó lista alapján.
     *
     * @param direction az első lépés iránya amerre a Snake lépni fog
     * @param body a Snake kezdeti celláinak pozícióit tartalmazó lista
     */
    public Snake(Direction direction, List<Position> body) {
        this.direction = direction;
        this.nextDirection = direction;
        this.body = body;
    }

    /**
     * Visszaadja a Snake jelenlegi irányát.
     *
     * @return a Snake jelenlegi iránya
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Visszaadja a Snake következő lépésének irányát.
     *
     * @return a következő lépés iránya
     */
    public Direction getNextDirection() {
        return nextDirection;
    }

    /**
     * Visszaadja a Snake celláinak pozícióit tartalmazó listát.
     *
     * @return a Snake celláinak pozícióit tartalmazó lista
     */
    public List<Position> getBody() {
        return body;
    }

    /**
     * Beállítja a Snake celláinak pozíciót tartalmazó listát.
     *
     * @param snake a Snake celláinak pozíciót tartalmazó lista
     */
    public void setBody(List<Position> snake) {
        this.body = snake;
    }

    /**
     * A Snake irányát a következő lépés irányára állítja.
     */
    public void turn() {
        direction = nextDirection;
    }

    /**
     * Beállítja a következő lépés irányát. A jelenlegi iránnyal ellentétes
     * irány nem állítható be.
     *
     * @param direction a következő lépés iránya
     */
    public void setNextDirection(Direction direction) {
        logger.debug("Direction: " + this.direction + " nextDirection: " + nextDirection + " newDirection: " + direction);
        if (direction != Direction.RIGHT && this.direction == Direction.LEFT) {
            this.nextDirection = direction;
        } else if (direction != Direction.LEFT && this.direction == Direction.RIGHT) {
            this.nextDirection = direction;
        } else if (direction != Direction.UP && this.direction == Direction.DOWN) {
            this.nextDirection = direction;
        } else if (direction != Direction.DOWN && this.direction == Direction.UP) {
            this.nextDirection = direction;
        }
        
    }
    
}
