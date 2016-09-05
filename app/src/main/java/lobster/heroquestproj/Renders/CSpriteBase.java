package lobster.heroquestproj.Renders;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.animation.BounceInterpolator;

import lobster.heroquestproj.VFramework.CFontCollection;
import lobster.heroquestproj.VFramework.CTextureCollection;
import lobster.heroquestproj.VFramework.EFonts;
import lobster.heroquestproj.VFramework.ETexture;

/**
 * Created by Vagner on 9/5/2016.
 */
public abstract class CSpriteBase extends RectF {
    // texture of this rectangle
    public Bitmap mTexture;
    // object for actual drawing
    protected Paint mObjectDrawer = new Paint();
    // label - if applicable
    // all labels are center aligned for now
    public String mLabel = "";
    // X relative center position of the rectangle
    protected float mRelativeX = 0.0f;
    // Y relative center position of the rectangle
    protected float mRelativeY = 0.0f;
    // Size of the label in pixels - calculated based on the selected font type multiplied by scalar factor of this same font
    protected float charInPixels = 0.0f;
    // default font - if not specified
    protected Typeface mTextFont = CFontCollection.useFont(EFonts.COMICS);
    // scale - re-scale the current rectangle - important for animation effects
    protected float mScaleX = 1.0f; // 1.0f means current size
    protected float mScaleY = 1.0f; // 1.0f means current size

    public CSpriteBase() {
        super();
    }

    public CSpriteBase(ETexture text) {
        super();
        mTexture = CTextureCollection.instance().getTexture(text);
    }


    public CSpriteBase(ETexture text, float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
        mTexture = CTextureCollection.instance().getTexture(text);
    }

    public CSpriteBase(RectF r) {
        super(r);
    }

    public CSpriteBase(Rect r) {
        super(r);
    }

    public void printLabel(Canvas c) {
        final int sTextSize = 72;

        mObjectDrawer.setColor(Color.rgb(0xc0, 0xcf, 0x10));
        mObjectDrawer.setTextSize(sTextSize);
        charInPixels = sTextSize * CFontCollection.getFontScaleFactor(EFonts.COMICS);
        mObjectDrawer.setTypeface(mTextFont);
        c.drawText(mLabel, (((this.left + this.right) / 2) - ((mLabel.length() / 2) * charInPixels)), this.bottom + 45, mObjectDrawer);
    }

    public void setTop(float top){
        this.top = top;
    }
    public void setBottom(float bottom){
        this.bottom = bottom;
    }
    public void setRight(float right){
        this.right = right;
    }
    public void setLeft(float left){
        this.left = left;
    }

    public void setScreenDimensions(int width, int height) {
        this.left = (width * mRelativeX) - (mTexture.getWidth() * mScaleX) / 2;
        this.right = (width * mRelativeX) + (mTexture.getWidth() * mScaleX) / 2;
        this.bottom = (height * mRelativeY) - (mTexture.getHeight() * mScaleY) / 2;
        this.top = (height * mRelativeY) + (mTexture.getHeight() * mScaleY) / 2;
    }

    public void setLeft(int leftVal) {
        this.left = leftVal;
    }

    public void setNameY(String yPropertyName) {

    }

    // even though this function exists - you shouldn't use it
    // really time consuming...
    // better to use only for animation purposes
    // if you have to resize something, create a new resized image
    public void setScaleFactor(float scale) {
        mScaleX = scale;
        mScaleY = scale;
    }

    public void setScaleX(float scaleX) {
        mScaleX = scaleX;
    }

    public void setScaleY(float scaleY) {
        mScaleY = scaleY;
    }

    public void startBouncing(float minScale, float finalScale, int duration) {
        //final TileView tileView = TileView.fromXml(CSharedUtils.instance().getmSurfaceView().getContext(), parent);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", minScale, finalScale);
        scaleXAnimator.setInterpolator(new BounceInterpolator());
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", minScale, finalScale);
        scaleYAnimator.setInterpolator(new BounceInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(duration);
        //tileView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animatorSet.start();
    }

    public abstract void draw(Canvas c);

}
