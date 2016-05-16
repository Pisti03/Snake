/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.snake;

import hu.unideb.inf.snake.model.Position;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pisti
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

}
