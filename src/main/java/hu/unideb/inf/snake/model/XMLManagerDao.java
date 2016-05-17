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
import java.nio.file.Path;
import java.util.List;
import org.w3c.dom.Element;

/**
 * Az XMLManager DAO osztálya, az XML fájlon végezhető műveleteket definiálja.
 *
 * @author Kokas István
 */
public interface XMLManagerDao {

    /**
     * Létrehozza a megadott elérési útvonalon az players.xml fájlt.
     *
     * @param path az elérési útvonal
     */
    public void createPlayersXML(Path path);

    /**
     * Visszaadja az xml fájlból kiolvasott játékosok adatait egy listában.
     *
     * @param path az xml fájl elérési útvonala
     * @return az xml fájlból kiolvasott játékosok adatait tartalmazó lista
     */
    public List<Element> readPlayersFromXML(Path path);

    /**
     * Pontszám alapján csökkenő sorrendbe rendezi a listában szereplő
     * játékosokat.
     *
     * @param lista játékosok adatait tartalmazó listta
     * @return játékosokat tartalmazó lista pontszám szerint csökkenő sorrendben
     */
    public List<Element> sortPlayersByScore(List<Element> lista);

    /**
     * Egy új játékost ad a players.xml fájlhoz.
     *
     * @param path az xml fájl elérési útvonala
     * @param jatekos a játékos adatait tartalmazó {@link Player} objektum
     */
    public void addNewPlayerToXML(Path path, Player jatekos);

    /**
     * Igazat ad vissza, ha az adott útvonalon lévő XML fájlban szerepelnek az
     * adott játékos adatai.
     *
     * @param path az xml fájl elérési útvonala
     * @param jatekos a játékos adatait tartalmazó {@link Player} objektum
     * @return igaz, ha a játékos adatai szerepelnek az XML fájlban
     */
    public boolean isPlayerInXML(Path path, Player jatekos);
}
