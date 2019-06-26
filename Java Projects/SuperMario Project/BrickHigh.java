package com.example.supermario;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class BrickHigh {
	private final int dimension, col, row;
	private Board board;
	Rect brickhigh;
	
	public BrickHigh(Board board, int row, int col, int tileDimension){
		this.board = board;
		this.row = row;
		this.col = col;
		this.dimension = tileDimension;
		brickhigh = new Rect(0, 0, 100, 20);
	}
	
	private int getRelX() {
		return col * dimension;
	}
	
	private int getRelY() {
		return row * dimension;
	}
	public void draw(int x, int y, Canvas c) {
		
		brickhigh.set(x + getRelX(), 600, 100 + x + getRelX(), 500);
		
			
		Paint p = new Paint();
		p.setColor(Color.GREEN);
		c.drawRect(brickhigh, p);
	}


}
