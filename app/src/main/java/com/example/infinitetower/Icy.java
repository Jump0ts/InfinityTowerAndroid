package com.example.infinitetower;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

public class Icy {
    private static final int BMP_ROWS = 6;
    private static final int BMP_COLUMNS = 4;
    private final int ALTSALTO = 340;
    private final int BASE = 905;
    private int x = 0;
    private int y = 0;
    private int xSpeed = 0;
    private int ySpeed = 10;
    private int punt = 0;
    private Bitmap bmp;
    private Juego vista;
    private Rect rectanguloPJ;
    private int currentFrame = 0;
    private int width;
    private int height;
    private int[] DIRECTION_TO_ANIMATION_MAP = {5,2,0,1,3,4};
    //animation = 5 salto ver, 2 izq, 1 der, 0 estatico, 3 salto der, 4 salto izq
    private Boolean salto = false, saltoP = false, bajar = false;
    private int base;
    private ArrayList<Plataforma> plats;



    public Icy(Juego vista, Resources res) {
        this.bmp = BitmapFactory.decodeResource(res, R.drawable.sprite_icy);
        this.vista = vista;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.x = vista.getWidth()/2;
        this.y = BASE;
    }

    public void moverD(){
        xSpeed = 10;
    }

    public void moverI(){
        xSpeed = -10;
    }

    public void parar(){
        xSpeed = 0;
    }

    public void onDraw(Canvas canvas, ArrayList<Plataforma> plataformas){
        int srcY = 0, srcX = 0;
        plats = plataformas;

        //movimientos laterales y saltos
        if(salto == false && saltoP == false) {
            andar();
            srcY = getAnimationRow() * height;
            srcX = currentFrame * width;

        }
        else if(saltoP == false  && salto == true){
            srcY = DIRECTION_TO_ANIMATION_MAP[0] * height;
            srcX = 0% BMP_COLUMNS;
            saltoV();
        }
        else if(saltoP == true && salto == false){

            srcY = getAnimationRow() * height;
            srcX = saltoP();

            if(xSpeed == 0){
                srcY = DIRECTION_TO_ANIMATION_MAP[0] * height;
                srcX = 0% BMP_COLUMNS;
            }
        }

        //caida();



        Rect src = new Rect(srcX, srcY, srcX+width, srcY+height);
        rectanguloPJ = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp,src,rectanguloPJ,null);
    }

    private void andar(){
        if(x> vista.getWidth()-width-xSpeed || x+xSpeed<0){
            xSpeed = 0;
        }
        if(y!=BASE)isOver();
        x = x+xSpeed;
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    private void saltoV(){
        boolean over = false;

        /*if(ySpeed>0){

            over = isOver();
        }*/
        if(y== BASE || aproxSalto() || y+ySpeed<0){
            ySpeed = -ySpeed;
        }
        y = y+ySpeed;
        System.out.println(y);
        if(y==base || isOver() == true)salto = false;

    }

    private int saltoP(){
        boolean over = false;

        //deteccion de colision con plataformas
        if(ySpeed>0){
            over = isOver();
        }

        if(aproxSalto() ||y == BASE|| y+ySpeed<0 ){
            ySpeed = -ySpeed;

        }


        if(over==false)y = y+ySpeed;

        if(y == BASE || over == true){
            saltoP = false;

        }
        if(x> vista.getWidth()-width-xSpeed || x+xSpeed<0){
            xSpeed = -xSpeed;
        }
        x = x+xSpeed;

        //cambiar sprites en salto parabolico
        if(y== base-ALTSALTO){
            if(xSpeed>0)return 220;
            else return 99;
        }
        if(ySpeed>0){
            if(xSpeed>0)return 320;
            else return 0;
        }
        if(ySpeed<0){
            if(xSpeed>0)return 120;
            else return 220;
        }

        return 0;
    }


    private boolean aproxSalto(){
        if(y == base-ALTSALTO || y == base-ALTSALTO+5 || y == base-ALTSALTO-5)return true;
        else return false;
    }

    private boolean isOver(){

        for(Plataforma plat: plats){
            if(y+height==plat.getY() && ((x>=plat.getX() && x<=plat.getX()+plat.getWidth())||(x+width>=plat.getX() && x+width<=plat.getX()+plat.getWidth()))==true){
                saltoP = false;
                ySpeed = -ySpeed;
                bajar = true;
                return true;
            }


        }
        saltoP=true;
        return false;
    }

    public void baja(){
        if(saltoP == false && salto == false){
            y+=5;
            punt+=5;
        }
    }

    public void saltos(){
        if(xSpeed==0)jumpP();
        else jumpP();
    }

    private void jumpP(){
        if(salto != true && saltoP != true){
            base = y;
            saltoP = true;
            currentFrame = 2;
            currentFrame = 1 % BMP_COLUMNS;
        }
    }



    private int getAnimationRow(){
        int direccion;
        if(xSpeed<0){
            if(saltoP==false)direccion = 1;
            else direccion = 5;
        }
        else if(xSpeed>0){
            if(saltoP == false)direccion = 3;
            else direccion = 4;
        }
        else direccion = 2;
        return DIRECTION_TO_ANIMATION_MAP[direccion];
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

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    public int getHeight(){
        return height;
    }

    public int getPunt(){
        return punt;
    }

    public boolean getBajar(){
        return bajar;
    }
}
