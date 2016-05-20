package hu.unideb.inf.snake;

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

import hu.unideb.inf.snake.model.Direction;
import hu.unideb.inf.snake.model.Position;
import hu.unideb.inf.snake.model.Snake;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kokas István
 */
public class SnakeTest {

    private static Snake snake;

    @BeforeClass
    public static void setUpClass() {
        List<Position> body = new ArrayList<>();
        body.add(new Position(1, 1));
        body.add(new Position(2, 1));
        body.add(new Position(3, 1));
        snake = new Snake(Direction.DOWN, body);
    }

    @AfterClass
    public static void tearDownClass() {
        snake = null;
    }

    @Test
    public void testTurn() {
        snake.setNextDirection(Direction.RIGHT);
        assertEquals(Direction.RIGHT, snake.getNextDirection());
        assertEquals(Direction.DOWN, snake.getDirection());
        snake.turn();
        assertEquals(Direction.RIGHT, snake.getDirection());
    }

    @Test
    public void testSetNextDirection() {
        snake.setNextDirection(Direction.UP);
        snake.turn();
        assertEquals(Direction.UP, snake.getDirection());
        assertEquals(Direction.UP, snake.getNextDirection());
        snake.setNextDirection(Direction.DOWN);
        snake.turn();
        assertEquals(Direction.UP, snake.getDirection());
        assertEquals(Direction.UP, snake.getNextDirection());
        snake.setNextDirection(Direction.RIGHT);
        snake.turn();
        assertEquals(Direction.RIGHT, snake.getDirection());
        assertEquals(Direction.RIGHT, snake.getNextDirection());
    }
}
