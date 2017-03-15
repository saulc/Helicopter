package com.app.sc.helicopter;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;

class HandlerExtension extends Handler {
    
	  private final WeakReference<MainActivity> currentActivity;
	  
	  public HandlerExtension(MainActivity activity){
	    currentActivity = new WeakReference<MainActivity>(activity);
	  }
	  
	  @Override
	  public void handleMessage(Message message){
		  MainActivity activity = currentActivity.get();
	    if (activity!= null){
//	    	String s = message.getData().getString("INFO");
//	       activity.setInfo(s);
	     //  if(s == null)
	       activity.gameOver(message.getData().getInt("OVER"));
	    }
	  }
	}


