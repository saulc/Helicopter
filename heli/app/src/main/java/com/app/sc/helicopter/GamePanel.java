package com.app.sc.helicopter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


class Point {
	private int x, y;
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}


public class GamePanel extends SurfaceView  implements SurfaceHolder.Callback{

		private static final String TAG = GamePanel.class.getSimpleName();
		private MyThread thread;
		private Paint p = new Paint(Color.GREEN);
		

		public GamePanel(Context context) {
			super(context);
		
			getHolder().addCallback(this);
			setFocusable(true);
			thread = new MyThread(getHolder(), this, getResources());
			thread.setRunning(true);
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			Log.d(TAG, "Surface changed");
			
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			Log.d(TAG, "Surface created");
			thread.setRunning(true);
			thread.start();

		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// tell the thread to shut down and wait for it to finish
			// this is a clean shutdown
			Log.d(TAG, "Surface destroyed");
			boolean retry = true;
			while (retry) {
				try {
					
					thread.join();
					//thread.setRunning(false);
					thread.interrupt();
					thread.setRunning(false);
					
					retry = false;
				} catch (InterruptedException e) {
					// try again shutting down the thread
				}
			}
		}


	}