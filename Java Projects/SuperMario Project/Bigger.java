package com.example.supermario;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Bigger {
	private final int dimension, col, row;
	private Board board;
	
	
	public Bigger(Board board, int row, int col, int tileDimension){
		this.board = board;
		this.row = row;
		this.col = col;
		this.dimension = tileDimension;;
	}
	
	private int getRelX() {
		return col * dimension;
	}
	
	private int getRelY() {
		return row * dimension;
	}
	public void draw(int x, int y, Canvas c) {
		int r = 20;
		
		
			
		Paint p = new Paint();
		p.setColor(Color.DKGRAY);
		c.drawCircle(x + getRelX() + 50, 480, r, p);
	}

}
