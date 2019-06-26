package com.example.qbert;




import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class QBert extends SurfaceView{

	float x = 50;
	float y = 600;
	int length = 45;
	int i;
	int j;
	int k;
	int x1, x2, y1, y2; // sprite coordinates
	int xb, yb; // ball coordinates
	int displacementx, displacementy; // sprite displacement
	int displacementa, displacementb; // ball displacement
	int angle = 20;
	Rect sprite;
	Bitmap playerBitmap;
	boolean down;
	Random generator;

	public QBert(Context context) {
		super(context);
		setFocusable(true);
		sprite = new Rect(320, 410, 120, 210);
		xb = 365;
		yb = 195;
		playerBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		// TODO Auto-generated constructor stub
	}
	

	public boolean onTouchEvent(MotionEvent event){
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN: //if the screen is being touched
				down = true;
				// if the touch is to the top left
				if (down==true){
					displacementa += 45;
					displacementb += 45 + angle;
				}	
				
				if (event.getX() < getWidth()/2 && event.getY() < getHeight()/2){
					displacementx += -45;
					displacementy += -45 - angle;
				}
				// if the touch is to the top right
				else if (event.getX() > getWidth()/2 && event.getY() < getHeight()/2 ){
					displacementx += 45;
					displacementy += -45 - angle;
				} 
				// if the touch is to the bottom left
				else if (event.getX() < getWidth()/2 && event.getY() > getHeight()/2){ //if the touch is above half the screen or not
					displacementx += -45;
					displacementy += 45 + angle;
				}
				// if the touch is to the bottom right
				else if(event.getX() > getWidth()/2 && event.getY() > getHeight()/2){
					displacementx += 45;
					displacementy += 45 + angle;
				}
				return true;
			case MotionEvent.ACTION_MOVE: //screen is touched and moves 
				down = true;           		// around on screen
				// if the touch is to the left of the screen or to the right
				if (event.getX() < getWidth()/2 && event.getY() < getHeight()/2){
					displacementx += -45;
					displacementy += -45 - angle;
				}
				// if the touch is to the top right
				else if (event.getX() > getWidth()/2 && event.getY() < getHeight()/2){
					displacementx += 45;
					displacementy += -45 - angle;
				} 
				// if the touch is to the bottom left
				else if (event.getX() < getWidth()/2 && event.getY() > getHeight()/2){ //if the touch is above half the screen or not
					displacementx += -45;
					displacementy += 45 + angle;
				}
				// if the touch is to the bottom right
				else if(event.getX() > getWidth()/2 && event.getY() > getHeight()/2){
					displacementx += 45;
					displacementy += 45 + angle;
				}
				return true;
			case MotionEvent.ACTION_UP: //finger is lifted
				down = false;
				return true;
		}
		
		return false;
	} 
	
	public Path RedSide() {
		Path p = new Path();

		// creates the Red side of the cube pyramid
		p.moveTo(x, y);

	
		p.rLineTo(0, length);
		p.rLineTo(length , angle);
		p.rLineTo(0, -length);
		for (i = 0; i < 6; i++){
			p.rMoveTo(length, -angle);
			p.rLineTo(0, length);
			p.rLineTo(length ,angle);
			p.rLineTo(0, -length);
			
		}
		p.rMoveTo(-length, -angle - length);
		p.rLineTo(0, length);
		p.rLineTo(-length, -angle);
		p.rLineTo(0, -length);
		for (i = 0; i < 5; i++){
			p.rMoveTo(-length, angle);
			p.rLineTo(0, length);
			p.rLineTo(-length, -angle);
			p.rLineTo(0, -length);
		}
		
		p.rMoveTo(length, -angle - length);
		p.rLineTo(0, length);
		p.rLineTo(length , angle);
		p.rLineTo(0, -length);
		for (i = 0; i < 4; i++){
			p.rMoveTo(length, -angle);
			p.rLineTo(0, length);
			p.rLineTo(length , angle);
			p.rLineTo(0, -length);
		}
		
		p.rMoveTo(-length, -angle - length);
		p.rLineTo(0, length);
		p.rLineTo(-length, -angle);
		p.rLineTo(0, -length);
		for (i = 0; i < 3; i++){
			p.rMoveTo(-length, angle);
			p.rLineTo(0, length);
			p.rLineTo(-length, -angle);
			p.rLineTo(0, -length);
		}
		
		p.rMoveTo(length, -angle - length);
		p.rLineTo(0, length);
		p.rLineTo(length , angle);
		p.rLineTo(0, -length);
		for (i = 0; i < 2; i++){
			p.rMoveTo(length, -angle);
			p.rLineTo(0, length);
			p.rLineTo(length , angle);
			p.rLineTo(0, -length);
		}
		
		p.rMoveTo(-length, -angle - length);
		p.rLineTo(0, length);
		p.rLineTo(-length, -angle);
		p.rLineTo(0, -length);
		for (i = 0; i < 1; i++){
			p.rMoveTo(-length, angle);
			p.rLineTo(0, length);
			p.rLineTo(-length, -angle);
			p.rLineTo(0, -length);
		}
		
		p.rMoveTo(length, -angle - length);
		p.rLineTo(0, length);
		p.rLineTo(length , angle);
		p.rLineTo(0, -length);

		// Close the path, to return to the top of the triangle
		p.close();
		
		return p;
	}
	public Path WhiteSide(){
		//creates the white side of the cube pyramid
		Path q = new Path();
		q.moveTo(x+ length, y + angle);

		
		q.rLineTo(0, length);
		q.rLineTo(length , -angle);
		q.rLineTo(0, -length);
		for (j = 0; j < 6; j++){
			q.rMoveTo(length, angle);
			q.rLineTo(0, length);
			q.rLineTo(length , -angle);
			q.rLineTo(0, -length);
			
		}
		q.rMoveTo(-length, -angle - length);
		q.rLineTo(0, length);
		q.rLineTo(-length, angle);
		q.rLineTo(0, -length);
		for (j = 0; j < 5; j++){
			q.rMoveTo(-length, -angle);
			q.rLineTo(0, length);
			q.rLineTo(-length, angle);
			q.rLineTo(0, -length);
		}
		
		q.rMoveTo(length, -angle - length);
		q.rLineTo(0, length);
		q.rLineTo(length , -angle);
		q.rLineTo(0, -length);
		for (i = 0; i < 4; i++){
			q.rMoveTo(length, angle);
			q.rLineTo(0, length);
			q.rLineTo(length , -angle);
			q.rLineTo(0, -length);
		}
		
		q.rMoveTo(-length, -angle - length);
		q.rLineTo(0, length);
		q.rLineTo(-length, angle);
		q.rLineTo(0, -length);
		for (i = 0; i < 3; i++){
			q.rMoveTo(-length, -angle);
			q.rLineTo(0, length);
			q.rLineTo(-length, angle);
			q.rLineTo(0, -length);
		}
		
		q.rMoveTo(length, -angle - length);
		q.rLineTo(0, length);
		q.rLineTo(length , -angle);
		q.rLineTo(0, -length);
		for (i = 0; i < 2; i++){
			q.rMoveTo(length, angle);
			q.rLineTo(0, length);
			q.rLineTo(length , -angle);
			q.rLineTo(0, -length);
		}
		
		q.rMoveTo(-length, -angle - length);
		q.rLineTo(0, length);
		q.rLineTo(-length, angle);
		q.rLineTo(0, -length);
		for (i = 0; i < 1; i++){
			q.rMoveTo(-length, -angle);
			q.rLineTo(0, length);
			q.rLineTo(-length, angle);
			q.rLineTo(0, -length);
		}
		
		q.rMoveTo(length, -angle - length);
		q.rLineTo(0, length);
		q.rLineTo(length , -angle);
		q.rLineTo(0, -length);
	
		q.close();
		return q;
	}
	
	public Path BlueSide(){
		//creates the Blue side of the pyramid
		Path r = new Path();
		r.moveTo(x, y );

		r.rLineTo(length , angle);
		r.rLineTo(length , -angle);
		r.rLineTo(-length, -angle);
		for (k = 0; k < 6; k++){
			r.rMoveTo(length, angle);
			r.rLineTo(length , angle);
			r.rLineTo(length , -angle);
			r.rLineTo(-length, -angle);
		}
		r.rMoveTo(-length, -angle - length);
		r.rLineTo(length, angle);
		r.rLineTo(-length, angle);
		r.rLineTo(-length, -angle);
		for (i = 0; i < 5; i++){
			r.rMoveTo(-length, -angle);
			r.rLineTo(length, angle);
			r.rLineTo(-length, angle);
			r.rLineTo(-length, -angle);
		}
		
		r.rMoveTo(length, -angle - length);
		r.rLineTo(length, -angle);
		r.rLineTo(length , angle);
		r.rLineTo(-length, angle);
		for (i = 0; i < 4; i++){
			r.rMoveTo(length, -angle);
			r.rLineTo(length, -angle);
			r.rLineTo(length , angle);
			r.rLineTo(-length, angle);		
		}
		
		r.rMoveTo(-length, -angle - length);
		r.rLineTo(length, -angle);
		r.rLineTo(-length, -angle);
		r.rLineTo(-length, angle);
		for (i = 0; i < 3; i++){
			r.rMoveTo(-length, -angle);
			r.rLineTo(length, angle);
			r.rLineTo(-length, angle);
			r.rLineTo(-length, -angle);
		}
		
		r.rMoveTo(length, -angle - length);
		r.rLineTo(length, -angle);
		r.rLineTo(length , angle);
		r.rLineTo(-length, angle);
		for (i = 0; i < 2; i++){
			r.rMoveTo(length, -angle);
			r.rLineTo(length, -angle);
			r.rLineTo(length , angle);
			r.rLineTo(-length, angle);	
		}
		
		r.rMoveTo(-length, -angle - length);
		r.rLineTo(length, -angle);
		r.rLineTo(-length, -angle);
		r.rLineTo(-length, angle);
		for (i = 0; i < 1; i++){
			r.rMoveTo(-length, -angle);
			r.rLineTo(length, angle);
			r.rLineTo(-length, angle);
			r.rLineTo(-length, -angle);
		}
		
		r.rMoveTo(length, -angle - length);
		r.rLineTo(length, -angle);
		r.rLineTo(length , angle);
		r.rLineTo(-length, angle);
		r.close();
		return r;
	}

	
	public void UpdateWorld(){
		// update Qbert's movements
		x1 = 320 + displacementx;
		x2 = 410 + displacementx; 
		y1 = 120 + displacementy;
		y2 = 210 + displacementy;
		xb = 365 + displacementa;
		yb = 195 + displacementb;
		
		// update Ball movements
		
		// Qbert can't go off-screen
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
	


	public void draw(Canvas c){

		sprite.set(x1, y1, x2, y2);

		
		// Make background black
		c.drawColor(Color.BLACK);
		
		// Draw paths
		Paint g = new Paint();
		Paint g1 = new Paint();
		Paint g2 = new Paint();
		g.setColor(Color.RED);
		g1.setColor(Color.WHITE);
		g2.setColor(Color.BLUE);
		c.drawPath(RedSide(), g);
		c.drawPath(WhiteSide(), g1);
		c.drawPath(BlueSide(), g2);	
		

		
		// Draw ball
		Paint b = new Paint();
		b.setColor(Color.CYAN);
		c.drawCircle(xb, yb, 10, b);
		c.drawBitmap(playerBitmap, null, sprite, null);

		
	}

	

}
