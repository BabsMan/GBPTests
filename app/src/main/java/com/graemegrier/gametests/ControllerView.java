package com.graemegrier.gametests;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ControllerView {

    private Callbacks callbacks;
    LinearLayout controllerView;

    public ControllerView(Context context, LinearLayout controllerView) {

        try {
            callbacks = (Callbacks) context;
        } catch (ClassCastException e) {
            throw new IllegalStateException((context.getClass().getName()) + "must implement" +
                    Callbacks.class.getName());
        }

        this.controllerView = controllerView;
        initControllerView();
    }

    private void initControllerView() {

        Button upButton = (Button) controllerView.findViewById(R.id.btn_up);
        upButton.setOnTouchListener(touchListener);
        Button downButton = (Button) controllerView.findViewById(R.id.btn_down);
        downButton.setOnTouchListener(touchListener);
        Button leftButton = (Button) controllerView.findViewById(R.id.btn_left);
        leftButton.setOnTouchListener(touchListener);
        Button rightButton = (Button) controllerView.findViewById(R.id.btn_right);
        rightButton.setOnTouchListener(touchListener);
    }

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            boolean pressed = false;

            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                pressed = true;
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                pressed = false;
            }

            if (v.getId() == R.id.btn_up) {
                callbacks.onUpButtonPressed(pressed);
            } else if (v.getId() == R.id.btn_down) {
                callbacks.onDownButtonPressed(pressed);
            } else if (v.getId() == R.id.btn_left) {
                callbacks.onLeftButtonPressed(pressed);
            } else if (v.getId() == R.id.btn_right) {
                callbacks.onRightButtonPressed(pressed);
            }

            return true;
        }
    };

    public interface Callbacks {

        void onUpButtonPressed(final boolean pressed);
        void onDownButtonPressed(final boolean pressed);
        void onLeftButtonPressed(final boolean pressed);
        void onRightButtonPressed(final boolean pressed);
    }
}
