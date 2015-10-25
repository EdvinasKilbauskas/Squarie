package com.edvinaskilbauskas.squarie.EdvGameLib.System;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.edvinaskilbauskas.squarie.R;
import com.edvinaskilbauskas.squarie.EdvGameLib.Tools.BaseGameUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.edvinaskilbauskas.squarie.Global;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.google.android.gms.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

/**
 * Main game system, it manages graphics, sound, input and more.
 *
 */

public abstract class GameSystem extends Activity implements Renderer, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private android.opengl.GLSurfaceView GLSurfaceView;
    private GraphicsSystem graphicsSystem;
    private RenderSystem renderSystem;
    private InputSystem inputSystem;
    private FileIOSystem fileIOSystem;
    private boolean initialized = false;
    private float lastTime;
    private float deltaTime;
    private AdView adView;
    public GoogleApiClient googleApiClient;
    public RelativeLayout relativeLayout;
    public GameSystem gameSystem;

    Runnable adManager = new Runnable(){
        public void run(){
            if(Global.showAd == true && adView.getVisibility() == View.INVISIBLE){
                adView.setVisibility(AdView.VISIBLE);
                adView.bringToFront();
            }else if(Global.showAd == false  && adView.getVisibility() == View.VISIBLE){
                adView.setVisibility(AdView.INVISIBLE);
            }
        }
    };


    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);

        gameSystem = this;
        // relative layout
        relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(relativeParams);

        // gl surface view
        GLSurfaceView = new android.opengl.GLSurfaceView(this);
        GLSurfaceView.setEGLContextClientVersion(2);
        GLSurfaceView.setRenderer(this);
        relativeLayout.addView(GLSurfaceView);

        // ad view
        adView = new AdView(this);
        adView.setVisibility(View.INVISIBLE);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3777391894723764/3644259533");
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        adView.setLayoutParams(adParams);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);
        relativeLayout.addView(adView);

        setContentView(relativeLayout);

        // google games api
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
    }

    private static int RC_SIGN_IN = 9001;

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // launch the sign-in flow

        BaseGameUtils.resolveConnectionFailure(this,
                googleApiClient, connectionResult,
                RC_SIGN_IN, "Unknown sign-in error");


        // Put code here to display the sign-in button
    }


    public void startLeaderboardActivity(){
        if(googleApiClient != null) {
            if (!googleApiClient.isConnected())
                googleApiClient.blockingConnect();

            if(googleApiClient.isConnected()){
                gameSystem.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(googleApiClient, gameSystem.getString(R.string.leaderboard_id)), 0);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        connectPlayServices();
    }

    public void onConnected(Bundle connectionHint){
        Games.setViewForPopups(googleApiClient, relativeLayout);
    }

    public boolean connectPlayServices(){
        if(googleApiClient.isConnected() == false && googleApiClient != null && isNetworkAvailable() && googleApiClient.isConnecting() == false){
            googleApiClient.blockingConnect();
        }
        return googleApiClient.isConnected();
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config){
        if(initialized == false){
            creating();
        }

        lastTime = System.nanoTime();
        resuming();
    }

    public void initializeSystem(float gameScreenWidth, float gameScreenHeight){
        graphicsSystem = new GraphicsSystem(gameScreenWidth,gameScreenHeight);
        renderSystem = new RenderSystem(graphicsSystem);
        inputSystem = new InputSystem(this, GLSurfaceView);
        fileIOSystem = new FileIOSystem(this);
        initialized = true;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onDrawFrame(GL10 unused){
        deltaTime = (System.nanoTime() - lastTime)/1000000000.0f;
        lastTime = System.nanoTime();

        if(deltaTime > 0.033f) {
            deltaTime = 0.033f;
        }


        if(initialized == true){
            updating(deltaTime);
            graphicsSystem.update();
            renderSystem.update();
            inputSystem.update();
            runOnUiThread(adManager);
        }
    }

    abstract protected void pausing();
    abstract protected void resuming();
    abstract protected void creating();
    abstract protected void updating(float deltaTime);

    public void onSurfaceChanged(GL10 gl, int width, int height){
    }

    protected void onResume(){
        super.onResume();

        GLSurfaceView.onResume();

        adView.resume();
        adView.bringToFront();
    }

    protected void onPause(){
        super.onPause();
        pausing();

        GLSurfaceView.onPause();
        adView.pause();
    }

    protected void onDestroy(){
        super.onDestroy();
        initialized = false;
        if(googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        adView.destroy();
    }

    public RenderSystem getRenderSystem(){
        return renderSystem;
    }

    public void onBackPressed(){
        System.exit(0);
    }

    public GraphicsSystem getGraphicsSystem(){
        return graphicsSystem;
    }

    public FileIOSystem getFileIOSystem(){
        return fileIOSystem;
    }

    public InputSystem getInputSystem(){
        return inputSystem;
    }
}



