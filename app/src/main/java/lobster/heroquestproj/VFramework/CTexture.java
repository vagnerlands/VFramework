package lobster.heroquestproj.VFramework;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;

import lobster.heroquestproj.MainActivity;

/**
 * Created by Vagner on 8/19/2016.
 */
public class CTexture {
    private Bitmap mTexture = null;

    CTexture(String textureName, AssetManager assetManager) {
        try {
            InputStream inputStream = assetManager.open(textureName);
            mTexture = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            Log.d(MainActivity.LOG_ID, "Loading texture " + textureName, e);
        }
    }

    public Bitmap getBitmap() {
        return mTexture;
    }
}
