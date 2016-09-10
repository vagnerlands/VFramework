package lobster.heroquestproj.Renders;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lobster.heroquestproj.MainActivity;
import lobster.heroquestproj.VFramework.CSharedUtils;
import lobster.heroquestproj.VFramework.CTextureCollection;
import lobster.heroquestproj.VFramework.ETexture;


/**
 * Created by Vagner on 8/20/2016.
 */
public class CSpriteFilm extends CSpriteBase {

    // width in pixels of the each sprite in this sprite film
    private int mSpriteFrameWidth = 0;
    // height in pixels of the each sprite in this sprite film
    private int mSpriteFrameHeight = 0;
    // total number of frames in the sprite film
    private int mTotalNumOfFrames = 0;
    // frame to be displayed
    private int mCurrentDisplayFrame = 0;
    // how many cycles must wait till the frame switches
    private int mAnimationSpeed = 1;
    // measuring time from the object creation - this way it's possible to know which frame shall be draw
    private long mStartTime = 0;
    // list of frames in a CSpriteFilm
    private List<Bitmap> mBitmapFrames = new ArrayList();

    public CSpriteFilm() {
        super();
    }

    public CSpriteFilm(ETexture text, float left, float top, float right, float bottom) {
        super(text, left, top, right, bottom);
    }

    public CSpriteFilm(RectF r) {
        super(r);
    }

    public CSpriteFilm(Rect r) {
        super(r);
    }

    public CSpriteFilm(ETexture text, float relativeX, float relativeY, int totalOfFrames, int spriteWidth, int spriteHeight, int animationSpeed) {
        super(text);
        mRelativeX = relativeX;
        mRelativeY = relativeY;
        
        mSpriteFrameHeight = spriteHeight;
        mSpriteFrameWidth = spriteWidth;
        mTotalNumOfFrames = totalOfFrames;
        mAnimationSpeed = animationSpeed;

        long initTime = System.nanoTime();
        for (int i = 0 ; i < mTotalNumOfFrames; i++) {

            // based on the current sprite frame - generates a new Bitmap
            Bitmap spriteFrame = Bitmap.createBitmap(mSpriteFrameWidth, mSpriteFrameHeight, Bitmap.Config.ARGB_8888);
            // allocates a temporary buffer with the correct frame size
            int[] pixelBuf = new int[mSpriteFrameWidth * mSpriteFrameHeight];
            // copies this very specific frame from the whole sprite film to pixelBuf
            mTexture.getPixels(pixelBuf, 0, mSpriteFrameWidth, mSpriteFrameWidth * i, 0, mSpriteFrameWidth, mSpriteFrameHeight);
            // sets the pixelBuf to spriteFrame
            spriteFrame.setPixels(pixelBuf, 0, mSpriteFrameWidth, 0, 0, mSpriteFrameWidth, mSpriteFrameHeight);

            // finally adds this new frame to the list of frames
            mBitmapFrames.add(spriteFrame);

        }
        long endTime = System.nanoTime();
        Log.d(MainActivity.LOG_ID, " time " + (long)((endTime - initTime) / 1000));


        mStartTime = System.currentTimeMillis();
    }


    public void setScreenDimensions(int width, int height) {
        this.left = (width * mRelativeX) - (mSpriteFrameWidth * mScaleX) / 2;
        this.right = (width * mRelativeX) + (mSpriteFrameWidth * mScaleX) / 2;
        this.bottom = (height * mRelativeY) - (mSpriteFrameHeight * mScaleY) / 2;
        this.top = (height * mRelativeY) + (mSpriteFrameHeight * mScaleY) / 2;
    }

    public void draw(Canvas c) {
        // if this layer isn't displayed - do nothing
        if (mVisible == false) return;
        // draws the current frame according to the current animation state
        if ((CSharedUtils.instance().getmHeartBit() % mAnimationSpeed) == 0) mCurrentDisplayFrame++;

        if (mCurrentDisplayFrame == mTotalNumOfFrames) mCurrentDisplayFrame = 0;

        // updates the screen dimensions
        // always assuming that the given coordinates refer to the top left corner
        if ((mFixedX > 0.0f) && (mFixedY > 0.0f)) {
            this.left = mFixedX;
            this.top = mFixedY + (mSpriteFrameHeight * mScaleY) ;
            this.right = mFixedX + (mSpriteFrameWidth * mScaleX);
            this.bottom = mFixedY;
        } else {
            setScreenDimensions(CSharedUtils.instance().getmSurfaceView().getWidth(), CSharedUtils.instance().getmSurfaceView().getHeight());
        }

        // draw the screen
        this.set(this.left, this.bottom, this.right, this.top);
        // consumes the drawing
        c.drawBitmap(mBitmapFrames.get(mCurrentDisplayFrame), null, this, mObjectDrawer);

        if (mLabel.length() > 0) {
            printLabel(c);
        }
    }
}
