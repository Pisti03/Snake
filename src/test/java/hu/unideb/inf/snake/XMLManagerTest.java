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
import hu.unideb.inf.snake.model.Player;
import hu.unideb.inf.snake.model.XMLManager;
import hu.unideb.inf.snake.model.XMLManagerDao;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.w3c.dom.Element;

/**
 *
 * @author Kokas István
 */
public class XMLManagerTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    private static XMLManagerDao manager;

    @BeforeClass
    public static void setUpClass() {
        manager = new XMLManager();
    }

    @AfterClass
    public static void tearDownClass() {
        manager = null;
    }

    @Test
    public void testCreatePlayersXML() throws IOException {
        Path p = Paths.get(tempFolder.getRoot().getPath(), "players.xml");
        manager.createPlayersXML(p);
        assertTrue(p.toFile().isFile());
    }

    @Test
    public void testIsPlayerInXML() {
        Path p = Paths.get(tempFolder.getRoot().getPath(), "players.xml");
        manager.createPlayersXML(p);
        Player jatekos = new Player("Asd", 5);
        manager.addNewPlayerToXML(p, jatekos);
        Player jatekos2 = new Player("Asd2", 3);
        assertTrue(manager.isPlayerInXML(p, jatekos));
        assertFalse(manager.isPlayerInXML(p, jatekos2));
    }

    @Test
    public void testAddNewPlayerToXML() {
        Path p = Paths.get(tempFolder.getRoot().getPath(), "players.xml");
        manager.createPlayersXML(p);
        Player jatekos = new Player("Asd", 5);
        Player jatekos2 = new Player("Asd2", 3);
        Player jatekos3 = new Player("Asd3", 4);
        manager.addNewPlayerToXML(p, jatekos);
        manager.addNewPlayerToXML(p, jatekos2);
        manager.addNewPlayerToXML(p, jatekos3);
        assertTrue(manager.isPlayerInXML(p, jatekos));
        assertTrue(manager.isPlayerInXML(p, jatekos2));
        assertTrue(manager.isPlayerInXML(p, jatekos2));

    }

    @Test
    public void testReadPlayersFromXML() {
        Path p = Paths.get(tempFolder.getRoot().getPath(), "players.xml");
        manager.createPlayersXML(p);
        Player jatekos = new Player("Asd", 5);
        Player jatekos2 = new Player("Asd2", 3);
        Player jatekos3 = new Player("Asd3", 4);
        manager.addNewPlayerToXML(p, jatekos);
        manager.addNewPlayerToXML(p, jatekos2);
        manager.addNewPlayerToXML(p, jatekos3);
        List<Element> lista = manager.readPlayersFromXML(p);
        assertEquals(lista.get(0).getElementsByTagName("nev").item(0).getTextContent(), "Asd");
        assertEquals(lista.get(0).getElementsByTagName("pont").item(0).getTextContent(), "5");
        assertEquals(lista.get(1).getElementsByTagName("nev").item(0).getTextContent(), "Asd2");
        assertEquals(lista.get(1).getElementsByTagName("pont").item(0).getTextContent(), "3");
        assertEquals(lista.get(2).getElementsByTagName("nev").item(0).getTextContent(), "Asd3");
        assertEquals(lista.get(2).getElementsByTagName("pont").item(0).getTextContent(), "4");
    }

    @Test
    public void testSortPlayersByScore() {
        Path p = Paths.get(tempFolder.getRoot().getPath(), "players.xml");
        manager.createPlayersXML(p);
        Player jatekos = new Player("Asd", 5);
        Player jatekos2 = new Player("Asd2", 3);
        Player jatekos3 = new Player("Asd3", 4);
        manager.addNewPlayerToXML(p, jatekos);
        manager.addNewPlayerToXML(p, jatekos2);
        manager.addNewPlayerToXML(p, jatekos3);
        List<Element> lista = manager.readPlayersFromXML(p);
        assertEquals(lista.get(0).getElementsByTagName("pont").item(0).getTextContent(), "5");
        assertEquals(lista.get(1).getElementsByTagName("pont").item(0).getTextContent(), "3");
        assertEquals(lista.get(2).getElementsByTagName("pont").item(0).getTextContent(), "4");
        lista = manager.sortPlayersByScore(lista);
        assertEquals(lista.get(0).getElementsByTagName("pont").item(0).getTextContent(), "5");
        assertEquals(lista.get(1).getElementsByTagName("pont").item(0).getTextContent(), "4");
        assertEquals(lista.get(2).getElementsByTagName("pont").item(0).getTextContent(), "3");
    }
}
