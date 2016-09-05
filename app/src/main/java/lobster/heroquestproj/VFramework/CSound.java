package lobster.heroquestproj.VFramework;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;
import android.util.Log;

import lobster.heroquestproj.MainActivity;
import lobster.heroquestproj.VFramework.CSharedUtils;

/**
 * Created by Vagner on 9/3/2016.
 */
public class CSound {
    private int mSoundStreamId = -1;

    CSound(String soundName, AssetManager assetManager) {
        try {
            AssetFileDescriptor inputStream = assetManager.openFd(soundName);
            // file descriptor and priority
            mSoundStreamId = CSharedUtils.instance().getSoundPool().load(inputStream, 1);
            inputStream.close();
        } catch (Exception e) {
            Log.d(MainActivity.LOG_ID, "Loading sound " + soundName, e);
        }
    }

    public int getSoundStream() {
        return mSoundStreamId;
    }
}
