package lobster.heroquestproj;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import lobster.heroquestproj.VFramework.CSharedUtils;

import static android.view.SurfaceHolder.SURFACE_TYPE_GPU;

/**
 * Created by Vagner on 8/14/2016.
 * Renderer
 */
public class RenderEngine extends SurfaceView implements Runnable, View.OnTouchListener {

    private volatile boolean isRendering = false;
    Thread renderThread = null;
    SurfaceHolder mSurfaceHolder;
    Screen mCurrentScreen;

    public RenderEngine(Context context, Screen objScreen) {
        super(context);
        mSurfaceHolder = getHolder();
        // GPU acceleration enabled
        mSurfaceHolder.setType(SURFACE_TYPE_GPU);
        mCurrentScreen = objScreen;
        setOnTouchListener(this);
    }

    public void resume() {
        isRendering = true;
        Log.d(MainActivity.LOG_ID, "new called in resume()");
        renderThread = new Thread(this);
        renderThread.start();
    }

    @Override
    public void run() {
        try {
            while(isRendering){
                while(!mSurfaceHolder.getSurface().isValid()) {
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) { Log.d(MainActivity.LOG_ID, "getSurface", e);  }
                }
                CSharedUtils.instance().setStartCycle();
                CSharedUtils.instance().increaseHeartBit();
                // update screen's context
                mCurrentScreen.update(this);

                // draw screen
                Canvas c = mSurfaceHolder.lockCanvas();
                // while flipping the screen - this will cause exception cause canvas isn't ready
                mCurrentScreen.draw(c, this);
                mSurfaceHolder.unlockCanvasAndPost(c);
                // useful to calculate how many fps
                // also performs sleep in case that the cycle has passed too fast - avoid frying your cpu
                CSharedUtils.instance().setEndCycle();
            }
        } catch (Exception e) {
            // arguably overzealous to grab all exceptions here...but i want to know.
            Log.d(MainActivity.LOG_ID, "View", e);
            e.printStackTrace();
        }
    }

    public void pause() {
        isRendering = false;
        while(true) {
            try {
                renderThread.join();
                return;
            } catch (InterruptedException e) {
                // retry
            }
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        try {
            return mCurrentScreen.onTouch(event);
        }
        catch (Exception e) {
            // arguably overzealous to grab all exceptions here...but i want to know.
            Log.d(MainActivity.LOG_ID, "onTouch", e);
        }
        return false;
    }
}

