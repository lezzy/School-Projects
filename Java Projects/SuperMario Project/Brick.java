package com.example.supermario;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Brick {
	private final int dimension, col, row;
	private Board board;
	Rect brick;
	
	public Brick(Board board, int row, int col, int tileDimension){
		this.board = board;
		this.row = row;
		this.col = col;
		this.dimension = tileDimension;
		brick = new Rect(0, 0, 100, 20);
	}
	
	private int getRelX() {
		return col * dimension;
	}
	
	private int getRelY() {
		return row * dimension;
	}
	public void draw(int x, int y, Canvas c) {
		
		brick.set(x + getRelX(), 800, 100 + x + getRelX(), 700);
		
			
		Paint p = new Paint();
		p.setColor(Color.GREEN);
		c.drawRect(brick, p);
	}

}
