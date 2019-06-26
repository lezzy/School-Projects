package com.example.qbert;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class QBertThread extends Thread{
	QBert q;
	public QBertThread(QBert q){
		this.q = q;
	}
	
	public void run(){
		while(true){
			SurfaceHolder sh = q.getHolder();
			Canvas c = sh.lockCanvas();
			//q.ProcessInput();
			if (c != null){
				q.draw(c);
				//q.draw(c);
				sh.unlockCanvasAndPost(c);
			} 
			else {
				try {
					sleep(100);
				} 
				catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}


