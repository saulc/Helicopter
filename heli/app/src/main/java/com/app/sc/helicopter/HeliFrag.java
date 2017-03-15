package com.app.sc.helicopter;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class HeliFrag extends Fragment{
	

	 private static final String TAG = HeliFrag.class.getSimpleName();
	
	  private TextView info;

	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	      Log.d(TAG, "Creating HeliFrag!");
		  return new GamePanel(getActivity());
	    }
	  
	 
		
}
