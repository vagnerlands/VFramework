package lobster.heroquestproj.Renders;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import lobster.heroquestproj.VFramework.CFontCollection;
import lobster.heroquestproj.VFramework.CSharedUtils;
import lobster.heroquestproj.VFramework.EFonts;

/**
 * Created by Vagner on 9/10/2016.
 */
public class CScreenText {
    // object for actual drawing
    protected Paint mObjectDrawer = new Paint();
    // label
    // all labels are center aligned for now
    public String mLabel = "";
    // X relative center position of the rectangle
    protected float mRelativeX = 0.0f;
    // Y relative center position of the rectangle
    protected float mRelativeY = 0.0f;
    // fixed position for X and Y
    private float mFixedX = -1f;
    private float mFixedY = -1f;
    // Size of the label in pixels - calculated based on the selected font type multiplied by scalar factor of this same font
    protected float charInPixels = 0.0f;
    // default font - if not specified
    protected EFonts mTextFont = EFonts.COMICS;
    // text size
    protected int mTextSize = 72;
    // default color is yellow
    protected int mTextColor = Color.rgb(0xc0, 0xcf, 0x10);

    public CScreenText(String screenText, float relativeX, float relativeY) {
        super();
        mRelativeX = relativeX;
        mRelativeY = relativeY;
        mLabel = screenText;
    }

    public void setFixedX(float fixedX) { mFixedX = fixedX; }
    public void setFixedY(float fixedY) { mFixedY = fixedY; }

    // updates the text size
    public void setTextSize(int iTextSize) { mTextSize = iTextSize; }
    // updates the text font
    public void setTextFont(EFonts eFonts) { mTextFont = eFonts; }
    // updates the text color
    public void setTextColor(int texColor) { mTextColor = texColor; }

    public void setScreenDimensions(int width, int height) {
        mObjectDrawer.setColor(mTextColor);
        mObjectDrawer.setTextSize(mTextSize);
        charInPixels = mTextSize * CFontCollection.getFontScaleFactor(mTextFont);
        //charInPixels = 50f;
        mObjectDrawer.setTypeface(CFontCollection.useFont(mTextFont));
    }

    public void draw(Canvas c) {
        // update the object dimensions with the current view surface parameters
        setScreenDimensions(CSharedUtils.instance().getmSurfaceView().getWidth(), CSharedUtils.instance().getmSurfaceView().getHeight());
        if ((mFixedX > 0f) && (mFixedY > 0f)) {
            printLabelFixed(c);
        } else {
            printLabelRelative(c);
        }
    }

    public void printLabelFixed(Canvas c) {
        c.drawText(mLabel, mFixedX, mFixedY + (charInPixels / 2), mObjectDrawer);
    }

    public void printLabelRelative(Canvas c) {
        c.drawText(mLabel, CSharedUtils.instance().getmSurfaceView().getWidth() * mRelativeX, (CSharedUtils.instance().getmSurfaceView().getHeight() * mRelativeY) + (charInPixels/2), mObjectDrawer);
    }
}
