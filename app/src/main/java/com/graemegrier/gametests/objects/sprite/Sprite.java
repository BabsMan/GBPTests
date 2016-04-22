package com.graemegrier.gametests.objects.sprite;

/**
 * Created by Graeme Grier on 07/03/2016.
 *
 * Every drawable asset in the GameView is a Sprite object.
 */
public class Sprite {

    protected SpriteBitmap spriteBitmap;
    protected float xPos;
    protected float yPos;

    protected boolean isMovingUp;
    protected boolean isMovingDown;
    protected boolean isMovingLeft;
    protected boolean isMovingRight;

    public Sprite() {}

    public SpriteBitmap getSpriteBitmap() {
        return spriteBitmap;
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public boolean isMovingRight() {
        return isMovingRight;
    }

    public boolean isMovingUp() {
        return isMovingUp;
    }

    public boolean isMovingDown() {
        return isMovingDown;
    }

    public boolean isMovingLeft() {
        return isMovingLeft;
    }

    public boolean isMoving() {
        return isMovingUp || isMovingDown || isMovingLeft || isMovingRight;
    }

    public void setSpriteBitmap(SpriteBitmap spriteBitmap) {
        this.spriteBitmap = spriteBitmap;
    }

    public void setXPos(final float x) {
        this.xPos = x;
    }

    public void setYPos(final float y) {
        this.yPos = y;
    }

    public void setMovingUp(final boolean movingUp) {
        isMovingUp = movingUp;
    }

    public void setMovingDown(final boolean movingDown) {
        isMovingDown = movingDown;
    }

    public void setMovingLeft(final boolean movingLeft) {
        isMovingLeft = movingLeft;
    }

    public void setMovingRight(final boolean movingRight) {
        isMovingRight = movingRight;
    }

    public void update(final float walkSpeedPerSecond, final long fps) {
        if (isMovingUp()) {
            getSpriteBitmap().navigateSpriteSheet(3);
            setYPos(getYPos() - (walkSpeedPerSecond/fps));
        } else if (isMovingDown()) {
            getSpriteBitmap().navigateSpriteSheet(2);
            setYPos(getYPos() + (walkSpeedPerSecond/fps));
        } else if (isMovingRight()) {
            getSpriteBitmap().navigateSpriteSheet(0);
            setXPos(getXPos() + (walkSpeedPerSecond/fps));
        } else if (isMovingLeft()) {
            getSpriteBitmap().navigateSpriteSheet(1);
            setXPos(getXPos() - (walkSpeedPerSecond/fps));
        }
    }
}
