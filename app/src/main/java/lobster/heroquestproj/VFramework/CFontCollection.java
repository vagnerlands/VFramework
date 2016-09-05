package lobster.heroquestproj.VFramework;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vagner on 8/19/2016.
 */
public class CFontCollection {
    static private CFontCollection mInstance;
    static private AssetManager mAssetsManager;
    static private Map<EFonts, CFont> mFontsDB = new HashMap();

    private CFontCollection() {
        // do nothing - for now
    }

    public void setmAssetsManager(AssetManager assMan) {
        mAssetsManager = assMan;
    }

    static public CFontCollection instance() {
        if (mInstance == null) {
            mInstance = new CFontCollection();
        }
        return mInstance;
    }

    static public void addFont(String fontName, EFonts fontOpt, float sizeScalar) {
        mFontsDB.put(fontOpt, new CFont(fontName, mAssetsManager, sizeScalar));
    }

    static public Typeface useFont(EFonts fontOpt) {
        return mFontsDB.get(fontOpt).getGameFontTypeface();
    }

    static public float getFontScaleFactor(EFonts fontOpt) {
        return mFontsDB.get(fontOpt).getGameFontScalar();
    }
}
