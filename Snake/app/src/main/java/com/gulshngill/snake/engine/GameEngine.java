package com.gulshngill.snake.engine;

import android.graphics.Path;

import com.gulshngill.snake.classes.Coordinate;
import com.gulshngill.snake.enums.Direction;
import com.gulshngill.snake.enums.GameState;
import com.gulshngill.snake.enums.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by gulsh on 20/5/2017.
 */

public class GameEngine {
    public static final int GAME_WIDTH = 28;
    public static final int GAME_HEIGHT = 42;

    private int score = 0;

    private List<Coordinate> walls = new ArrayList<>();
    private List<Coordinate> snake = new ArrayList<>();
    private List<Coordinate> food = new ArrayList<>();

    private Random random = new Random();
    private boolean increaseTail = false;

    private Direction currentDirection = Direction.East;

    private GameState currentStateGame = GameState.Running;

    private Coordinate getSnakeHead() {
        return snake.get(0);
    }


    public GameEngine() {

    }

    public int getScore() {
        return score;
    }

    public void initGame() {

        AddSnake();
        AddWalls();
        AddFood();
    }

    public void UpdateDirection(Direction newDirection) {
        if (Math.abs(newDirection.ordinal() - currentDirection.ordinal()) % 2 == 1) {
            currentDirection = newDirection;
        }
    }

    public void Update() {
        //Update snake direction
        switch (currentDirection) {
            case North:
                UpdateSnake(0,-1);
                break;
            case East:
                UpdateSnake(1,0);
                break;
            case South:
                UpdateSnake(0,1);
                break;
            case West:
                UpdateSnake(-1,0);
                break;
        }

        //Check wall collision
        for( Coordinate w : walls) {
            if(snake.get(0).equals(w)) {
                currentStateGame = GameState.Lost;
            }
        }

        //Check self-collision
        for ( int i = 1; i < snake.size(); i++) {
            if( getSnakeHead().equals(snake.get(i))) {
                currentStateGame = GameState.Lost;
                return;
            }
        }

        //Check food
        Coordinate foodToRemove = null;

        for( Coordinate foods: food ) {
            if( getSnakeHead().equals(foods)) {
                foodToRemove = foods;
                increaseTail = true;
                score +=1;
            }
        }

        if(foodToRemove != null) {
            food.remove(foodToRemove);
            AddFood();
        }

    }

    private void UpdateSnake(int x, int y) {
        int newX = snake.get(snake.size()-1).getX();
        int newY = snake.get(snake.size()-1).getY();

        for(int i = snake.size() -1; i>0; i--) {
            snake.get(i).setX(snake.get(i-1).getX());
            snake.get(i).setY(snake.get(i-1).getY());
        }

        if(increaseTail) {
            snake.add(new Coordinate(newX,newY));
            increaseTail = false;
        }

        snake.get(0).setX( snake.get(0).getX() + x);
        snake.get(0).setY(snake.get(0).getY() + y);

    }

    private void AddSnake() {
        snake.clear();

        snake.add(new Coordinate(7,7));
        snake.add(new Coordinate(6,7));
        snake.add(new Coordinate(5,7));
        snake.add(new Coordinate(4,7));
        snake.add(new Coordinate(3,7));
        snake.add(new Coordinate(2,7));

    }

    public TileType[][] getMap() {
        TileType[][] map = new TileType[GAME_WIDTH][GAME_HEIGHT];

        //set all tiles to nothing
        for(int x = 0; x < GAME_WIDTH; x++) {
            for(int y = 0; y < GAME_HEIGHT; y++) {
                map[x][y] = TileType.Nothing;
            }
        }

        for(Coordinate s: snake) {
            map[s.getX()][s.getY()] = TileType.SnakeTail;
        }

        map[snake.get(0).getX()][snake.get(0).getY()] = TileType.SnakeHead;

        //add walls
        for (Coordinate wall: walls) {
            map[wall.getX()][wall.getY()] = TileType.Wall;

        }

        for(Coordinate f: food) {
            map[f.getX()][f.getY()] = TileType.Food;
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
            walls.add(new Coordinate(GAME_WIDTH-1, y));
        }
    }

    public GameState getCurrentGameState() {
        return currentStateGame;
    }

    private void AddFood() {
        Coordinate coordinate = null;

        boolean added = false;

        while (!added) {
            int x = 1 + random.nextInt(GAME_WIDTH - 2);
            int y = 1 + random.nextInt(GAME_HEIGHT - 2);

            coordinate = new Coordinate(x,y);
            boolean collision = false;
            for(Coordinate s: snake) {
                if( s.equals(coordinate)) {
                    collision = true;
                    //break;
                }
            }

            for(Coordinate f: food) {
                if( f.equals(coordinate) ) {
                    collision = true;
                    //break;
                }
            }

            for(Coordinate s: snake) {
                if( s.equals(coordinate) ) {
                    collision = true;
                    //break;
                }
            }

            added = !collision;
        }

        food.add(coordinate);

    }
}
