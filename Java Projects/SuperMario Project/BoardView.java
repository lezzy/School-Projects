package com.example.supermario;


import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BoardView extends SurfaceView implements SurfaceHolder.Callback{
	
	private BoardThread thread;
	private Board board;
	static Rect box;
	static Bitmap playerBitmap;


	public BoardView(Context context) {
		super(context);
		
		playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.supermariosprite);
		box = new Rect(0, 400, 100, 500);
		
		setFocusable(true);
		this.getHolder().addCallback((SurfaceHolder.Callback) this);
	}

	//@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void surfaceCreated(SurfaceHolder holder) {
		System.out.println("Surface created");
		board = new Board(this);
		setOnTouchListener(board);
		thread = new BoardThread(this.getHolder(), board);
		thread.start();
	}

	//@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		System.out.println("Surface destroyed");
		thread.stopGame();
		try {
			thread.join(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (thread.isAlive()) {
				System.err.println("Error: thead did not stop in time");
			}
		}
	}

	
	public static void draw(int x, int y, Canvas c){
		box.set(-x, 800 + y, 100 - x, 900 + y);
		c.drawBitmap(playerBitmap, null, box, null);
	}

}
