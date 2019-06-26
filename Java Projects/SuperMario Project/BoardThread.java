package com.example.supermario;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class BoardThread extends Thread{
	final SurfaceHolder sh;
	final Board board;
	private boolean gameShouldStop = false;
	public BoardThread(SurfaceHolder sh, Board board) {
		this.sh = sh;
		this.board = board;
	}
	
	@Override
	public void run() {
		while (!shouldStop()) {
			Canvas c = sh.lockCanvas();
			if (c != null) {
				board.takeStep();
				board.draw(c);
				sh.unlockCanvasAndPost(c);
			} else {
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public synchronized void stopGame() {
		gameShouldStop = true;
	}
	
	private synchronized boolean shouldStop() {
		return gameShouldStop;
	}
}

