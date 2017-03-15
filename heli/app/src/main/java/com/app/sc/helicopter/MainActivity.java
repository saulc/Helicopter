package com.app.sc.helicopter;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class MainActivity  extends FragmentActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
	 private Handler mHandler;
	 private Fragment myFrag;
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_fullscreen);
	     mHandler = new HandlerExtension(this);
		
	}

	private void startGame(){
		Log.d(TAG, "Starting Game, adding helifragment");
		try {
			myFrag = new HeliFrag();

			getSupportFragmentManager().beginTransaction()
              .add(R.id.mainFrame, myFrag).commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void endGame(){
		Log.d(TAG, "Ending game!, removing helifragment");

		try {
			getSupportFragmentManager().beginTransaction()
              .remove(myFrag).commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void gameOver(int score) {

		Log.d(TAG, "GameOVer! " + score);
	    endGame();
	    Log.d(TAG, "Restarting!");
	    startGame();
		
	
	
	}

	public Handler getHandler() {
		
		return mHandler;
	}

	
	@Override
	public void onResume(){
		super.onResume();
		startGame();
	}

	@Override
	public void onPause(){
		super.onPause();
		endGame();
	}


}
