package com.example.supermario;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Coins {
	private final int dimension, col, row;
	private Board board;
	RectF coins;
	
	
	public Coins(Board board, int row, int col, int tileDimension){
		this.board = board;
		this.row = row;
		this.col = col;
		this.dimension = tileDimension;;
		coins = new RectF(0, 0, 20, 20);
	}
	
	private int getRelX() {
		return col * dimension;
	}
	
	private int getRelY() {
		return row * dimension;
	}
	public void draw(int x, int y, Canvas c) {
		coins.set(x + getRelX() + 40, y + getRelY() - 40, x+ getRelX() + 60, y + getRelY());
		
			
		Paint p = new Paint();
		p.setColor(Color.YELLOW);
		c.drawOval(coins, p);
	}

}
