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

import hu.unideb.inf.snake.model.GameEngine;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kokas István
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
