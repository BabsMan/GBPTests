package com.graemegrier.gametests;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.graemegrier.gametests.objects.sprite.Sprite;
import com.graemegrier.gametests.objects.sprite.SpriteBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Graeme Grier on 07/03/2016.
 *
 * GameView houses UI elements of the "Game"
 */
public class GameView extends SurfaceView implements Runnable {

    private static final String TAG = GameView.class.getSimpleName();
    private static final int FRAME_LENGTH_MIL = 100;

    Context context;

    // The Game thread.
    Thread gameThread = null;

    SurfaceHolder surfaceHolder;

    volatile boolean playing;

    // A Canvas and a Paint object
    Canvas canvas;
    Paint paint;

    long fps;

    Sprite sprite1;
    Sprite sprite2;
    boolean isMoving;
    float walkSpeedPerSecond = 150;  // Walk at 150 pixels per second
    private int frameWidth = 52;
    private int frameHeight = 64;
    // How many frames are there on the sprite sheet?
    private int frameCountHorizontal = 2;
    private int frameCountVertical = 4;

    // Start at the first frame - where else?
    private int currentFrame = 0;

    // What time was it when we last changed frames
    private long lastFrameChangeTime = 0;

    // A rectangle to define an area of the
    // sprite sheet that represents 1 frame
    private Rect frameToDraw = new Rect(
            0, 0,
            frameWidth,
            frameHeight);

    // A rect that defines an area of the screen
    // on which to draw
    RectF whereToDraw = new RectF(
            0, 0,
            frameWidth,
            frameHeight);

    List<Sprite> sprites = new ArrayList<>();
    //AnimatedSprite testAnimatedSprite;

    public GameView(final Context context) {
        super(context);
        this.context = context;
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initGameView();

    }
    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initGameView();
    }

    private void initGameView() {

        surfaceHolder = getHolder();
        paint = new Paint();

        sprite1 = new Sprite();
        sprite1.setSpriteBitmap(new SpriteBitmap(this.getResources(), R.drawable.ashwalklr,
                frameWidth, frameHeight, frameCountHorizontal, frameCountVertical));
        sprite1.getSpriteBitmap().setFrameToDraw(frameToDraw);
        sprite1.setXPos(150);
        sprite1.setYPos(200);
        sprites.add(sprite1);

        sprite2 = new Sprite();
        sprite2.setSpriteBitmap(new SpriteBitmap(this.getResources(), R.drawable.ashwalklr,
                frameWidth, frameHeight, frameCountHorizontal, frameCountVertical));
        sprite2.getSpriteBitmap().setFrameToDraw(frameToDraw);
        sprite2.setXPos(50);
        sprite2.setYPos(0);
        sprites.add(sprite2);
    }

    private void loopGame() {
        while (playing) {
            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // Update the frame
            update();

            // Draw the frame
            draw();

            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame > 0) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    /**
     * Updates the objects on the screen on a game loop.
     *
     * Will respond to events that occur in game logic in time.
     */
    public void update() {

        for (Sprite sprite : sprites) {
            sprite.update(walkSpeedPerSecond, fps);
        }

        if(isMoving){
            sprite2.setXPos(sprite2.getXPos() + (walkSpeedPerSecond/fps));
        }
    }

    public void getCurrentFrame(Sprite sprite) {

            long time = System.currentTimeMillis();
            if (sprite.isMoving()) {
                if (time > lastFrameChangeTime + FRAME_LENGTH_MIL) {
                    lastFrameChangeTime = time;
                    currentFrame++;
                    if (currentFrame >= frameCountHorizontal) {
                        currentFrame = 0;
                    }
                }
            }

            sprite.getSpriteBitmap().updateFrameToDraw(currentFrame);
    }

    /**
     * Draws the newly updated GameView to the screen.
     */
    public void draw() {

        // Make sure our drawing surface is valid or we crash
        if (surfaceHolder.getSurface().isValid()) {
            // Lock the canvas, ready to draw.
            // Make the drawing surface our canvas object.
            canvas = surfaceHolder.lockCanvas();
            // Draw the background color.
            canvas.drawColor(context.getResources().getColor(R.color.gbLightGray));
            //// Choose the brush color for drawing.
            //paint.setColor(Color.argb(255,  249, 129, 0));
            //// Make the text a bit bigger.
            //paint.setTextSize(45);
            //// Display the current fps on the screen.
            //canvas.drawText("FPS:" + fps, 20, 40, paint);

            drawSprites(canvas);

            // Draw everything to the screen
            // and unlock the drawing surface.
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Loop through the Sprite list and draw each in its appropriate position.
     */
    private void drawSprites(final Canvas canvas) {

        for (Sprite sprite : sprites) {
            whereToDraw.set((int) sprite.getXPos(), (int) sprite.getYPos(),
                    (int) sprite.getXPos() + frameWidth, (int) sprite.getYPos() + frameHeight);
            getCurrentFrame(sprite);

            canvas.drawBitmap(sprite.getSpriteBitmap().getBitmapImage(),
                    sprite.getSpriteBitmap().getFrameToDraw(), whereToDraw, paint);
        }
    }

    // If the Activity is paused/stopped/shutdown our thread.
    public void onPause() {
        playing = false;
        try {

            gameThread.join();
        } catch (InterruptedException e) {
            Log.e(TAG, "Error joining thread: " + e);
        }

    }

    // If the Activity is started then start our thread.
    public void onResume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Function that responds to the button "up" press events.
     * @param pressed - Boolean value denoting where the button has been pressed or not.
     */
    public void onUpPressed(final boolean pressed) {
        sprite1.setMovingUp(pressed);
        sprite2.setMovingUp(pressed);
    }

    /**
     * Function that responds to the button "down" press events.
     * @param pressed - Boolean value denoting where the button has been pressed or not.
     */
    public void onDownPressed(final boolean pressed) {
        sprite1.setMovingDown(pressed);
        sprite2.setMovingDown(pressed);
    }

    /**
     * Function that responds to the button "left" press events.
     * @param pressed - Boolean value denoting where the button has been pressed or not.
     */
    public void onLeftPressed(final boolean pressed) {
        sprite1.setMovingLeft(pressed);
        sprite2.setMovingLeft(pressed);
    }

    /**
     * Function that responds to the button "right" press events.
     * @param pressed - Boolean value denoting where the button has been pressed or not.
     */
    public void onRightPressed(final boolean pressed) {
        sprite1.setMovingRight(pressed);
        sprite2.setMovingRight(pressed);
    }

    /* Overrides for Runnable begin here */
    @Override
    public void run() {
        loopGame();
    }
    /* Overrides for Runnable end here */
}
