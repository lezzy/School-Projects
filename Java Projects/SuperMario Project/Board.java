package com.example.supermario;





import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Board implements OnTouchListener {
	
	private static Object context;
	private BoardView view;
	private boolean down, jump, back, forward;
	private Brick brick[][];
	private BrickHigh brickhigh[][];
	private Bigger bigger[][];
	private Tile tiles[][];
	private Coins coins[][];
	private final int boardHeight, boardWidth;
	public int displacementx;
	private static final int NUM_X_TILES = 100, NUM_Y_TILES = 20;
	private int boardX = 0, boardY = 0;
	Bitmap playerBitmap;
	Rect box;
	int y;


	public Board(BoardView view) {
		
		int tileDimension;
		boardHeight = view.getHeight() * 2;
		boardWidth = view.getHeight() * 10;
		tileDimension = boardHeight / NUM_Y_TILES;
		
		brickhigh = new BrickHigh[NUM_Y_TILES][NUM_X_TILES];
		brick = new Brick[NUM_Y_TILES][NUM_X_TILES];
		tiles = new Tile[NUM_Y_TILES][NUM_X_TILES];
		bigger = new Bigger[NUM_Y_TILES][NUM_X_TILES];
		coins = new Coins[NUM_Y_TILES][NUM_X_TILES];
		for (int row = 0; row < NUM_Y_TILES; row++) {
			for (int col = 0; col < NUM_X_TILES; col++) {
				tiles[row][col] = new Tile(this, row, col, tileDimension);
				brick[row][col] = new Brick(this, row, col, tileDimension);
				brickhigh[row][col] = new BrickHigh(this, row, col, tileDimension);
				bigger[row][col]= new Bigger(this, row, col, tileDimension);
				coins[row][col]= new Coins(this, row, col, tileDimension);
				
				}
		}
		

	}
	

	public void draw(Canvas c) {
		c.drawColor(Color.BLUE);
		for (int i = 0; i < NUM_Y_TILES; i++){
			for (int j = 0; j < NUM_X_TILES; j++){
				tiles[i][j].draw(boardX, boardY, c);
				
			}
		}
		for (int k = 0; k < NUM_Y_TILES; k++){
			for (int l = 4; l < 9 ; l++){
				brick[k][l].draw(boardX, boardY, c);
			}
		}
		
		for (int m = 0; m < NUM_Y_TILES; m++){
			for (int n = 3; n < 80 ; n+=8){
				brickhigh[m][n].draw(boardX, boardY, c);
			}
		}
		for (int o = 3; o < NUM_X_TILES; o+=16)
			bigger[5][o].draw(boardX, boardY, c);
		
		for (int p = 4; p < 7; p++)
			for (int q = 4; q < 11; q++)
				coins[p][q].draw(boardX, boardY, c);
		
	
		BoardView.draw(boardX, y, c);
		
		
	}
	
	public void takeStep() {
		
		//if (forward == true){
			//boardX -= 20; //this moves view to the right
			
			//increase mario's x-coordinate as well
		//}
		
		//else if(back == true){
			//boardX += 20;
			//moves board's view to the left
			//also change mario's coordinates 
		//}
			
		//else if (jump == true){
			//y += 20;
			//set mario's y = plus 1 tile, x = plus 1 tile
		//}
		
		
		//boardX -= 5;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // if the screen is being touched
			down = true;
			// if the touch is to the left or right of the screen
			if (event.getX() > 360){
				boardX -= 20;
				forward = true;
				jump = false;
				back = false;
			}
			else if (event.getX() < 360){
				boardX +=20;
				back = true;
				jump = false;
				forward = false;
			}
			else if (event.getY() > 640){
				y = 100;
				jump = true;
				back = false;
				forward = false;
			} 
			return true;
		case MotionEvent.ACTION_MOVE: // screen is touched and moves
			if (event.getX() < boardWidth/2){
				back = true;
			}
			else if (event.getX() > boardWidth/2){
				forward = true;
			}
			return true;
		case MotionEvent.ACTION_UP: // finger is lifted
			down = false;
			return true;
		}
		return false;
	}	
}
