package com.jikexueyuan.catchcrazycat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;


public class Playground extends SurfaceView{
		
	
	private static  int WIDTH = 40;
	private static final int ROW = 10;
	private static final int COL = 10;
	private static final int BLOCKS = 15;//默认添加的路障数量
	
	
	private Dot matrix[][];
	private Dot cat;

	public Playground(Context context) {
		super(context);
		getHolder().addCallback(callback);
		matrix = new Dot[ROW][COL];
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				matrix[i][j] = new Dot(j, i);
			}
		}
		initGame();
	}
	
	private Dot getDot(int x,int y) {
		return matrix[y][x];
	}


	private void redraw() {
		Canvas c = getHolder().lockCanvas();
		c.drawColor(Color.LTGRAY);
		Paint paint = new Paint();
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		for (int i = 0; i < ROW; i++) {
			int offset = 0;
			if (i%2 != 0) {
				offset = WIDTH/2;
			}
			for (int j = 0; j < COL; j++) {
				Dot one = getDot(j, i);
				switch (one.getStatus()) {
				case Dot.STATUS_OFF:
					paint.setColor(0xFFEEEEEE);
					break;
				case Dot.STATUS_ON:
					paint.setColor(0xFFFFAA00);
					break;
				case Dot.STATUS_IN:
					paint.setColor(0xFFFF0000);
					break;

				default:
					break;
				}
				c.drawOval(new RectF(one.getX()*WIDTH+offset, one.getY()*WIDTH, 
						(one.getX()+1)*WIDTH+offset, (one.getY()+1)*WIDTH), paint);
			}
			
		}
		getHolder().unlockCanvasAndPost(c);
	}
	
	Callback callback = new Callback() {
		
		@Override
		public void surfaceDestroyed(SurfaceHolder arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void surfaceCreated(SurfaceHolder arg0) {
			// TODO Auto-generated method stub
			redraw();
		}
		
		@Override
		public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			WIDTH = arg2/(COL+1);
		  redraw();
		}
	};
	
	private void initGame() {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				matrix[i][j].setStatus(Dot.STATUS_OFF);
			}
		}
		cat = new Dot(4, 5);
		getDot(4, 5).setStatus(Dot.STATUS_IN);
		for (int i = 0; i < BLOCKS;) {
			int x = (int) ((Math.random()*1000)%COL);
			int y = (int) ((Math.random()*1000)%ROW);
			if (getDot(x, y).getStatus() == Dot.STATUS_OFF) {
				getDot(x, y).setStatus(Dot.STATUS_ON);
				i++;
				//System.out.println("Block:"+i);
			}
		}
	}
	}


