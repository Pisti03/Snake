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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * A Snake játék alapvető funkciót leíró osztály.
 *
 * @author Kokas István
 */
public class SnakeEngine {

    /**
     * A Snake összes celláját a kövekező lépés pozíciójára lépteti. Ellenőrzi,
     * hogy a következő lépésben a Snake ételt vesz fel vagy sem. Ha igen, akkor
     * megvívja a {@link #grow} metódust és a Snake nőni fog. Ha a fal ki van
     * kapcsolva és a Snake kimegy a pályáról, amit az {@link #isOutOfMap}
     * metódussal ellenőríz, akkor a {@link #backToMap} metódussal visszahelyezi
     * a pályára. Ha a fal be van kapcsolva és falnak ütközik, vagy önmagának a
     * <code>game</code> változó értékét hamisra állítja.
     *
     * @param snake a Snake celláinak léptetés előtti pozícióit tartalmazó lista
     * @param direction a lépés iránya
     * @param food az étel pozíciója
     * @param fal a fal állapota
     * @param game a játék állapota, igaz ha a játék még fut
     * @return a Snake celláinak léptetés utáni új pozícióit tartalmazó lista
     */
    public List<Position> moveToNextPosition(List<Position> snake, Direction direction, Position food, boolean fal, SimpleBooleanProperty game) {
        Position seged = new Position(snake.get(snake.size() - 1).getX(), snake.get(snake.size() - 1).getY());
        Boolean nott = false;
        Position nextHead = getNextPosition(snake.get(0), direction);
        if (isOutOfMap(nextHead)) {
            nextHead = backToMap(nextHead);
        }
        if (isInList(snake, nextHead) || (fal && isNextWall(snake.get(0), direction))) {
            game.set(false);
        }
        if (game.getValue()) {
            for (int i = snake.size() - 1; i > 0; i--) {
                snake.get(i).setX(snake.get(i - 1).getX());
                snake.get(i).setY(snake.get(i - 1).getY());
            }
            if (isNextFood(snake.get(0), direction, food)) {
                snake = grow(snake, seged);
                nott = true;
            }
            snake.set(0, nextHead);
            if (nott) {
                food.setValue(getRandomEmptyPosition(snake, food));
            }
        }
        return snake;
    }

    /**
     * Adott pozíció és irány alapján megadja a következő lépés pozícióját.
     *
     * @param head a pozíció, ahonnan a következő pozícióra lépünk
     * @param direction az irány, amerre lépni fogunk
     * @return a következő lépés pozíciója
     */
    public Position getNextPosition(Position head, Direction direction) {
        Position newhead = new Position(head);
        switch (direction) {
            case UP:
                newhead.setY(newhead.getY() - 1);
                break;
            case DOWN:
                newhead.setY(newhead.getY() + 1);
                break;
            case RIGHT:
                newhead.setX(newhead.getX() + 1);
                break;
            case LEFT:
                newhead.setX(newhead.getX() - 1);
                break;
        }
        return newhead;
    }

    /**
     * Igazat ad vissza, ha az adott pozíció a pályán kívűlre esik.
     *
     * @param pos egy pozíció amelyet ellenőrízni szeretnénk, hogy a pályán van
     * e
     * @return igaz, ha a pozíció a pályán kívűlre esik
     */
    public Boolean isOutOfMap(Position pos) {
        return pos.getX() > 29 || pos.getX() < 0 || pos.getY() > 29 || pos.getY() < 0;
    }

    /**
     * Adott pozíciót visszahelyezi a pálya ellentéte oldalára.
     *
     * @param head egy pályán kívúlre eső pozíció
     * @return egy a pályára visszahelyezett pozíció
     */
    public Position backToMap(Position head) {
        Position newhead = new Position(head);
        if (head.getX() > 29) {
            newhead.setX(0);
        } else if (head.getX() < 0) {
            newhead.setX(29);
        } else if (head.getY() > 29) {
            newhead.setY(0);
        } else if (head.getY() < 0) {
            newhead.setY(29);
        }
        return newhead;
    }

    /**
     * Igazat ad vissza, ha adott pozíció és irány mellett a következő lépés a
     * pálya falára esik.
     *
     * @param head egy pozíció amelyből a következő lépésre lépünk
     * @param direction egy irány amerre lépünk
     * @return igaz, ha a következő lépés a pálya falára esik
     */
    public Boolean isNextWall(Position head, Direction direction) {
        Position newhead = getNextPosition(head, direction);
        return newhead.getX() == 30 || newhead.getX() == -1 || newhead.getY() == 30 || newhead.getY() == -1;
    }

    /**
     * Igazat ad vissza, ha a megadott pozíció benne van a megadott listában.
     *
     * @param snake a lista amiben a pozíciót keressük
     * @param next a pozíció amit a listában keresünk
     * @return igaz ha a pozíció benne van a listában
     */
    public Boolean isInList(List<Position> snake, Position next) {
        for (int i = 0; i < snake.size(); i++) {
            if (snake.get(i).equals(next)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Igazat ad vissza, ha adott pozíció és irány alapján a következő lépés az
     * étel pozíciójára esik.
     *
     * @param head a pozíció ahonnan lépni fogunk
     * @param direction az irány amerre lépni fogunk
     * @param food az étel pozíciója
     * @return igaz, ha a következő lépés pozíciója az étel pozíciójára esik
     */
    public Boolean isNextFood(Position head, Direction direction, Position food) {
        return getNextPosition(head, direction).equals(food);
    }

    /**
     * A Snake méretét megnöveli egyel és új cellát ad a Snake végéhez.
     *
     * @param snake a Snake celláinak pozícióit tartalmazó lista.
     * @param newlast a Snake új utolsó cellájának pozíciója
     * @return a megnövelt mérető Snake pozícióit tartalmazó lista
     */
    public List<Position> grow(List<Position> snake, Position newlast) {
        List<Position> newsnake = new ArrayList<>(snake);
        newsnake.add(newlast);
        return newsnake;
    }

    /**
     * Visszaadja az olyan üres pozíciókat a pályáról, ahol nincs a Snakenek
     * cellája, vagy nincs étel.
     *
     * @param snake a Snake celláinak pozícióit tartalmazó lista
     * @param food az étel pozíciója
     * @return a pálya üres pozícióit tartalmazó lista
     */
    public List<Position> getEmptyPositions(List<Position> snake, Position food) {
        List<Position> empty = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                Position seged = new Position(i, j);
                if (!seged.equals(food) && !isInList(snake, seged)) {
                    empty.add(seged);
                }
            }
        }

        return empty;
    }

    /**
     * Visszaad egy véletlenszerű üres pozíciót a pályán.
     *
     * @param snake a Snake celláinak pozícióit tartalmazó lista.
     * @param food az étel pozíciója
     * @return egy véletlenszerű üres pozíció
     */
    public Position getRandomEmptyPosition(List<Position> snake, Position food) {
        Position newfood;
        Random rand = new Random();
        List<Position> empty = getEmptyPositions(snake, food);
        newfood = empty.get(rand.nextInt(empty.size()));
        return newfood;
    }

    /**
     * A Snake mérete alapján visszaadja az elért pontszámot. A Snake kezdő
     * mérete 3, így az elért pontszám a lista mérete mínusz 3.
     *
     * @param list a Snake celláinak pozícióit tartalmazó lista
     * @return a Snake mérete alapján számolt pontszám
     */
    public int countScore(List<Position> list) {
        return list.size() - 3;
    }

}
