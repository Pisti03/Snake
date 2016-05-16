/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.snake;

import hu.unideb.inf.snake.model.GameEngine;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pisti
 */
public class GameEngineTest {

    @Test
    public void testChangefpsString() {
        GameEngine engine = new GameEngine(10);
        engine.changefps("SLOW");
        assertEquals(engine.getFrameRate(), 10);
        engine.changefps("MEDIUM");
        assertEquals(engine.getFrameRate(), 15);
        engine.changefps("FAST");
        assertEquals(engine.getFrameRate(), 25);
        engine.changefps("EXTRA");
        assertEquals(engine.getFrameRate(), 40);
    }

    @Test
    public void testChangefpsInt() {
        GameEngine engine = new GameEngine(10);
        engine.changefps(20);
        assertEquals(engine.getFrameRate(), 20);
        engine.changefps(30);
        assertEquals(engine.getFrameRate(), 30);
        engine.changefps(40);
        assertEquals(engine.getFrameRate(), 40);
    }
}
