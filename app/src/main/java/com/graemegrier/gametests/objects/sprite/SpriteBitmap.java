package com.graemegrier.gametests.objects.sprite;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Class that holds image information for a {@link Sprite} object.
 */
public class SpriteBitmap {

    private Bitmap spriteBitmap;
    private int frameWidth;
    private int frameHeight;
    private int frameCountHorizontal;
    private int frameCountVertical;

    // A rectangle to define an area of the
    // sprite sheet that represents 1 frame
    private Rect frameToDraw = new Rect(
            0, 0,
            frameWidth,
            frameHeight);

    public SpriteBitmap(final Resources resources, final int id,
                        final int frameWidth, final int frameHeight, final int frameCount) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        frameCountHorizontal = frameCount;
        frameCountVertical = frameCount;
        loadScaledSprite(resources, id);
    }
    public SpriteBitmap(final Resources resources, final int id,
                        final int frameWidth, final int frameHeight,
                        final int frameCountHorizontal, final int frameCountVertical) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameCountHorizontal = frameCountHorizontal;
        this.frameCountVertical = frameCountVertical;
        loadScaledSprite(resources, id);
    }

    public void loadScaledSprite(final Resources resources, final int id) {
        spriteBitmap = BitmapFactory.decodeResource(resources, id);
        spriteBitmap = Bitmap.createScaledBitmap(spriteBitmap, frameWidth * frameCountHorizontal,
                frameHeight * frameCountVertical, false);
    }

    public Bitmap getBitmapImage() {
        return spriteBitmap;
    }

    public Rect getFrameToDraw() {
        return frameToDraw;
    }

    public void setFrameToDraw(final Rect frameToDraw) {
        this.frameToDraw = frameToDraw;
    }

    public void updateFrameToDraw(final int currentFrame) {
        // Update the left and right values of the source of the next
        // frame on the sprite-sheet.
        frameToDraw.left = currentFrame * frameWidth;
        frameToDraw.right = frameToDraw.left + frameWidth;
    }

    public void navigateSpriteSheet(final int pos) {
        if (frameToDraw.top != pos * frameHeight) {
            frameToDraw.top = pos * frameHeight;
            frameToDraw.bottom = frameToDraw.top + frameHeight;
        }
    }
}
