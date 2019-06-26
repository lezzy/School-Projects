package com.example.defenderfinal;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class DefenderThread extends Thread {
	Defender d;
	boolean gameStillRunning = true;
	public DefenderThread(Defender d){
		this.d=d;
	}
	
	public void run(){
		while(gameStillRunning){
			SurfaceHolder sh = d.getHolder();
			Canvas c = sh.lockCanvas();
			//d.ProcessInput();
			d.UpdateWorld();
			if (c != null){
				d.draw(c);
				sh.unlockCanvasAndPost(c);
			} else {
				try {
					sleep(100);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}

