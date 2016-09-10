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
import android.widget.Toast;

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
            CTextureCollection.instance().addTexture("medieval_village.png", ETexture.CHARACTER_SCREEN_BACKGROUND);
            CTextureCollection.instance().addTexture("startBut.png", ETexture.START_BUTTON);
            CTextureCollection.instance().addTexture("startButBack.png", ETexture.START_BUTTON_BACKGROUND);
            CTextureCollection.instance().addTexture("campfire_raw.png", ETexture.CAMPFIRE_ANIMATED);
            CTextureCollection.instance().addTexture("sprite-steps.png", ETexture.SPRITE_STEPS);
            CTextureCollection.instance().addTexture("paladinForwardSprite.png", ETexture.SPRITE_PALADIN);
            CTextureCollection.instance().addTexture("menu_exit.png", ETexture.MENU_EXIT_DOOR);
            CTextureCollection.instance().addTexture("paladin_portrait.png", ETexture.PALADIN_PORTRAIT);
            CTextureCollection.instance().addTexture("elf_portrait.png", ETexture.ELF_PORTRAIT);
            CTextureCollection.instance().addTexture("elfForwardSprite.png", ETexture.SPRITE_ELF);
            CTextureCollection.instance().addTexture("mage_portrait.png", ETexture.MAGE_PORTRAIT);
            CTextureCollection.instance().addTexture("mageForwardSprite.png", ETexture.SPRITE_MAGE);
            // load all sound libraries
            //CSoundCollection.instance().addSound("menu_background.mp3", ESounds.MENU_BACKGROUND_TRACK);
            CSoundCollection.instance().addSound("pause.mp3", ESounds.GAME_IS_PAUSED);
            CSoundCollection.instance().addSound("start.wav", ESounds.PLAY_BUTTON_PRESSED);
            CSoundCollection.instance().addSound("metal_impact.wav", ESounds.METAL_IMPACT);
            CSoundCollection.instance().addSound("femalethankyou.mp3", ESounds.FEMALE_THANK_YOU);
            CSoundCollection.instance().addSound("victory.mp3", ESounds.FIGHT_VICTORY);
            CSoundCollection.instance().addSound("levelup.mp3", ESounds.LEVEL_UP);
            CSoundCollection.instance().addSound("goodbye_female.wav", ESounds.GOODBYE_FEMALE);


            CSharedUtils.instance().getSoundPool().setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                    CSoundCollection.instance().mSoundIsReady = true;
                    //CSoundCollection.instance().playSound(ESounds.MENU_BACKGROUND_TRACK);
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
        //CSoundCollection.instance().playSound(ESounds.ENTRY_SCREEN_BACKGROUND_TRACK);
    }


    protected void onStop() {
        super.onStop();
        //CSoundCollection.instance().playSound(ESounds.GAME_IS_PAUSED);
        mRenderEngine.pause();
    }

    /**
     * Handle pausing of the game.
     */
    @Override
    protected void onPause() {
        super.onPause();
        CSoundCollection.instance().playSound(ESounds.GAME_IS_PAUSED);
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
    public void onBackPressed() {
        Toast.makeText(mRenderEngine.getContext(), "Back button pressed", Toast.LENGTH_LONG);
        Log.d(LOG_ID, "Back button pressed");
        CSharedUtils.instance().setCurrentScreenView(new WelcomeScreen());
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
