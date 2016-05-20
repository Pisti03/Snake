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
import java.util.Random;

/**
 * Egy cella pozícióját tartalmazó osztály.
 *
 * @author Kokas István
 */
public class Position {

    /**
     * A cella x koordinátája.
     */
    private int x;
    
    /**
     * A cella y koordinátája.
     */
    private int y;

    /**
     * Visszaadja az x koordinátát.
     *
     * @return az objektum x koordinátája
     */
    public int getX() {
        return x;
    }

    /**
     * Visszaadja az y koordinátát.
     *
     * @return az objektum y koordinátája
     */
    public int getY() {
        return y;
    }

    /**
     * Beállítja az objektum x koordinátáját.
     *
     * @param x az objektum x koordinátája
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Beállítja az objektum y koordinátáját.
     *
     * @param y az objektum y koordinátája
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Készít egy új pozíciót a megadott x és y koordináták alapján.
     *
     * @param x az új pozíció x koordinátája
     * @param y az új pozíció y koordinátája
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Készít egy új pozíciót 0 értékű x és y koordinátákkal.
     */
    public Position() {
        x = 0;
        y = 0;
    }

    /**
     * Készít egy pozíciót egy másik pozíció koordinátái alapján.
     *
     * @param a egy másik pozíció
     */
    public Position(Position a) {
        x = a.x;
        y = a.y;
    }

    /**
     * Egy másik pozíció alapján beállítja ennek a pozíciónak a koordinátáit.
     *
     * @param a egy másik pozíció
     */
    public void setValue(Position a) {
        this.x = a.x;
        this.y = a.y;
    }

    /**
     * A pozíció x és y koordinátáit egy véletlenszerű értékre állítja.
     *
     * @param min a véletlenszám alsó határa
     * @param max a véletlenszám felső határa
     */
    public void randomize(int min, int max) {
        Random rand = new Random();
        x = rand.nextInt(max - min) + min;
        y = rand.nextInt(max - min) + min;
    }

    /**
     * Igazat ad vissza, ha az objektum megegyezik a paraméterként kapott
     * objektummal.
     *
     * @param obj egy másik objektum
     * @return <code>igaz</code>, ha az objektum megegyezik a paraméterként
     * kapott objektummal, egyébként <code>hamis</code>
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    /**
     * Visszaad egy a pozíció koordinátáit leíró {@link String}-et.
     *
     * @return egy a pozívió koordinátáit leíró {@link String}
     */
    @Override
    public String toString() {
        return "Position{" + "x=" + x + ", y=" + y + '}';
    }

}
