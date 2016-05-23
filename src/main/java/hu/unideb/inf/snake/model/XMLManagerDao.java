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

/**
 * Az XML fájlon végezhető műveleteket definiáló interfész.
 *
 * @author Kokas István
 */
public interface XMLManagerDao {

    /**
     * Létrehozza a megadott elérési útvonalon a players.xml fájlt.
     *
     * @param path az elérési útvonal
     */
    public void createPlayersXML(Path path);

    /**
     * Visszaad egy az xml fájlból kiolvasott játékosok adatait tartalmazó
     * listát.
     *
     * @param path az xml fájl elérési útvonala
     * @return az xml fájlból kiolvasott játékosok adatait tartalmazó lista
     */
    public List<Player> readPlayersFromXML(Path path);

    /**
     * Pontszám alapján csökkenő sorrendbe rendezi a paraméterként kapott
     * listában szereplő játékosokat.
     *
     * @param lista játékosok adatait tartalmazó listta
     * @return játékosokat tartalmazó lista pontszám szerint csökkenő sorrendben
     */
    public List<Player> sortPlayersByScore(List<Player> lista);

    /**
     * Egy új játékost ad a players.xml fájlhoz a paraméterként kapott
     * <code>jatekos</code> adatai alapján.
     *
     * @param path az xml fájl elérési útvonala
     * @param jatekos a játékos adatait tartalmazó {@link Player} objektum
     */
    public void addNewPlayerToXML(Path path, Player jatekos);

    /**
     * Igazat ad vissza, ha az adott útvonalon lévő XML fájlban szerepelnek az
     * adott játékos adatai. A figyelembe vett adatok a {@link Player#name},
     * azaz a játékos neve és a {@link Player#point}, a játékos pontszáma.
     *
     * @param path az xml fájl elérési útvonala
     * @param player a játékos adatait tartalmazó {@link Player} objektum
     * @return <code>igaz</code>, ha a játékos adatai szerepelnek az XML
     * fájlban, egyébként <code>hamis</code>
     */
    public boolean isPlayerInXML(Path path, Player player);
}
