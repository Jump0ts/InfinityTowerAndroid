package com.example.infinitetower;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class PantJuego extends AppCompatActivity {
    private Button btnD, btnI;
    private ImageButton btnS;
    private MediaPlayer musica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pant_juego);
        final Juego juego = new Juego(this, this);
        btnD = findViewById(R.id.btnDer);
        btnI = findViewById(R.id.btnIzq);
        btnS = findViewById(R.id.btnSalto);
        musica = MediaPlayer.create(getApplicationContext(),R.raw.derrota);

        LinearLayout pantJuego = findViewById(R.id.layoutJuego);
        pantJuego.addView(juego);

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                juego.saltar();
            }
        });

        btnD.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)juego.moverD();
                else if(event.getAction() == MotionEvent.ACTION_UP)juego.parar();
                return true;
            }
        });


        btnI.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)juego.moverI();
                else if(event.getAction() == MotionEvent.ACTION_UP)juego.parar();
                return true;
            }
        });
    }

    public void onBackPressed() {
        //Creo la alerta
        AlertDialog.Builder alerta = new AlertDialog.Builder(PantJuego.this);
        alerta.setMessage("¿Desea salir del juego?").setCancelable(false);
        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

                finish();
            }
        });
        alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog titulo = alerta.create();
        titulo.setTitle("Volver al Menú");
        titulo.show();
    }

    public void finJuego(final int punt){
        musica.start();
        AlertDialog.Builder alerta = new AlertDialog.Builder(PantJuego.this);
        alerta.setMessage("Su puntuación es de: "+punt+" punto/s.").setCancelable(false);
        alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                Puntuaciones.nuevaPuntuacion(punt);
                musica.stop();
                finish();
            }
        });
        AlertDialog titulo = alerta.create();
        titulo.setTitle("Fin Juego");
        titulo.show();
    }
}
