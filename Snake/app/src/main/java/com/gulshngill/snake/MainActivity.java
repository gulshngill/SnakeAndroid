package com.gulshngill.snake;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.gulshngill.snake.engine.GameEngine;
import com.gulshngill.snake.enums.GameState;
import com.gulshngill.snake.views.SnakeView;

public class MainActivity extends AppCompatActivity {

    private GameEngine gameEnigne;
    private SnakeView snakeView;
    private final Handler handler = new Handler();
    private final long updateDelay = 125;

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

    private void startUpdateHandler() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameEnigne.Update();

                if(gameEnigne.getCurrentGameState() == GameState.Running) {
                    handler.postDelayed(this,updateDelay);
                }

                if(gameEnigne.getCurrentGameState() == GameState.Lost) {
                    OnGameLost();
                }

                snakeView.setSnakeViewMap(gameEnigne.getMap());
                snakeView.invalidate();
            }
        }, updateDelay);
    }

    private void OnGameLost(){
        Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
    }
}
