package com.example.infinitetower;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class Plataforma {
    private Bitmap bmp;
    private int x, y;
    private Juego vista;
    private int width, height;
    private final int DISTP = 165;
    private static boolean primera = true;

    public Plataforma(Juego vista, Resources res) {
        this.vista = vista;
        this.bmp = BitmapFactory.decodeResource(res,R.drawable.plataforma);
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
    }

    public Plataforma(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void repaint(Plataforma plat){
        Random rnd = new Random();
        y = plat.getY()-DISTP;
        x = rnd.nextInt(vista.getWidth()-width);
    }

    public void inicializarPlat(Plataforma plat){
        Random rnd = new Random();
        if(primera == true){
            primera = false;
            y = plat.getY()-175;
        }
        else y = plat.getY()-DISTP;
        x = rnd.nextInt(vista.getWidth()-width);

    }

    public void baja(){
        y = y+5;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }
}
