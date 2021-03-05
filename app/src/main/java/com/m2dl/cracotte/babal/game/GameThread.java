package com.m2dl.cracotte.babal.game;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.SurfaceHolder;

public class GameThread extends Thread{
    private final SurfaceHolder surfaceHolder;
    private final GameView gameView;
    private final Handler mHandler;

    private boolean running;
    private Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
        this.mHandler = new Handler();
    }


    @SuppressLint("ShowToast")
    @Override
    public void run() {
        if (running) {
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                //Toast.makeText(this.gameView.getContext(), "Une erreur est survenue", Toast.LENGTH_LONG);
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                mHandler.postDelayed(this, 1000/60);
            }
        }
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }
}
