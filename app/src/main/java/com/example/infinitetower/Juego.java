package com.example.infinitetower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Juego extends SurfaceView implements SurfaceHolder.Callback {

    private final int NPLATAFORMAS = 8;
    private int punt = 0;
    private MediaPlayer musica;
    private Hilo hilo;
    private Icy sprite;
    private Bitmap platB;
    private int yBase = 0;
    private ArrayList<Plataforma> plataformas;
    private ArrayList<Bitmap> fondos;
    private boolean emp = true;
    private PantJuego pantalla;
    private boolean caido = false;


    public Juego(Context context, PantJuego pantalla) {
        super(context);
        setBackgroundColor(Color.BLUE);
        getHolder().addCallback(this);
        musica = MediaPlayer.create(context,R.raw.juego);
        this.pantalla = pantalla;
        plataformas = new ArrayList<Plataforma>();
        creaPlataformas();
        platB = BitmapFactory.decodeResource(getResources(),R.drawable.plat_base);
        fondos = new ArrayList<Bitmap>();
        inicializarFondos();
    }

    private void inicializarFondos(){
        for(int i = 0; i<5; i++){
            fondos.add(BitmapFactory.decodeResource(getResources(),R.drawable.fondo));
        }
    }

    private void creaPlataformas(){
        for(int i = 0; i<=NPLATAFORMAS;i++) {
            plataformas.add(new Plataforma(this,getResources()));
        }
    }

    private void primerasPlat(){
        plataformas.get(0).inicializarPlat(new Plataforma(sprite.getX()+150,sprite.getY()+150));

        for(int i = 1; i<plataformas.size();i++){
            plataformas.get(i).inicializarPlat(plataformas.get(i-1));
        }

        emp = false;
    }

    public void moverD(){
        sprite.moverD();
    }

    public void moverI(){
        sprite.moverI();
    }

    public void parar(){
        sprite.parar();
    }

    public void saltar(){
        sprite.saltos();
    }

    private void avanza(){
        for(int i = 0; i<plataformas.size();i++){
            plataformas.get(i).baja();
        }
        sprite.baja();
        yBase+=5;
    }

    private void pintaFondo(Canvas canvas, boolean prim){
        int x = 0;
        for(int i = 0; i<fondos.size();i++){
            if(i==0 && prim==true){
                x = 0;
                canvas.drawBitmap(fondos.get(i),0,0,null);
            }
            else if(i==0){
                x = x-fondos.get(fondos.size()-1).getHeight();
                canvas.drawBitmap(fondos.get(i),0,x,null);
            }
            else {

                x = x - fondos.get(i - 1).getHeight();
                canvas.drawBitmap(fondos.get(i), 0, x, null);
            }
        }
    }

    private void compBajaPlat(){
        for(int i = 0; i<plataformas.size();i++){
            if(plataformas.get(i).getY()>1200){
                if(i == 0)plataformas.get(i).repaint(plataformas.get(plataformas.size()-1));
                else plataformas.get(i).repaint(plataformas.get(i-1));
            }
        }
    }

    private void gameOver(){
        if(caido == false) {
            caido = true;
            pantalla.finJuego(sprite.getPunt());
        }
    }

    @Override
    public void onDraw(Canvas canvas){
        if(emp == true){
            canvas.drawColor(Color.TRANSPARENT);
            primerasPlat();
            pintaFondo(canvas,true);
            yBase = 850+sprite.getHeight();
        }

        if(sprite.getBajar() == true){
            avanza();
        }

        if(sprite.getY() > 1400)gameOver();

        compBajaPlat();

        //pintaFondo(canvas,false);

        canvas.drawBitmap(platB,0,yBase,null);
        for(int i = 0; i<plataformas.size();i++){
            canvas.drawBitmap(plataformas.get(i).getBmp(),plataformas.get(i).getX(),plataformas.get(i).getY(),null);
        }
        sprite.onDraw(canvas, plataformas);




    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {


        musica.start();

        sprite = new Icy(this,getResources());
        hilo = new Hilo(getHolder(), this);
        hilo.setRunning(true);
        hilo.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        musica.stop();

        boolean retry = true;
        hilo.setRunning(false);

        while(retry){
            try{
                hilo.join();
                retry = false;
            }catch (InterruptedException e){

            }
        }
    }
}
