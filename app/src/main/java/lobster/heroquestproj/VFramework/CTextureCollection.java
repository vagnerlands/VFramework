package lobster.heroquestproj.VFramework;

import android.content.res.AssetManager;
import android.graphics.Bitmap;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vagner on 8/19/2016.
 */
public class CTextureCollection {

    static private CTextureCollection sInstance = null;
    static private AssetManager mAssetsManager;
    static private Map<ETexture, CTexture> mTexturesDB = new HashMap();

    CTextureCollection() {

    }

    static public CTextureCollection instance() {
        if (sInstance == null) {
            sInstance = new CTextureCollection();
        }

        return sInstance;
    }

    public void setmAssetsManager(AssetManager assMan) {
        mAssetsManager = assMan;
    }

    static public void addTexture(String textureName, ETexture textureOpt) {
        mTexturesDB.put(textureOpt, new CTexture(textureName, mAssetsManager));
    }

    static public Bitmap getTexture(ETexture textureOpt) {
        return mTexturesDB.get(textureOpt).getBitmap();
    }


}
