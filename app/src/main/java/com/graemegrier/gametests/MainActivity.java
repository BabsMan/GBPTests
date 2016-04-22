package com.graemegrier.gametests;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements ControllerView.Callbacks {

    // Our object that will hold the view and
    // the sprite sheet animation logic
    GameView gameView;
    ControllerView controllerView;
    LinearLayout controllerViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize gameView and set it as the view
        //gameView = new GameView(this);
        setContentView(R.layout.activity_main);
        gameView = (GameView) findViewById(R.id.game_view);
        controllerViewLayout = (LinearLayout) findViewById(R.id.controller_view);
        controllerView = new ControllerView(this, controllerViewLayout);
    }

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        gameView.onResume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        gameView.onPause();
    }

    @Override
    public void onUpButtonPressed(final boolean pressed) {
        gameView.onUpPressed(pressed);
    }

    @Override
    public void onDownButtonPressed(final boolean pressed) {
        gameView.onDownPressed(pressed);
    }

    @Override
    public void onLeftButtonPressed(final boolean pressed) {
        gameView.onLeftPressed(pressed);
    }

    @Override
    public void onRightButtonPressed(final boolean pressed) {
        gameView.onRightPressed(pressed);
    }
}