package com.example.infinitetower;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Puntuaciones extends AppCompatActivity {

    private ListView lista;
    private static SharedPreferences archivo = null;
    private MediaPlayer musica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        musica = MediaPlayer.create(getApplicationContext(),R.raw.victoria);
        musica.start();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuaciones2);

        lista = findViewById(R.id.listaPunt);

        lista.setAdapter(new ArrayAdapter<String>(Puntuaciones.this,android.R.layout.simple_spinner_dropdown_item,getPunt()));
    }

    public static void setArchivo(SharedPreferences preferences){
        archivo = preferences;
    }

    private ArrayList<String> getPunt(){
        ArrayList<String> puntuaciones = new ArrayList<String>();

        puntuaciones.add("TOP 1: "+archivo.getInt("top1", 0));
        puntuaciones.add("TOP 2: "+archivo.getInt("top2", 0));
        puntuaciones.add("TOP 3: "+archivo.getInt("top3", 0));

        return puntuaciones;
    }

    public static void nuevaPuntuacion(int punt){
        //Actualizo las puntuaciones, haciendo que las puntuaciones ya puestas se muevan para abajo o no según donde tenga que meter la nueva
        if(archivo.getInt("top1", 0)<punt){
            archivo.edit().putInt("top3", archivo.getInt("top2", 0)).apply();
            archivo.edit().putInt("top2", archivo.getInt("top1", 0)).apply();
            archivo.edit().putInt("top1", punt).apply();
        }
        else if(archivo.getInt("top2", 0)<punt){
            archivo.edit().putInt("top3", archivo.getInt("top2", 0)).apply();
            archivo.edit().putInt("top2", punt).apply();
        }
        else if(archivo.getInt("top3", 0)<punt)archivo.edit().putInt("top3", punt).apply();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        musica.stop();
        finish();
    }
}
