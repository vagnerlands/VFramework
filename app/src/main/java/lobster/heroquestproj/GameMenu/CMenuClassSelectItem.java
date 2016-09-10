package lobster.heroquestproj.GameMenu;

import android.graphics.Canvas;
import android.graphics.Color;

import lobster.heroquestproj.Renders.CScreenText;
import lobster.heroquestproj.Renders.CSprite;
import lobster.heroquestproj.Renders.CSpriteFilm;
import lobster.heroquestproj.VFramework.EFonts;
import lobster.heroquestproj.VFramework.ETexture;

/**
 * Created by Vagner on 9/10/2016.
 */
public class CMenuClassSelectItem {
    CSprite mItemPortrait;
    CSpriteFilm mItemSprite;
    private String mClassName;

    private int mStrength;
    private int mHealth;
    private int mMagic;

    CScreenText mAttribClass;
    CScreenText mAttribMagic = new CScreenText("Magic:", 0.45f, 0.26f);
    CScreenText mAttribHealth = new CScreenText("Health:", 0.45f, 0.33f);
    CScreenText mAttribStrength = new CScreenText("Strength:", 0.45f, 0.4f);

    // optional field
    private String mShortDescription;

    public CMenuClassSelectItem(CSprite classPortrait, CSpriteFilm classSprite, String className, int strength, int health, int magic) {
        mItemPortrait = classPortrait;
        mItemSprite = classSprite;
        mClassName = className;
        mStrength = strength;
        mHealth = health;
        mMagic = magic;

        // creates a text object with the class name
        mAttribClass = new CScreenText(mClassName, 0f, 0f);
        // update text font
        mAttribClass.setTextFont(EFonts.IMMORTAL);
        mAttribMagic.setTextFont(EFonts.IMMORTAL);
        mAttribHealth.setTextFont(EFonts.IMMORTAL);
        mAttribStrength.setTextFont(EFonts.IMMORTAL);

        // update text size
        mAttribClass.setTextSize(40);
        mAttribMagic.setTextSize(36);
        mAttribHealth.setTextSize(36);
        mAttribStrength.setTextSize(36);

        // update text color
        mAttribClass.setTextColor(Color.rgb(0x0, 0xff, 0x0));
    }

    public void setShortDescription(String shortDesc) { mShortDescription = shortDesc; }

    public void draw(Canvas canvas, float coordX, float coordY) {
        // ignore relative positioning - this mode will calculate fix position for all objects
        mItemPortrait.setRelativeX(-1f);
        mItemPortrait.setRelativeY(-1f);

        mItemPortrait.setFixedX(coordX);
        mItemPortrait.setFixedY(coordY);

        mItemSprite.setFixedX(coordX + 270);
        mItemSprite.setFixedY(coordY);

        mAttribClass.setFixedX(coordX + 375);
        mAttribClass.setFixedY(coordY);

        mAttribMagic.setFixedX(coordX + 375);
        mAttribMagic.setFixedY(coordY + 40);

        mAttribHealth.setFixedX(coordX + 375);
        mAttribHealth.setFixedY(coordY + 80);

        mAttribStrength.setFixedX(coordX + 375);
        mAttribStrength.setFixedY(coordY + 120);

        mItemPortrait.draw(canvas);
        mItemSprite.draw(canvas);
        mAttribClass.draw(canvas);
        mAttribMagic.draw(canvas);
        mAttribHealth.draw(canvas);
        mAttribStrength.draw(canvas);


    }
}
