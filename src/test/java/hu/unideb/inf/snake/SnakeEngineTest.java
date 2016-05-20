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
import hu.unideb.inf.snake.model.SnakeEngine;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Kokas István
 */
public class SnakeEngineTest {

    private static SnakeEngine engine;

    public SnakeEngineTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        engine = new SnakeEngine();
    }

    @AfterClass
    public static void tearDownClass() {
        engine = null;
    }

    @Test
    public void testMoveToNextPosition() {
        SimpleBooleanProperty game = new SimpleBooleanProperty(true);
        Position food = new Position(20, 20);
        List<Position> snake = new ArrayList<>();
        snake.add(new Position(29, 10));
        snake.add(new Position(28, 10));
        snake.add(new Position(27, 10));
        List<Position> snake2 = new ArrayList<>();
        snake2.add(new Position(0, 10));
        snake2.add(new Position(29, 10));
        snake2.add(new Position(28, 10));
        List<Position> snake3 = engine.moveToNextPosition(snake, Direction.RIGHT, food, false, game);
        assertEquals(snake2, snake3);
        assertTrue(game.getValue());
        snake = new ArrayList<>();
        snake.add(new Position(29, 10));
        snake.add(new Position(28, 10));
        snake.add(new Position(27, 10));
        snake3 = engine.moveToNextPosition(snake, Direction.RIGHT, food, true, game);
        assertEquals(snake, snake3);
        assertFalse(game.getValue());
    }

    @Test
    public void testGetNextPosition() {
        Position nexthead = engine.getNextPosition(new Position(5, 5), Direction.RIGHT);
        assertEquals(6, nexthead.getX());
        assertEquals(5, nexthead.getY());
        nexthead = engine.getNextPosition(new Position(5, 5), Direction.UP);
        assertEquals(5, nexthead.getX());
        assertEquals(4, nexthead.getY());
        nexthead = engine.getNextPosition(new Position(5, 5), Direction.DOWN);
        assertEquals(5, nexthead.getX());
        assertEquals(6, nexthead.getY());
        nexthead = engine.getNextPosition(new Position(5, 5), Direction.LEFT);
        assertEquals(4, nexthead.getX());
        assertEquals(5, nexthead.getY());

    }

    @Test
    public void testIsOutOfMap() {
        Position teszt = new Position(30, 29);
        assertEquals(true, engine.isOutOfMap(teszt));
        teszt = new Position(29, 30);
        assertEquals(true, engine.isOutOfMap(teszt));
        teszt = new Position(-1, 29);
        assertEquals(true, engine.isOutOfMap(teszt));
        teszt = new Position(29, -1);
        assertEquals(true, engine.isOutOfMap(teszt));

    }

    @Test
    public void testBackToMap() {
        Position teszt = new Position(30, 29);
        Position newpos = engine.backToMap(teszt);
        assertEquals(0, newpos.getX());
        assertEquals(29, newpos.getY());
        teszt = new Position(29, 30);
        newpos = engine.backToMap(teszt);
        assertEquals(29, newpos.getX());
        assertEquals(0, newpos.getY());
        teszt = new Position(-1, 4);
        newpos = engine.backToMap(teszt);
        assertEquals(29, newpos.getX());
        assertEquals(4, newpos.getY());
        teszt = new Position(4, -1);
        newpos = engine.backToMap(teszt);
        assertEquals(4, newpos.getX());
        assertEquals(29, newpos.getY());

    }

    @Test
    public void testIsNextWall() {
        Position teszt = new Position(29, 3);
        assertEquals(true, engine.isNextWall(teszt, Direction.RIGHT));

    }

    @Test
    public void testIsInList() {
        List<Position> lista = new ArrayList<>();
        lista.add(new Position(5, 5));
        lista.add(new Position(10, 10));
        lista.add(new Position(29, 3));
        Position teszt = new Position(29, 3);
        assertEquals(true, engine.isInList(lista, teszt));
    }

    @Test
    public void testIsNextFood() {
        Position head = new Position(29, 3);
        Position food = new Position(28, 3);
        assertEquals(true, engine.isNextFood(head, Direction.LEFT, food));

    }

    @Test
    public void testGrow() {
        List<Position> lista = new ArrayList<>();
        lista.add(new Position(5, 5));
        lista.add(new Position(10, 10));
        lista.add(new Position(29, 3));
        Position teszt = new Position(29, 3);
        List<Position> lista2 = new ArrayList<>(lista);
        lista2.add(teszt);
        assertEquals(engine.grow(lista, teszt), lista2);
    }

    @Test
    public void testSetRandPos() {
        List<Position> lista = new ArrayList<>();
        lista.add(new Position(5, 5));
        lista.add(new Position(10, 10));
        lista.add(new Position(29, 3));
        Position food = new Position(20, 3);
        Position food2 = new Position(food);
        food2 = engine.getRandomEmptyPosition(lista, food2);
        assertFalse(food.equals(food2));
        assertFalse(engine.isInList(lista, food2));
    }

    @Test
    public void testCountPont() {
        List<Position> lista = new ArrayList<>();
        lista.add(new Position(5, 5));
        lista.add(new Position(10, 10));
        lista.add(new Position(29, 3));
        lista.add(new Position(28, 3));
        assertEquals(1, engine.countScore(lista));

    }
}
