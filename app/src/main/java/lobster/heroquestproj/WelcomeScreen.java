package lobster.heroquestproj;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lobster.heroquestproj.Renders.CSprite;
import lobster.heroquestproj.Renders.CSpriteBase;
import lobster.heroquestproj.VFramework.CFontCollection;
import lobster.heroquestproj.VFramework.CSharedUtils;
import lobster.heroquestproj.VFramework.CSoundCollection;
import lobster.heroquestproj.VFramework.CTextureCollection;
import lobster.heroquestproj.VFramework.EFonts;
import lobster.heroquestproj.VFramework.ESounds;
import lobster.heroquestproj.VFramework.ETexture;

/**
 * Created by Vagner on 8/14/2016.
 */
public class WelcomeScreen extends Screen {
    private int colorAltRGB = 0x0;
    //private Handler mHandler;
    Bitmap backgroundScreen;
    Rect backgroundRect = new Rect(); // generic rect for scaling
    // object for drawing purposes
    Paint mObjectDrawer = new Paint();
    List<CSpriteBase> mObjectList = new ArrayList();
    // relative position in the screen
    // left-right 1/4
    // up-bottom 3/4
    // creates a start animated actor
    CSprite mStartButton = new CSprite(ETexture.START_BUTTON, 0.25f, 0.75f);
    // creates a start animated actor
    CSprite mSelectedButton = new CSprite(ETexture.START_BUTTON_BACKGROUND, 0.25f, 0.75f);

    // creates a exit animated button
    CSprite mExitButton = new CSprite(ETexture.MENU_EXIT_DOOR, 0.75f, 0.75f);

    // creates a paladin animated actor
    //CSpriteFilm mPaladin = new CSpriteFilm(ETexture.SPRITE_PALADIN, 0.50f, 0.50f, 6, (int)(624/6), 151, 7);

    public WelcomeScreen() {
        backgroundScreen = CTextureCollection.instance().getTexture(ETexture.WELCOME_SCREEN_BACKGROUND);
        // START button initialization
        mStartButton.mLabel = "PLAY";
        mStartButton.mTouchEvent = (new Runnable() {
            @Override
            public void run() {
                mStartButton.startBouncing(1.1f, 1.0f, 2200, 0);
                CSoundCollection.instance().playSound(ESounds.PLAY_BUTTON_PRESSED);
                mSelectedButton.setVisibility(true); // makes it visible again

                // adds an event to the end of the animation - enters to the "Select your Character" Screen
                mStartButton.mEndAnimationEvent = (new Runnable() {
                    @Override
                    public void run() {
                        mSelectedButton.setVisibility(false); // makes it invisble again
                        // set a new screen to be displayed
                        CSharedUtils.instance().setCurrentScreenView(new CharacterSelectScreen());
                    }
                });
            }
        });

        // SELECTED button initialization
        mSelectedButton.setVisibility(false); // invisible at the beggining
        mSelectedButton.setScaleFactor(2f);


        // EXIT button initialization
        mExitButton.mLabel = "EXIT";
        mExitButton.mTouchEvent = (new Runnable() {
            @Override
            public void run() {
                mExitButton.startBouncing(1.1f, 1.0f, 2200, 0);
                CSoundCollection.instance().playSound(ESounds.GOODBYE_FEMALE);
                mSelectedButton.setVisibility(true); // makes it visible again
                // adds an event to the end of the animation - to finish this application
                mExitButton.mEndAnimationEvent  = (new Runnable() {
                    @Override
                    public void run() {
                        mSelectedButton.setVisibility(false); // makes it invisble again
                        System.exit(1);
                    }
                });
            }
        });

        // add all created objects to this list
        // this will be used furthermore for touch detection
        mObjectList.add(mStartButton);
        mObjectList.add(mExitButton);
        //mObjectList.add(mPaladin);

        //mHandler = new Handler();
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

        // draws background of this window
        c.drawBitmap(backgroundScreen, null, backgroundRect, mObjectDrawer);

        // draws each object according to the insertion order
        for (int i = 0; i < mObjectList.size() ; i++) {
            mObjectList.get(i).draw(c);
        }

        // selected button - if applicable
        mSelectedButton.draw(c);

        // version/copyright line
        colorAltRGB += 0x1;
        if (++colorAltRGB > 0xffffff) colorAltRGB -= 0xffffff;
        // randomizing color
        mObjectDrawer.setColor(Color.rgb(colorAltRGB & 0xff, (colorAltRGB >> 2) & 0xff, (colorAltRGB >> 3) & 0xff));

        mObjectDrawer.setTextSize(72);
        mObjectDrawer.setTypeface(CFontCollection.useFont(EFonts.IMMORTAL));
        String msg = "v" + BuildConfig.VERSION_NAME;
        int xTextEnd = (int)(width*.99f);
        c.drawText(msg, xTextEnd - mObjectDrawer.measureText(msg), height - 80, mObjectDrawer);
        msg = "Vagner Landskron";
        c.drawText(msg, xTextEnd - mObjectDrawer.measureText(msg), height - 40, mObjectDrawer);
    }

    @Override
    public boolean onTouch(MotionEvent e) {
        //mHandler.postDelayed(new Runnable() {
        //    @Override
        //    public void run() {
        //        //act.startGame();
        //        Log.d(MainActivity.LOG_ID, "animation10");
        //    }
        //}, 600);
        for (int i = 0; i < mObjectList.size() ; i++) {
            if (mObjectList.get(i).contains(e.getX(), e.getY())) {

                if (mObjectList.get(i).mTouchEvent != null) {
                    mObjectList.get(i).onClickEvent();
                    // adds nice effect for selection
                    // bouncing and fade effect
                    mSelectedButton.setRelativeX(mObjectList.get(i).getRelativeX());
                    mSelectedButton.setRelativeY(mObjectList.get(i).getRelativeY());
                    mSelectedButton.startFadeEffect(200, 0, 3500);
                    mSelectedButton.startBouncing(2.0f, 0.0f, 3500, 0);
                }
            }
        }

        // we don't care about followup events in this screen
        return false;
    }


}
