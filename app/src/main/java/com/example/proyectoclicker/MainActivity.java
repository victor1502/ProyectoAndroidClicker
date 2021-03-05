package com.example.proyectoclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import static android.icu.text.MessagePattern.ArgType.SELECT;
import static com.example.proyectoclicker.Variables.dinero;
import static com.example.proyectoclicker.Variables.multiplicador;
import static com.example.proyectoclicker.Variables.suma;
import static com.example.proyectoclicker.Variables.tiempo;

public class MainActivity extends AppCompatActivity {


    Calendar fechaAbrir, fechaCerrar;
    long tiempoAbrir, tiempoCerrar;
    TextView textoDinero, textoLogin;
    static Thread hilo;
    Handler h = new Handler();
    View viewClick;
    Boolean continuar, hilillo = true;
    CrearBDLogin crearLogin;
    SQLiteDatabase dbLogin;
    String nombrePreferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        textoLogin = findViewById(R.id.textLogin);
        crearLogin = new CrearBDLogin(this, "DBLogin",null,1);
        dbLogin = crearLogin.getReadableDatabase();

        Cursor c = dbLogin.rawQuery("SELECT Username FROM Cuenta WHERE Iniciada = 1",null);
        if(c != null && c.getCount() > 0){
            c.moveToFirst();
            Log.i("cursor", ""+c.getString(0));
            textoLogin.setText(c.getString(0));
            nombrePreferencia = "Preferencia"+c.getString(0);
        }
        else{
            Log.i("curso fallo", "no devuleve nada");
            nombrePreferencia = "MisPreferencias";
        }

        Log.i("DineroAntes", dinero+"");
        SharedPreferences pref = getSharedPreferences(nombrePreferencia, Context.MODE_PRIVATE);

        textoDinero = findViewById(R.id.textoOro);
        tiempoCerrar = pref.getLong("tiempo",0);
        dinero = pref.getInt("dinero",0);
        suma = pref.getFloat("suma",1);
        multiplicador = pref.getFloat("multiplicador",1);
        Log.i("dineroCreate",dinero+"");

        calendario(tiempoCerrar);
        dinero = dinero + ((int)suma * tiempo);

        textoDinero.setText("Oro: "+dinero+"$");

        viewClick = findViewById(R.id.viewClicks);
        continuar = true;

        if(hilillo){
            Log.i("Hilo", "creo hilo");
            HiloSumar(viewClick);
            hilillo = false;
        }

    }

    public void clickSumar(View view) {
        dinero += Math.round(suma * multiplicador);
        textoDinero.setText("Oro: "+dinero+"$");

    }

    public void botonMejoras(View view) {
        Intent intent = new Intent(getApplicationContext(), ActivityUpgrades.class);
        startActivity(intent);
    }

    public void botonConfig(View view) {
        Intent intent = new Intent(getApplicationContext(), ActivityConfig.class);
        startActivity(intent);
    }

    public void guardar(){
        fechaCerrar = Calendar.getInstance();
        SharedPreferences pref = getSharedPreferences(nombrePreferencia, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("dinero",dinero);
        editor.putFloat("suma",suma);
        editor.putFloat("multiplicador",multiplicador);
        editor.putLong("tiempo",fechaCerrar.getTimeInMillis());
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("stop", "Stop");
        continuar = false;
        guardar();
    }

    public void calendario(long tiempoCerrar){
        fechaAbrir = Calendar.getInstance();
        tiempoAbrir = fechaAbrir.getTimeInMillis();
        if(tiempoCerrar > 0){

            tiempo = (int) ((tiempoAbrir - tiempoCerrar)/1000);
            Log.i("tiempo", ""+tiempo);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("dineroResume",dinero+"");
        textoDinero.setText("Oro: "+dinero+"$");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("restart","Restart");
    }

    private void HiloSumar(View view){
        hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                while(continuar){
                    try {
                        Thread.sleep(1000);
                        dinero += suma;

                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                textoDinero.setText("Oro: "+dinero+"$");
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        hilo.start();
    }
}