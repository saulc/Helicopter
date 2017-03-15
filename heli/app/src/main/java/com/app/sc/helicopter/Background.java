package com.app.sc.helicopter;

import java.util.ArrayList;

import com.app.sc.helicopter.GraphicsItem.state;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Background implements OnTouchListener, SensorEventListener{

	private static final String TAG = Background.class.getSimpleName();
	
	private GamePanel gameView;
	private Bitmap res;
	private int width, height;
	private long ti;
	private int x, y, speedX, speedY, dx, dy;
	private ArrayList<GraphicsItem> gItems;
	private Gravity gravity;
	//private ButtonItem button;
	private Bitmap buttomBit;
	private MyThread mThread;
	private Paint mPaint;
	

	private SensorManager mSensorManager;
	private Sensor mSensor;
	
	private float zStart = -1;
	
	public Background(Resources r, GamePanel sv){
		gameView = sv;
		gameView.setOnTouchListener(this);
		res = BitmapFactory.decodeResource(r, R.drawable.bg);
		width = res.getWidth();
		height = res.getHeight();
		x = 0;
		y = 0;
		speedX = 0;
		speedY = 0;
		ti = 0;
		
		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		//mPaint.setStyle(Style.FILL);
		mPaint.setTextSize(40);
		
		
	//gyroscope
//		mSensorManager = (SensorManager) gameView.getContext().getSystemService(Context.SENSOR_SERVICE);
//		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
//		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
//
//		log("Sensors ready...");
	//	buttomBit = BitmapFactory.decodeResource(r, R.drawable.ic_launcher);
		//button = new ButtonItem(buttomBit, kind.BUTTON);
	}


	private void log(String s){
				Log.d(TAG, s);
	}
	public void setGravityEnabled(boolean b){
		if(b && hasGravity())
			return;
		else if(!b && !hasGravity())
			return;
		else if(!b && hasGravity())
			gravity = null;
		else if(b && !hasGravity())
			gravity = new Gravity();
		Log.d(TAG, "Gravity is on: " + b); 
	}
	public boolean hasGravity(){
		return gravity != null;
	}
	public void setResource(Bitmap b){
		res = b;
	}
	public void updatePos(long t){
		if(hasGravity())
			gravity.updateGravity(t);
		
		  //Log.d(TAG, "Updateing bg t: " + t);
		x += speedX * (t - ti);
		x += speedY * (t - ti);
	
		
		for(int i=0; i<gItems.size(); i++)
		if(gItems.get(i).getState() == state.FIXEDTOBACKGROUND){
			//Log.d(TAG, "Shifting coin by: " + (speedX ));
			gItems.get(i).setX(  (int) (gItems.get(i).getX() +( speedX * (t - ti))));
		}
		
		ti = t;
	}
	public void setSpeedX(int x){
		speedX = x;
	}
	public void setSpeedY(int y){
		speedY = y;
	}
	public int getSpeedX(){
		return speedX;
	}
	public int getSpeedY(){
		return speedY;
	}
	public int getDistance(){
		return dx - x;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public GraphicsItem removeItem(GraphicsItem g){
		if(gItems != null){
			
			return gItems.remove(gItems.indexOf(g));
		}
		return null;
	}
	public void addItem(GraphicsItem g){
		if(gItems == null)
			gItems = new ArrayList<GraphicsItem>();
		gItems.add(g);
	}
	public ArrayList<GraphicsItem> getItems(){
		return gItems;
	}
	public void drawItems(Canvas canvas){
		if(gItems == null)
			return;
		//Log.d(TAG, "Drawing graphicsItems");
		for(int i=0; i<gItems.size(); i++){
			if(gItems.get(i).getState() == state.GRAVITYFIXEDLOCATION){
				
			gItems.get(i).setY(gravity.getY() );
			//Log.d(TAG, "Robot at: x:" + gItems.get(i).getDX() + " y: " + gItems.get(i).getDY());
			} else if(gItems.get(i).getState() == state.FIXEDTOBACKGROUND){
				gItems.get(i).setX(  gItems.get(i).getX());
			}
			
			gItems.get(i).draw(canvas);
		}
			
		
	}
	
	
	public void draw(Canvas canvas, int s){
		  //Log.d(TAG, "Drawing bg");

		
		  if(canvas.getWidth() < (width + x) )
			  canvas.drawBitmap(res, x, 0, null);
		  else {
		//	  Log.d(TAG, "Drawing bg at:" + -x );
			  if(-x > width)
				shiftX();
			  canvas.drawBitmap(res, x, 0, null);		
			  canvas.drawBitmap(res, width + x, 0, null);
		  }
		  
		  drawItems(canvas);
		  canvas.drawText("Score: " + s, canvas.getWidth()*.7f, 60, mPaint);
	  }

	public void drawOver(Canvas canvas, int s, boolean won){
		//draw(canvas, s);

		Paint p = new Paint();
		p.setColor(Color.WHITE);

		mPaint.setStyle(Paint.Style.FILL);
		p.setTextSize(80);

		canvas.drawText("Game Over", canvas.getWidth()*.25f, 300, p);
		canvas.drawText("" + s,  canvas.getWidth()*.4f, 420, p);
		String m = "----____----";
		if(won)
			m = "----^__^----";
		canvas.drawText(m,  canvas.getWidth()*.24f, 530, p);


	}
	private void shiftX(){
		//  Log.d(TAG, "Shifting X bg");
		dx -= x;
		x += width;
	}
	private void shiftY(){
		dy += x;
		x -=height;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent ev) {

		
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			if(v == gameView){
				if(hasGravity()){
				gravity.jump();
				gItems.get(0).animate();
				//gItems.get(0).setDrawState(1);
				return true;
				}
			}
		}
		if(ev.getAction() == MotionEvent.ACTION_UP){
			if(v == gameView){
				if(hasGravity()){
				gravity.noJump();
				//gItems.get(0).setDrawState(0);
				return true;
				}
			}
		}
	
		return false;
	}


	@Override
	public void onSensorChanged(SensorEvent event) {
		int m = 1;
		if (zStart == -1) {
			zStart = event.values[2] * m;

			log("zStart: " + zStart);
		}

		else {
			float z = event.values[2]*m;
		if(z > zStart) gravity.jump();
		else gravity.noJump();
		
		log(zStart + "   "  + z);
		
		}
//		String s = "x: " + event.values[0] + " y: " + event.values[1] + " z: " + event.values[2];
//		System.out.println(s);
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		log("Accuracy Changed");
		
	}

}
