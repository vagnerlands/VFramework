package lobster.heroquestproj.VFramework;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.media.SoundPool;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import lobster.heroquestproj.MainActivity;

/**
 * Created by Vagner on 9/3/2016.
 */
public class CSoundCollection {
    static private CSoundCollection sInstance = null;
    static private AssetManager mAssetsManager;
    static private Map<ESounds, CSound> mSoundsDB = new HashMap();
    static public boolean mSoundIsReady = false;

    CSoundCollection() {

    }

    static public CSoundCollection instance() {
        if (sInstance == null) {
            sInstance = new CSoundCollection();
        }

        return sInstance;
    }

    public void setmAssetsManager(AssetManager assMan) {
        mAssetsManager = assMan;
    }

    public void addSound(String textureName, ESounds textureOpt) {
        mSoundsDB.put(textureOpt, new CSound(textureName, mAssetsManager));
    }

    public void playSound(ESounds soundOpt) {
        int retCode = CSharedUtils.instance().getSoundPool().play(
                mSoundsDB.get(soundOpt).getSoundStream(),
                0.9f, // left volume
                0.9f, // right volume
                0, // priority ???
                0, // loop
                1.0f); // rate
        Log.d(MainActivity.LOG_ID, "playSound st " + retCode);
    }

    public void playSoundInLoop(ESounds soundOpt) {
        int retCode = CSharedUtils.instance().getSoundPool().play(
                mSoundsDB.get(soundOpt).getSoundStream(),
                0.9f, // left volume
                0.9f, // right volume
                0, // priority ???
                -1, // loop (-1 for infinite)
                1.0f); // rate
        Log.d(MainActivity.LOG_ID, "playSound st " + retCode);
    }

    public void stopSound(ESounds soundOpt) {
        CSharedUtils.instance().getSoundPool().stop(
                mSoundsDB.get(soundOpt).getSoundStream()
        );
    }
}
