package game;

import android.graphics.Canvas;
import android.os.Handler;
import android.view.SurfaceHolder;

import static java.lang.Thread.sleep;

public class GameThread extends Thread{
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    private Canvas canvas;
    private Handler mHandler;

    private Runnable mUpdateTimeTask = new Runnable() {
        @Override
        public void run() {
            mHandler.postDelayed(this, 1000);
        }
    };

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;

        mHandler = new Handler();

    }

    @Override
    public void run() {
        while (running) {
            canvas = null;

            try {
                sleep(50);
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    mHandler.postDelayed(gameView::update, 100);
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {}
            finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                mHandler.postDelayed(this::run, 100);
            }
        }
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }
}
