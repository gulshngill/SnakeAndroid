package com.gulshngill.snake.engine;

import com.gulshngill.snake.classes.Coordinate;
import com.gulshngill.snake.enums.TileType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gulsh on 20/5/2017.
 */

public class GameEngine {
    public static final int GAME_WIDTH = 28;
    public static final int GAME_HEIGHT = 42;

    private List<Coordinate> walls = new ArrayList<>();


    public GameEngine() {

    }

    public void initGame() {
        AddWalls();
    }

    public TileType[][] getMap() {
        TileType[][] map = new TileType[GAME_WIDTH][GAME_HEIGHT];

        for(int x = 0; x < GAME_WIDTH; x++) {
            for(int y = 0; y < GAME_HEIGHT; y++) {
                map[x][y] = TileType.Nothing;
            }
        }

        for (Coordinate wall: walls) {
            map[wall.getX()][wall.getY()] = TileType.Wall;

        }

        return map;
    }

    public void AddWalls() {
        //add top/bottom walls
        for (int x = 0; x < GAME_WIDTH; x++) {
            walls.add(new Coordinate(x,0));
            walls.add(new Coordinate(x, GAME_HEIGHT-1));
        }

        //add left/right walls
        for (int y = 0; y < GAME_HEIGHT; y++) {
            walls.add(new Coordinate(0,y));
            walls.add(new Coordinate(GAME_WIDTH-1, 0));
        }
    }
}
