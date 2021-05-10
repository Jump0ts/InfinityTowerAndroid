package com.example.infinitetower;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private Button jugar, record;
    private MediaPlayer musica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jugar = findViewById(R.id.btnJugar);
        record = findViewById(R.id.btnRecord);
        musica = MediaPlayer.create(getApplicationContext(),R.raw.menu);
        musica.start();
        Puntuaciones.setArchivo(getSharedPreferences("puntuaciones", Context.MODE_PRIVATE));
        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PantJuego.class);
                startActivity(intent);
                musica.stop();
                finish();
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Puntuaciones.class);
                startActivity(intent);
                musica.stop();
                finish();
            }
        });


    }
    public void onBackPressed(){
        //Creo la alerta
        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
        alerta.setMessage("¿Desea salir del juego?").setCancelable(false);
        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                musica.stop();
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
        titulo.setTitle("Cerrar juego");
        titulo.show();
    }
}
