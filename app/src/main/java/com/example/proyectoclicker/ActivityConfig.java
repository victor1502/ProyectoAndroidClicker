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
import android.widget.EditText;
import android.widget.Toast;

import static com.example.proyectoclicker.Variables.dinero;
import static com.example.proyectoclicker.Variables.multiplicador;
import static com.example.proyectoclicker.Variables.suma;
import static com.example.proyectoclicker.Variables.tiempo;
import static com.example.proyectoclicker.Variables.upgrade1;
import static com.example.proyectoclicker.Variables.upgrade2;
import static com.example.proyectoclicker.Variables.upgrade3;
import static com.example.proyectoclicker.Variables.upgrade4;
import static com.example.proyectoclicker.Variables.upgrade5;
import static com.example.proyectoclicker.Variables.upgrade6;

public class ActivityConfig extends AppCompatActivity {

    CrearBDLogin crearLogin;
    SQLiteDatabase dbLogin;
    String nombrePreferencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        crearLogin = new CrearBDLogin(this, "DBLogin",null,1);
        dbLogin = crearLogin.getWritableDatabase();
    }

    public void reset(View view) {
        crearLogin = new CrearBDLogin(this, "DBLogin",null,1);
        dbLogin = crearLogin.getReadableDatabase();

        Cursor c = dbLogin.rawQuery("SELECT Username FROM Cuenta WHERE Iniciada = 1",null);
        if(c != null && c.getCount() > 0){
            c.moveToFirst();
            nombrePreferencia = "Preferencia"+c.getString(0);
        }
        else{
            Log.i("curso fallo", "no devuleve nada");
            nombrePreferencia = "MisPreferencias";
        }
        SharedPreferences pref = getSharedPreferences(nombrePreferencia, Context.MODE_PRIVATE);
        pref.edit().clear().commit();
        dinero = 0;
        multiplicador = 1;
        suma = 1;
        tiempo = 0;
        upgrade1 = 20;
        upgrade2 = 200;
        upgrade3 = 800;
        upgrade4 = 2200;
        upgrade5 = 15000;
        upgrade6 = 99000;
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void crearLogin(View view) {
        EditText textoUser = findViewById(R.id.editTextTextPersonName);
        String username = textoUser.getText().toString();

        EditText textoPassword = findViewById(R.id.editTextTextPassword);
        String password = textoPassword.getText().toString();

        Cursor c = dbLogin.rawQuery("SELECT * FROM Cuenta WHERE Username = '"+username+"' AND Password = '"+password+"'",null);

        if(!c.moveToFirst()){
            dbLogin.execSQL("INSERT INTO Cuenta(Username, Password, Iniciada) VALUES ('"+username+"','"+password+"', 0)");
            SharedPreferences pref = getSharedPreferences("Preferencia"+username, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("dinero",0);
            editor.putFloat("suma",1);
            editor.putFloat("multiplicador",1);
            editor.putLong("tiempo",0);
            editor.putInt("upgrade1",20);
            editor.putInt("upgrade2",200);
            editor.putInt("upgrade3",800);
            editor.putInt("upgrade4",2200);
            editor.putInt("upgrade5",15000);
            editor.putInt("upgrade6",99000);
            editor.commit();
        }
        else{
            Toast.makeText(this,"YA EXISTE EL USUARIO",Toast.LENGTH_LONG).show();
        }


    }

    public void volver(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


    public void ClickIniciar(View view) {
        EditText textoUser = findViewById(R.id.editTextTextPersonName);
        String username = textoUser.getText().toString();

        EditText textoPassword = findViewById(R.id.editTextTextPassword);
        String password = textoPassword.getText().toString();

        Cursor c = dbLogin.rawQuery("SELECT * FROM Cuenta WHERE Username = '"+username+"' AND Password = '"+password+"'",null);

        if(c.moveToFirst()){
            dbLogin.execSQL("UPDATE Cuenta SET Iniciada = 1 WHERE Username = '"+username+"' AND Password = '"+password+"'");

        }
        else{
            Toast.makeText(this,"NO EXISTE EL USUARIO",Toast.LENGTH_LONG).show();
        }
    }
}