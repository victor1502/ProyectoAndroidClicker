package com.example.proyectoclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static com.example.proyectoclicker.Variables.dinero;
import static com.example.proyectoclicker.Variables.multiplicador;
import static com.example.proyectoclicker.Variables.suma;
import static com.example.proyectoclicker.Variables.upgrade1;
import static com.example.proyectoclicker.Variables.upgrade2;
import static com.example.proyectoclicker.Variables.upgrade3;
import static com.example.proyectoclicker.Variables.upgrade4;
import static com.example.proyectoclicker.Variables.upgrade5;
import static com.example.proyectoclicker.Variables.upgrade6;

public class ActivityUpgrades extends AppCompatActivity {
    CrearBDLogin crearLogin;
    SQLiteDatabase dbLogin;
    String nombrePreferencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrades);

        crearLogin = new CrearBDLogin(this, "DBLogin",null,1);
        dbLogin = crearLogin.getReadableDatabase();

        Cursor c = dbLogin.rawQuery("SELECT Username FROM Cuenta WHERE Iniciada = 1",null);
        if(c != null && c.getCount() > 0){
            c.moveToFirst();
            Log.i("cursor", ""+c.getString(0));
            nombrePreferencia = "Preferencia"+c.getString(0);
        }
        else{
            Log.i("curso fallo", "no devuleve nada");
            nombrePreferencia = "MisPreferencias";
        }

        TextView texto = findViewById(R.id.textView);
        TextView textoUpgrade1 = findViewById(R.id.textCoste1);
        TextView textoUpgrade2 = findViewById(R.id.textCoste2);
        TextView textoUpgrade3 = findViewById(R.id.textCoste3);
        TextView textoUpgrade4 = findViewById(R.id.textCoste4);
        TextView textoUpgrade5 = findViewById(R.id.textCoste5);
        TextView textoUpgrade6 = findViewById(R.id.textCoste6);
        SharedPreferences pref = getSharedPreferences(nombrePreferencia, Context.MODE_PRIVATE);
        upgrade1 = pref.getInt("upgrade1",20);
        upgrade2 = pref.getInt("upgrade2",200);
        upgrade3 = pref.getInt("upgrade3",800);
        upgrade4 = pref.getInt("upgrade4",2200);
        upgrade5 = pref.getInt("upgrade5",15000);
        upgrade6 = pref.getInt("upgrade6",99000);
        texto.setText("Oro: "+dinero+"$");
        textoUpgrade1.setText(upgrade1+"$");
        textoUpgrade2.setText(upgrade2+"$");
        textoUpgrade3.setText(upgrade3+"$");
        textoUpgrade4.setText(upgrade4+"$");
        textoUpgrade5.setText(upgrade5+"$");
        textoUpgrade6.setText(upgrade6+"$");
    }

    public void botonVolver(View view) {
        SharedPreferences pref = getSharedPreferences(nombrePreferencia, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("dinero",dinero);
        editor.putFloat("suma",suma);
        editor.putFloat("multiplicador",multiplicador);
        editor.putInt("upgrade1",upgrade1);
        editor.putInt("upgrade2",upgrade2);
        editor.putInt("upgrade3",upgrade3);
        editor.putInt("upgrade4",upgrade4);
        editor.putInt("upgrade5",upgrade5);
        editor.putInt("upgrade6",upgrade6);
        editor.commit();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void upgrade1Click(View view) {
        if(dinero >= upgrade1)
        {
            dinero -= upgrade1;
            multiplicador = (float) (multiplicador * 1.06);

            TextView texto = findViewById(R.id.textView);
            texto.setText("Oro: "+dinero+"$");

            upgrade1 = (int) Math.round(upgrade1 * 1.3);
            TextView textoUpgrade1 = findViewById(R.id.textCoste1);
            textoUpgrade1.setText(upgrade1+"$");
        }
    }

    public void upgrade2Click(View view) {
        if(dinero >= upgrade2)
        {
            dinero -= upgrade2;
            suma += 0.12;

            TextView texto = findViewById(R.id.textView);
            texto.setText("Oro: "+dinero+"$");

            upgrade2 = (int) Math.round(upgrade2 * 1.3);
            TextView textoUpgrade2 = findViewById(R.id.textCoste2);
            textoUpgrade2.setText(upgrade2+"$");
        }
    }

    public void upgrade3Click(View view) {
        if(dinero >= upgrade3)
        {
            dinero -= upgrade3;
            suma += 0.12;

            TextView texto = findViewById(R.id.textView);
            texto.setText("Oro: "+dinero+"$");

            upgrade3 = (int) Math.round(upgrade3 * 1.3);
            TextView textoUpgrade3 = findViewById(R.id.textCoste3);
            textoUpgrade3.setText(upgrade3+"$");
        }
    }

    public void upgrade4Click(View view) {
        if(dinero >= upgrade4)
        {
            dinero -= upgrade4;
            suma += 0.12;

            TextView texto = findViewById(R.id.textView);
            texto.setText("Oro: "+dinero+"$");

            upgrade4 = (int) Math.round(upgrade4 * 1.3);
            TextView textoUpgrade4 = findViewById(R.id.textCoste4);
            textoUpgrade4.setText(upgrade4+"$");
        }
    }

    public void upgrade5Click(View view) {
        if(dinero >= upgrade5)
        {
            dinero -= upgrade5;
            suma += 0.12;

            TextView texto = findViewById(R.id.textView);
            texto.setText("Oro: "+dinero+"$");

            upgrade5 = (int) Math.round(upgrade5 * 1.3);
            TextView textoUpgrade5 = findViewById(R.id.textCoste5);
            textoUpgrade5.setText(upgrade5+"$");
        }
    }

    public void upgrade6Click(View view) {
        if(dinero >= upgrade6)
        {
            dinero -= upgrade6;
            suma += 0.12;

            TextView texto = findViewById(R.id.textView);
            texto.setText("Oro: "+dinero+"$");

            upgrade6 = (int) Math.round(upgrade6 * 1.3);
            TextView textoUpgrade6 = findViewById(R.id.textCoste6);
            textoUpgrade6.setText(upgrade6+"$");
        }
    }
}