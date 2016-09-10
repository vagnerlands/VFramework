package lobster.heroquestproj.VFramework;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.SurfaceView;

import lobster.heroquestproj.MainActivity;
import lobster.heroquestproj.RenderEngine;
import lobster.heroquestproj.Screen;

/**
 * Created by Vagner on 8/20/2016.
 */
public class CSharedUtils {
    static private CSharedUtils mInstance = null;


    //heart bit - number of cycles - important for animations and effects
    private int mHeartBit = 0;
    //Activity object - use is still TBD
    private Activity mActivityObj;
    // SurfaceView - used to get parameters such as width and height of the canvas
    private SurfaceView mSurfaceViewObj;
    // gets the sound pool
    private SoundPool mSoundPool;
    // start cycle timestamp
    private long mStartTime = 0;
    // cycle length in ms
    private final long mCycleLength = 17;



    private CSharedUtils() {
        Log.d(MainActivity.LOG_ID, "total num of sounds = " + ESounds.values().length);
        mSoundPool = new SoundPool(ESounds.values().length, AudioManager.STREAM_MUSIC, 100);
    }

    static public CSharedUtils instance() {
        if (mInstance == null) {
            mInstance = new CSharedUtils();
        }
        return mInstance;
    }

    public int getmHeartBit() {
        return mHeartBit;
    }

    public void increaseHeartBit() {
        mHeartBit++;
    }

    public void setActivity(Activity actObj) {
        // assign the activity object
        mActivityObj = actObj;
        // application volume
        mActivityObj.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    public Activity getActivity() {
        return mActivityObj;
    }

    public SoundPool getSoundPool() { return mSoundPool; }

    public void setmSurfaceView(SurfaceView surView) {
        mSurfaceViewObj = surView;
    }

    public SurfaceView getmSurfaceView() {
        return mSurfaceViewObj;
    }

    public void setCurrentScreenView(Screen newScreen) {
        // not the best approach - but for this project, all SurfaceViewObj will be a RenderEngine type which derives from SurfaceView
        ((RenderEngine)mSurfaceViewObj).setCurrentScreen(newScreen);
    }

    public void setStartCycle() {
        mStartTime = System.nanoTime();
    }

    public void setEndCycle() {
        long endTime = System.nanoTime();

        // one cycle should last 17ms (1000ms / 60fps)
        long cycleTime = (long)((endTime - mStartTime) / 1000);
        if (cycleTime < mCycleLength) {
            try {
                Log.d(MainActivity.LOG_ID, "sleeping " + (long)(mCycleLength - cycleTime));
                Thread.sleep((long) (mCycleLength - cycleTime));
            } catch (Exception e) {
                Log.d(MainActivity.LOG_ID, "after Cycle ", e);
            }

        }
    }



}
