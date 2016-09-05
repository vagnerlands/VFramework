package lobster.heroquestproj;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import lobster.heroquestproj.Renders.CSprite;
import lobster.heroquestproj.Renders.CSpriteBase;
import lobster.heroquestproj.Renders.CSpriteFilm;
import lobster.heroquestproj.VFramework.CSharedUtils;
import lobster.heroquestproj.VFramework.CTextureCollection;
import lobster.heroquestproj.VFramework.EFonts;
import lobster.heroquestproj.VFramework.CFontCollection;
import lobster.heroquestproj.VFramework.ETexture;

/**
 * Created by Vagner on 8/14/2016.
 */
public class WelcomeScreen extends Screen {
    private int colorAltRGB = 0x0;
    private Handler mHandler;
    Bitmap backgroundScreen;
    Rect backgroundRect = new Rect(); // generic rect for scaling
    List<CSpriteBase> mObjectList = new ArrayList();
    // relative position in the screen
    // left-right 1/4
    // up-bottom 3/4
    // creates a start animated actor
    CSprite mStartButton = new CSprite(ETexture.START_BUTTON, 0.25f, 0.75f);

    // creates a exit animated button
    CSpriteFilm mExitButton = new CSpriteFilm(ETexture.SPRITE_STEPS, 0.75f, 0.75f, 10, 50, 72, 5);

    // creates a paladin animated actor
    CSpriteFilm mPaladin = new CSpriteFilm(ETexture.SPRITE_PALADIN, 0.50f, 0.50f, 6, (int)(624/6), 151, 7);

    // object for drawing purposes
    Paint mObjectDrawer = new Paint();

    public WelcomeScreen() {
        backgroundScreen = CTextureCollection.instance().getTexture(ETexture.WELCOME_SCREEN_BACKGROUND);
        // some specific assignings
        mStartButton.mLabel = "PLAY";
        //mStartButton.setScaleFactor(1.0f);
        mExitButton.mLabel = "EXIT";
        mExitButton.setScaleFactor(3.0f);

        // add all created objects to this list
        // this will be used furthermore for touch detection
        mObjectList.add(mStartButton);
        mObjectList.add(mExitButton);
        mObjectList.add(mPaladin);

        mHandler = new Handler();
    }

    @Override
    public void update(View v) {
        // nothing to update
    }
    // debug area (begin)
    static int stCounter = 0;
    static float factorScal = 1.0f;
    // debug area (end)
    @Override
    public void draw(Canvas c, View v) {
        // for performance sake - declare width and height
        int width = CSharedUtils.instance().getmSurfaceView().getWidth();
        int height = CSharedUtils.instance().getmSurfaceView().getHeight();
        // draw the screen
        // TODO: make this less complicated
        backgroundRect.set(0, 0, width, height);
        c.drawBitmap(backgroundScreen, null, backgroundRect, mObjectDrawer);

        // draws the start button
        //mStartButton.draw(c);
        // draws the exit button (animated)
        //if ((CSharedUtils.instance().getmHeartBit() % 3) == 0)
        //{
        //    factorScal += 0.02f;
        //    if (factorScal > 4.0f) factorScal = 1.0f;
        //}
        //mExitButton.setScaleFactor(factorScal);
        //mExitButton.draw(c);

        //mPaladin.draw(c);
        for (int i = 0; i < mObjectList.size() ; i++) {
            mObjectList.get(i).draw(c);
        }
        // version/copyright line
        colorAltRGB += 0x1;
        if (++colorAltRGB > 0xffffff) colorAltRGB -= 0xffffff;
        // randomizing color
        mObjectDrawer.setColor(Color.rgb(colorAltRGB & 0xff, (colorAltRGB >> 2) & 0xff, (colorAltRGB >> 3) & 0xff));

        mObjectDrawer.setTextSize(72);
        mObjectDrawer.setTypeface(CFontCollection.useFont(EFonts.IMMORTAL));
        String msg = "v"+BuildConfig.VERSION_NAME;
        int xTextEnd = (int)(width*.99f);
        c.drawText(msg, xTextEnd - mObjectDrawer.measureText(msg), height - 80, mObjectDrawer);
        int w1 = backgroundRect.width();
        msg = "Vagner Landskron";
        //msg = act.getResources().getText(R.string.app_autor).toString();
        c.drawText(msg, xTextEnd - mObjectDrawer.measureText(msg), height - 40, mObjectDrawer);
    }

    @Override
    public boolean onTouch(MotionEvent e) {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                //act.startGame();
                Log.d(MainActivity.LOG_ID, "animation10");
            }
        }, 600);

        for (int i = 0; i < mObjectList.size() ; i++) {
            if (mObjectList.get(i).contains(e.getX(), e.getY())) {
                mObjectList.get(i).startBouncing(0.5f, 1.0f, 1200);
            }
        }

        // we don't care about followup events in this screen
        return false;
    }


}
