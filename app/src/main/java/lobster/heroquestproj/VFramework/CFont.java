package lobster.heroquestproj.VFramework;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;

import lobster.heroquestproj.MainActivity;

/**
 * Created by Vagner on 8/19/2016.
 */
public class CFont {

    private Typeface mFontObj;
    private float mFontScalar;

    public CFont(String fontName, AssetManager assManager, float sizeScalar) {
        try {
            mFontObj = Typeface.createFromAsset(assManager, fontName);
            mFontScalar = sizeScalar;
        } catch (Exception e) {
            Log.d(MainActivity.LOG_ID, "Loading font " + fontName, e);
        }
    }

    public Typeface getGameFontTypeface() {
        return mFontObj;
    }

    public float getGameFontScalar() {
        return mFontScalar;
    }
}
