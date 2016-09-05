package lobster.heroquestproj;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;

import lobster.heroquestproj.VFramework.CSharedUtils;
import lobster.heroquestproj.VFramework.CSoundCollection;
import lobster.heroquestproj.VFramework.CTextureCollection;
import lobster.heroquestproj.VFramework.EFonts;
import lobster.heroquestproj.VFramework.CFontCollection;
import lobster.heroquestproj.VFramework.ESounds;
import lobster.heroquestproj.VFramework.ETexture;


public class MainActivity extends ActionBarActivity {
    public static final String LOG_ID = "HeroQuest";
    DisplayMetrics dm;
    RenderEngine mRenderEngine = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            CSharedUtils.instance().setActivity(this);

            // update the assets manager for file retrieving
            CFontCollection.instance().setmAssetsManager(getAssets());
            CTextureCollection.instance().setmAssetsManager(getAssets());
            CSoundCollection.instance().setmAssetsManager(getAssets());
            // load all game application fonts
            CFontCollection.instance().addFont("IMMORTAL.ttf", EFonts.IMMORTAL, 0.83f);
            CFontCollection.instance().addFont("comics.ttf", EFonts.COMICS, 1.0f);
            // load all game textures
            CTextureCollection.instance().addTexture("heroquest.png", ETexture.WELCOME_SCREEN_BACKGROUND);
            CTextureCollection.instance().addTexture("startBut.png", ETexture.START_BUTTON);
            CTextureCollection.instance().addTexture("campfire_raw.png", ETexture.CAMPFIRE_ANIMATED);
            CTextureCollection.instance().addTexture("sprite-steps.png", ETexture.SPRITE_STEPS);
            CTextureCollection.instance().addTexture("paladinForwardSprite.png", ETexture.SPRITE_PALADIN);
            // load all sound libraries
            CSoundCollection.instance().addSound("background.mp3", ESounds.ENTRY_SCREEN_BACKGROUND_TRACK);


            CSharedUtils.instance().getSoundPool().setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                    CSoundCollection.instance().mSoundIsReady = true;
                    CSoundCollection.instance().playSound(ESounds.ENTRY_SCREEN_BACKGROUND_TRACK);
                }
            });

            //while (CSoundCollection.instance().mSoundIsReady == false) { };


            mRenderEngine = new RenderEngine(this, new WelcomeScreen());
            CSharedUtils.instance().setmSurfaceView(mRenderEngine);

            super.onCreate(savedInstanceState);
            setContentView(mRenderEngine);


        } catch (Exception e) {
            Log.d(LOG_ID, "Some Exception was found " + e.getMessage());
        }

    }

    /**
     * Handle resuming of the game,
     */
    @Override
    protected void onResume() {
        super.onResume();
        mRenderEngine.resume();
        CSoundCollection.instance().playSound(ESounds.ENTRY_SCREEN_BACKGROUND_TRACK);
    }


    protected void onStop() {
        super.onStop();
        CSoundCollection.instance().stopSound(ESounds.ENTRY_SCREEN_BACKGROUND_TRACK);
        mRenderEngine.pause();
    }

    /**
     * Handle pausing of the game.
     */
    @Override
    protected void onPause() {
        super.onPause();
        CSoundCollection.instance().stopSound(ESounds.ENTRY_SCREEN_BACKGROUND_TRACK);
        mRenderEngine.pause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // intentionally removed
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // General purpose methods
    BitmapFactory.Options sboptions = new BitmapFactory.Options();
    /**
     * load and scale bitmap according to the apps scale factors.
     *
     * @param fname
     * @return
     */
    Bitmap getScaledBitmap(String fname) throws IOException
    {
        sboptions.inScreenDensity = dm.densityDpi;
        sboptions.inTargetDensity =  dm.densityDpi;
        sboptions.inDensity = (int)(dm.densityDpi); // hack: want to load bitmap scaled for width, abusing density scaling options to do it
        InputStream inputStream = getAssets().open(fname);
        Bitmap btm = BitmapFactory.decodeStream(inputStream, null, sboptions);
        inputStream.close();
        return btm;

//        InputStream inputStream = getAssets().open(fname);
//        Bitmap btm = BitmapFactory.decodeStream(inputStream);
//        inputStream.close();
//        return Bitmap.createScaledBitmap(btm, (int)(btm.getWidth()*sizescalefactor), (int)(btm.getHeight()*sizescalefactor), false);
    }

    DisplayMetrics getDisplayMetrics() { return dm; }

}
