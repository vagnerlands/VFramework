package lobster.heroquestproj;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import lobster.heroquestproj.GameMenu.CMenuClassSelect;
import lobster.heroquestproj.GameMenu.CMenuClassSelectItem;
import lobster.heroquestproj.Renders.CScreenText;
import lobster.heroquestproj.Renders.CSprite;
import lobster.heroquestproj.Renders.CSpriteFilm;
import lobster.heroquestproj.VFramework.CSharedUtils;
import lobster.heroquestproj.VFramework.CTextureCollection;
import lobster.heroquestproj.VFramework.EFonts;
import lobster.heroquestproj.VFramework.ETexture;

/**
 * Created by Vagner on 9/10/2016.
 */
public class CharacterSelectScreen extends Screen {
    //private Handler mHandler;
    Bitmap backgroundScreen;
    Rect backgroundRect = new Rect(); // generic rect for scaling
    // object for drawing purposes
    Paint mObjectDrawer = new Paint();

    CSpriteFilm mLeftFire = new CSpriteFilm(ETexture.CAMPFIRE_ANIMATED, 0.27f, 0.1f, 5, (int)(160/5), 64, 7);
    CSpriteFilm mRightFire = new CSpriteFilm(ETexture.CAMPFIRE_ANIMATED, 0.7f, 0.1f, 5, (int)(160/5), 64, 7);

    CSpriteFilm mPaladinSprite = new CSpriteFilm(ETexture.SPRITE_PALADIN, 0.38f, 0.38f, 6, (int)(624/6), 151, 12);
    CSprite mPaladinPortrait = new CSprite(ETexture.PALADIN_PORTRAIT, 0.25f, 0.33f);

    CSpriteFilm mElfSprite = new CSpriteFilm(ETexture.SPRITE_ELF, 0.38f, 0.72f, 7, (int)(644/7), 151, 12);
    CSprite mElfPortrait = new CSprite(ETexture.ELF_PORTRAIT, 0.25f, 0.65f);

    CScreenText mPaladinAttribClass = new CScreenText("Paladin", 0.45f, 0.26f);
    CScreenText mPaladinAttribHealth = new CScreenText("Health:", 0.45f, 0.33f);
    CScreenText mPaladinAttribStrength = new CScreenText("Strength:", 0.45f, 0.4f);

    CScreenText mElfAttribClass = new CScreenText("Elf", 0.45f, 0.57f);
    CScreenText mElfAttribHealth = new CScreenText("Health:", 0.45f, 0.65f);
    CScreenText mElfAttribStrength = new CScreenText("Strength:", 0.45f, 0.73f);

    CScreenText mCharacterSelectionTitle = new CScreenText("Select your path:", 0.3f, 0.1f);

    // organize the menu with 2 rows by 2 cols
    CMenuClassSelect mMenuClassSelect = new CMenuClassSelect(2, 2);



    public CharacterSelectScreen() {

        mMenuClassSelect.addItem(new CMenuClassSelectItem(
                new CSprite(ETexture.ELF_PORTRAIT, 0.25f, 0.65f), // portrait
                new CSpriteFilm(ETexture.SPRITE_ELF, 0.38f, 0.72f, 7, (int)(644/7), 151, 8), // sprite
                "Elf", // class name
                4, // strength
                6, // health
                8 // magic
        ));
        mMenuClassSelect.addItem(new CMenuClassSelectItem(
                new CSprite(ETexture.PALADIN_PORTRAIT, 0.25f, 0.33f), // portrait
                new CSpriteFilm(ETexture.SPRITE_PALADIN, 0.38f, 0.38f, 6, (int)(624/6), 151, 12), // sprite
                "Paladin", // class name
                8, // strength
                6, // health
                4 // magic
        ));
        mMenuClassSelect.addItem(new CMenuClassSelectItem(
                new CSprite(ETexture.MAGE_PORTRAIT, 0.25f, 0.33f), // portrait
                new CSpriteFilm(ETexture.SPRITE_MAGE, 0.38f, 0.38f, 8, (int)(336/8), 151, 12), // sprite
                "Mage", // class name
                4, // strength
                4, // health
                10 // magic
        ));

        backgroundScreen = CTextureCollection.instance().getTexture(ETexture.CHARACTER_SCREEN_BACKGROUND);
        mCharacterSelectionTitle.setTextSize(72);
        mCharacterSelectionTitle.setTextFont(EFonts.IMMORTAL);
        mPaladinAttribClass.setTextSize(40);
        mPaladinAttribClass.setTextFont(EFonts.IMMORTAL);
        mPaladinAttribClass.setTextColor(Color.rgb(0x0, 0xff, 0x0));
        mPaladinAttribHealth.setTextFont(EFonts.IMMORTAL);
        mPaladinAttribHealth.setTextSize(36);
        mPaladinAttribStrength.setTextFont(EFonts.IMMORTAL);
        mPaladinAttribStrength.setTextSize(36);
        mElfAttribClass.setTextSize(40);
        mElfAttribClass.setTextFont(EFonts.IMMORTAL);
        mElfAttribClass.setTextColor(Color.rgb(0x0, 0xff, 0x0));
        mElfAttribHealth.setTextFont(EFonts.IMMORTAL);
        mElfAttribHealth.setTextSize(36);
        mElfAttribStrength.setTextFont(EFonts.IMMORTAL);
        mElfAttribStrength.setTextSize(36);
    }

    @Override
    public void update(View v) {
        // nothing to update
    }

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

        // left fire
        mLeftFire.draw(c);
        // right fire
        mRightFire.draw(c);
        // draws the screen title
        mCharacterSelectionTitle.draw(c);

        //mPaladinAttribClass.draw(c);
        //mPaladinSprite.draw(c);
        //mPaladinPortrait.draw(c);
        //mPaladinAttribHealth.draw(c);
        //mPaladinAttribStrength.draw(c);

        //mElfAttribClass.draw(c);
        //mElfSprite.draw(c);
        //mElfPortrait.draw(c);
        //mElfAttribHealth.draw(c);
        //mElfAttribStrength.draw(c);

        mMenuClassSelect.draw(c);
    }

    @Override
    public boolean onTouch(MotionEvent e) {

        return true;
    }
}
