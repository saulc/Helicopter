package com.app.sc.helicopter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public  class GraphicsItem {
	private Bitmap res;
	private Bitmap[] animation;
	private int framesPerSlide, currentFrameCount;
	private int animationFrame;
	private int x, y, dx, dy;
	public enum state { FIXEDTOBACKGROUND, FIXEDLOCATION, GRAVITYFIXEDTOBACKGROUND, GRAVITYFIXEDLOCATION, HALFGRAVITYFIXEDTOBACKGROUND};
	private state MYSTATE;
	public enum kind { COIN, BOMB, ROBOT, BUTTON };
	private kind ME;
	private boolean animate = false, alt = false;
	private int tolerance = 50;
	
	private final int noGo = 0, go = 1, boom = 2;
	private int drawState = 0;
	
	public GraphicsItem(Bitmap resources, kind k){
		res = resources;
		ME = k;
		x = 0; 
		y = 0;
		MYSTATE = state.FIXEDLOCATION;
	}
	public void setAnimation(Bitmap[] a, int framesPerSlide){
		animation = a;
		this.framesPerSlide = framesPerSlide;
		currentFrameCount = 0;
		animationFrame = 0;
	}
	public kind getKind(){
		return ME;
	}
	public void setDX(int d){
		dx = d;
	}
	public void setDY(int d){
		dy= d;
	}
	public int getDX(){
		return dx;
	}
	public int getDY(){
		return dy;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	public state getState(){
		return MYSTATE;
	}
	
	public void setState(state s){
		MYSTATE = s;
	}
	public void draw(Canvas c){
		if(animate){
			if(currentFrameCount++ > framesPerSlide)
				animationFrame++;
			if(animationFrame >= animation.length){
				animationFrame = 0;
				animate = false;
			}
			
			c.drawBitmap(animation[animationFrame], x, y, null);
			
		}
//		else if(MYSTATE == state.FIXEDLOCATION){
//			
//			c.drawBitmap(animation[drawState], x, y, null);
//		}
		else if(res != null){
		c.drawBitmap(res, x,  y, null);
		
		//Log.d("GI", "Drawing item at x= " + x + " y = " + y);
		}
	}
	
	public void setDrawState(int i){
		drawState = i;
	}
	public void animate(){
		if(animation.length > 1){
			animate = true;
			currentFrameCount = 0;
			animationFrame = 0;
		}
	}
	public Rect getBoundingRect(){
		return new Rect(x + tolerance, y + tolerance, res.getWidth() - tolerance, res.getHeight()- tolerance);
	}
	
	public boolean isColliding(GraphicsItem otherItem){
		Rect me = this.getBoundingRect();
		Rect other = otherItem.getBoundingRect();
		if(me.getX() < other.getX() + other.getWidth() 
				&& me.getX() + me.getWidth() > other.getX()
				&& me.getY() < other.getY() + other.getHeight()
				&& me.getY() + me.getHeight() > other.getY() )
			return true;
		
		return false;
//		if (RectA.X1 < RectB.X2 && RectA.X2 > RectB.X1 &&
//			    RectA.Y1 < RectB.Y2 && RectA.Y2 > RectB.Y1) 
	}

}
class Rect {
	int x, y, width, height;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Rect(int x, int y, int width,int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
}

