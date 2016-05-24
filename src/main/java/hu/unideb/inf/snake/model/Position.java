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
     * @return a pozíció x koordinátája
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
     * Beállítja a pozíció x koordinátáját.
     *
     * @param x a pozíció x koordinátája
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Beállítja a pozíció y koordinátáját.
     *
     * @param y a pozíció y koordinátája
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
     * Készít egy pozíciót egy másik pozíció koordinátái alapján. Az új pozíció
     * x és y koordinátája a paraméterként kapott pozíció x és y koordinátájával
     * lesz egyenlő.
     *
     * @param a egy másik pozíció
     */
    public Position(Position a) {
        x = a.x;
        y = a.y;
    }

    /**
     * Egy másik pozíció alapján beállítja ennek a pozíciónak a koordinátáit.
     * Átveszi az másik pozíció x és y koordinátájának értékét.
     *
     * @param a egy másik pozíció
     */
    public void setValue(Position a) {
        this.x = a.x;
        this.y = a.y;
    }

    /**
     * A pozíció x és y koordinátáit egy véletlenszerű értékre állítja adott
     * alsó és felső határ alapján.
     *
     * @param min a véletlenszám alsó határa
     * @param max a véletlenszám felső határa
     */
    public void randomize(int min, int max) {
        Random rand = new Random();
        x = rand.nextInt(max - min) + min;
        y = rand.nextInt(max - min) + min;
    }

    @SuppressWarnings("checkstyle:javadocmethod")
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

    @SuppressWarnings("checkstyle:javadocmethod")
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + this.x;
        hash = 73 * hash + this.y;
        return hash;
    }

    @SuppressWarnings("checkstyle:javadocmethod")
    @Override
    public String toString() {
        return "Position{" + "x=" + x + ", y=" + y + '}';
    }

}
