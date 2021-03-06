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
import java.time.LocalDate;

/**
 * Egy játékos adatait tartalmazó osztály.
 *
 * @author Kokas István
 */
public class Player {

    /**
     * A játékos neve.
     */
    private String name;

    /**
     * A játékos által elért pontszám.
     */
    private int point;

    /**
     * A játékos által játszott játék időpontja.
     */
    private LocalDate date;

    /**
     * Készít egy új játékost a megadott név és pont alapján. A játék időpontja
     * a játékos létrehozásának időpontja lesz
     *
     * @param name a játékos neve
     * @param point a játékos által elért pontszám
     */
    public Player(String name, int point) {
        this.name = name;
        this.point = point;
        this.date = LocalDate.now();
    }

    /**
     * Készít egy új játékost a megadott név, pont és játék időpontja alapján.
     *
     * @param name a játékos neve
     * @param point a játékos által elért pontszám
     * @param date a játék időpontja
     */
    public Player(String name, int point, LocalDate date) {
        this.name = name;
        this.point = point;
        this.date = date;
    }

    /**
     * Visszaadja a játékos nevét.
     *
     * @return a játékos neve
     */
    public String getName() {
        return name;
    }

    /**
     * Visszaadja a játékos pontszámát.
     *
     * @return a játékos által elért pontszám
     */
    public int getPoint() {
        return point;
    }

    /**
     * Visszaadja a játkos által játszott játék időpontját.
     *
     * @return a játékos által játszott játék időpontja
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Beállítja a játékos nevét.
     *
     * @param name a játékos neve
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Beállítja a játékos pontszámát.
     *
     * @param point a játékos által elért pontszám
     */
    public void setPont(int point) {
        this.point = point;
    }

    /**
     * Beállítja a játékos általál játszott játék időpontját.
     *
     * @param date a játék időpotja
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @SuppressWarnings("checkstyle:javadocmethod")
    @Override
    public String toString() {
        return "Player{" + "name=" + name + ", point=" + point + ", date=" + date + '}';
    }
}
