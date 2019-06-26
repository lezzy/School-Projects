package com.example.supermario;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


public class Tile {
	private final int dimension, col, row;
	private Board board;
	Rect ground;

	
	public Tile(Board board, int row, int col, int tileDimension) {
		this.board = board;
		this.row = row;
		this.col = col;
		this.dimension = tileDimension;
		ground = new Rect(0, 0, 100, 20);
	}
	
	private int getRelX() {
		return col * dimension;
	}
	
	private int getRelY() {
		return row * dimension;
	}
	public void draw(int x, int y, Canvas c) {
		ground.set(0 + x + getRelX(), 1000, 100 + x + getRelX(), 900);
		Paint p = new Paint();
		p.setColor(Color.GREEN);
		c.drawRect(ground, p);
		
		
		
	
		// x + getRelX();
		// y + getRelY();
		// board.getWidth();
		// board.getHeight();
	}
}
