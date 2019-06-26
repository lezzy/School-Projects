package com.example.defenderfinal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.content.Context;


public class Defender extends SurfaceView{
	Bitmap playerBitmap; //the bitmap 
	Rect dst; 
	int displacementx;
	int displacementy;
	int displacementb;
	int x1;
	int x2; 
	int y1;
	int y2;
	int xb = 0;
	boolean down; //if the screen is being touched, true.
	boolean shoot = true;
	
	public Defender(Context context){
		super(context);
		setFocusable(true);
		dst = new Rect(200, 280, 300, 380);
		playerBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
	}
	
	public boolean onTouchEvent(MotionEvent event){
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN: //if the screen is being touched
				down = true;
				// if the touch is to the left or right of the screen
				if (event.getX() < getWidth()/2){
					displacementx = -50;
				}
				else if (event.getX() > getWidth()/2){
					displacementx = 50;
				} 
				if (event.getX() > 600 && event.getX() < 680 && event.getY() > 330 && event.getY() < 390){
					displacementb = 100;
				}
				if (event.getY() < getHeight()/2){ //if the touch is above half the screen or not
					displacementy = -50;
				}
				else if(event.getY() > getHeight()/2){
					displacementy = 50;
				}
				return true;
			case MotionEvent.ACTION_MOVE: //screen is touched and moves 
				down = true;           		// around on screen
				// if the touch is to the left of the screen or to the right
				if (event.getX() < getWidth()/2){
					displacementx = -50;
				}
				if (event.getY() > getWidth()/2){
					displacementx = 50;
				} 
				if (event.getY() < getHeight()/2){ //if the touch is above half the screen or not
					displacementy = -50;
				}
				else if(event.getY() > getHeight()/2){
					displacementy = 50;
				}
				return true;
			case MotionEvent.ACTION_UP: //finger is lifted
				down = false;
				return true;
		}
		
		return false;
	}
	
	
	//public void ProcessInput(){
	//}
	
	public void UpdateWorld(){
		x1 = 200 + displacementx;
		x2 = 300 + displacementx; 
		y1 = 280 + displacementy;
		y2 = 380 + displacementy;
		xb += x2 + displacementb;
		if (x1 < 10){
			x1 = 10;
			x2 = 110;
		}
		else if (x2 > getWidth()){
			x2 = getWidth() - 10;
			x1 = getWidth() - 110;
		} 
		if (y1 < 10){
			y1 = 10;
			y2 = 110;
		}
		else if (y2 > getHeight()){
			y2 = getHeight() - 10;
			y1 = getHeight() - 110;
		}
	}
	public void draw(Canvas c) {
		dst.set(x1, y1, x2, y2); //draws the defender on updated coordinates
		c.drawColor(Color.BLACK); // Set the background to black
		c.drawBitmap(playerBitmap, null, dst, null); // Draw the bitmap
		
		if (shoot = true){
			Paint p = new Paint();
			p.setColor(Color.WHITE);
			c.drawCircle(xb, (y2-50), 5, p);
		}

	}
	
}
