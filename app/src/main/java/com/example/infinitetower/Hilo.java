package com.example.infinitetower;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class Hilo extends Thread {

        private SurfaceHolder hold;
        private Juego scr;
        private boolean run;
        private final long FPS = 30; //Nuevo Sprite

        public Hilo(SurfaceHolder hold, Juego scr){
            this.hold=hold;
            this.scr=scr;
            this.run=false;
        }

        public void setRunning(boolean run){
            this.run=run;
        }

        public void run(){
            Canvas cv;
            long ticksPS = 1000/FPS;
            long startTime;
            long sleepTime;
            while (run){
                cv = null;
                startTime = System.currentTimeMillis();
                try {
                    cv = hold.lockCanvas();
                    if (cv != null) {
                        synchronized (hold) {
                            scr.postInvalidate();
                        }
                    }
                } finally {
                    if (cv != null) hold.unlockCanvasAndPost(cv);
                }
                sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
                try{
                    if(sleepTime>0)sleep(sleepTime);
                    else sleep(10);
                }catch (Exception e){

                }
            }
        }



    }


