package com.example.qbert;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.SurfaceView;

public class Cube extends SurfaceView {
	public Cube(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	int i, length = 45, angle = 20;
	float x = 50, y = 600;
	
	public Path RedSide() {
		Path p = new Path();

		// Move to the "top" of the triangle
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
		Path q = new Path();
		q.moveTo(x+ length, y + angle);

		
		q.rLineTo(0, length);
		q.rLineTo(length , -angle);
		q.rLineTo(0, -length);
		for (i = 0; i < 6; i++){
			q.rMoveTo(length, angle);
			q.rLineTo(0, length);
			q.rLineTo(length , -angle);
			q.rLineTo(0, -length);
			
		}
		q.rMoveTo(-length, -angle - length);
		q.rLineTo(0, length);
		q.rLineTo(-length, angle);
		q.rLineTo(0, -length);
		for (i = 0; i < 5; i++){
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
		Path r = new Path();
		r.moveTo(x, y );

		r.rLineTo(length , angle);
		r.rLineTo(length , -angle);
		r.rLineTo(-length, -angle);
		for (i = 0; i < 6; i++){
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
	public void draw(Canvas c){
		c.drawColor(Color.BLACK);
		Paint g = new Paint();
		Paint g1 = new Paint();
		Paint g2 = new Paint();
		g.setColor(Color.RED);
		g1.setColor(Color.WHITE);
		g2.setColor(Color.BLUE);
		c.drawPath(RedSide(), g);
		c.drawPath(WhiteSide(), g1);
		c.drawPath(BlueSide(), g2);
		
	}

}

	