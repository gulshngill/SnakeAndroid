package com.gulshngill.snake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gulshngill.snake.engine.GameEngine;
import com.gulshngill.snake.views.SnakeView;

public class MainActivity extends AppCompatActivity {

    private GameEngine gameEnigne;
    private SnakeView snakeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameEnigne = new GameEngine();
        gameEnigne.initGame();

        snakeView = (SnakeView)findViewById(R.id.snakeView);
        snakeView.setSnakeViewMap(gameEnigne.getMap());
        snakeView.invalidate();
    }
}
