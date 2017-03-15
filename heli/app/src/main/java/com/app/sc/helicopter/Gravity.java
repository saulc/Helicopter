package com.app.sc.helicopter;

import android.util.Log;

public class Gravity{
	private int x, y, speedX, speedY;
	private long ti;
	private final int acceleration =  1;
	private final int jumpSpeed = 10;
	private final int topSpeed = 30;
	private final int userAcceration = 7;
	private boolean jumping = false;
	public Gravity(){
		x = 0;
		y = -10;
		speedX = 0;
		speedY = 20;
		ti = 0;
	}
	
	public void updateGravity(long t){
		
		int dt = (int) (ti - t);
		ti = t;
		
		if(jumping)
			speedY += (acceleration - userAcceration) * dt;
		else speedY = speedY + (acceleration * dt);
		
		if(speedY > topSpeed)
			speedY= topSpeed;
		else if(speedY < -topSpeed)
			speedY = -topSpeed;
		
		x -= speedX * dt;
		y += speedY * dt;
		
		//Log.d("Gravity", "Updating gravity, Speed =" + speedY);
	}
	public void jump(){
		//Log.d("Gravity", "Jump started");
		jumping(true);

	}
	
	public void noJump(){
	//	Log.d("Gravity", "Jump ended");
	
		jumping = false;
	}
	
	
	public void jumping(Boolean b){
		
			jumping = b;
	}
	public void shiftX(int dx){
		x -= dx;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getSpeedX(){
		return speedX;
	}
	public int getSpeedY(){
		return speedY;
	}
}