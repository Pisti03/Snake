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


import hu.unideb.inf.snake.model.Position;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kokas István
 */
public class PositionTest {

    private static Position pos;

    @BeforeClass
    public static void setUpClass() {
        pos = new Position(1, 2);
    }

    @AfterClass
    public static void tearDownClass() {
        pos = null;
    }

    @Test
    public void testToString() {
        assertEquals(pos.toString(), "Position{x=1, y=2}");
    }

    @Test
    public void testRandomize() {
        Position pos2 = new Position(pos);
        pos2.randomize(0, 30);
        assertNotEquals(pos, pos2);
    }

    @Test
    public void testEquals(){
        Position teszt=null;
        Position teszt2 = new Position();
        String position = "ez nem egy pozicio";
        assertFalse(teszt2.equals(teszt));
        assertFalse(teszt2.equals(position));
        Position teszt3 = new Position();
        assertEquals(teszt3, teszt2); 
    }
}
