package lobster.heroquestproj.Renders;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import lobster.heroquestproj.VFramework.CSharedUtils;
import lobster.heroquestproj.VFramework.CTextureCollection;
import lobster.heroquestproj.VFramework.EFonts;
import lobster.heroquestproj.VFramework.CFontCollection;
import lobster.heroquestproj.VFramework.ETexture;

/**
 * Created by Vagner on 8/15/2016.
 *
 * this is a rectangle which can be animated using the animator class from android
 */
public class CSprite extends CSpriteBase {


    public CSprite() {
        super();
    }

    public CSprite(ETexture text) {
        super();
        mTexture = CTextureCollection.instance().getTexture(text);
    }

    public CSprite(ETexture text, float left, float top, float right, float bottom) {
        super(text, left, top, right, bottom);
        mTexture = CTextureCollection.instance().getTexture(text);
    }

    public CSprite(RectF r) {
        super(r);
    }

    public CSprite(Rect r) {
        super(r);
    }

    public CSprite(ETexture text, float relativeX, float relativeY) {
        super();
        mTexture = CTextureCollection.instance().getTexture(text);
        mRelativeX = relativeX;
        mRelativeY = relativeY;
    }


    public void setTypeface(EFonts fontOpt) {
        mTextFont = CFontCollection.useFont(EFonts.COMICS);
    }

    public void draw(Canvas c) {
        // if this layer isn't displayed - do nothing
        if (mVisible == false) return;
        // updates the screen dimensions
        // always assuming that the given coordinates refer to the top left corner
        if ((mFixedX > 0.0f) && (mFixedY > 0.0f)) {
            this.left = mFixedX;
            this.top = mFixedY + (mTexture.getHeight() * mScaleY) ;
            this.right = mFixedX + (mTexture.getWidth() * mScaleX);
            this.bottom = mFixedY;
        } else {
            setScreenDimensions(CSharedUtils.instance().getmSurfaceView().getWidth(), CSharedUtils.instance().getmSurfaceView().getHeight());
        }

        // draw the screen
        this.set(this.left, this.bottom, this.right, this.top);

        // actually draws this object
        c.drawBitmap(mTexture, null, this, mObjectDrawer);

        // if there is a label - draws it on the screen
        if (mLabel.length() > 0) {
            printLabel(c);
        }
    }
}
