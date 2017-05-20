package com.gulshngill.snake;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.gulshngill.snake.engine.GameEngine;
import com.gulshngill.snake.enums.Direction;
import com.gulshngill.snake.enums.GameState;
import com.gulshngill.snake.views.SnakeView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private GameEngine gameEnigne;
    private SnakeView snakeView;
    private final Handler handler = new Handler();
    private final long updateDelay = 125;

    private float prevX, prevY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameEnigne = new GameEngine();
        gameEnigne.initGame();

        snakeView = (SnakeView)findViewById(R.id.snakeView);
        snakeView.setOnTouchListener(this);


        startUpdateHandler();
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();
                break;

            case MotionEvent.ACTION_UP :
                float newX = event.getX();
                float newY = event.getY();


                //Calculate swipe
                if(Math.abs( newX - prevX) > Math.abs(newY - prevY)) {
                    // Left/Right
                    if(newX > prevX) {
                        //Right
                        gameEnigne.UpdateDirection(Direction.East);
                    } else {
                        //left
                        gameEnigne.UpdateDirection(Direction.West);
                    }
                } else {
                    // up/down
                    if(newY > prevY) {
                        //up
                        gameEnigne.UpdateDirection(Direction.South);
                    } else {
                        //down
                        gameEnigne.UpdateDirection(Direction.North);
                    }
                }

                break;
        }

        return true;
    }
}
