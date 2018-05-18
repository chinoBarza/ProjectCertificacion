package com.example.chinobarza.facebooklogin;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chinobarza.facebooklogin.db.MyDatabase;

public class LoginEmail extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    EditText usuario, contraseña;
    Button guardar, regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        usuario = findViewById(R.id.edt_nombre);
        contraseña = findViewById(R.id.edt_pass);
        guardar = findViewById(R.id.btn_registrar);
        regresar = findViewById(R.id.btn_regresar);
        guardar.setOnClickListener(v -> {
            if (usuario.length() < 4 && contraseña.length() < 4)
                guardarUsuario();
            else
                Toast.makeText(getApplicationContext(), "Longitud minima de 5 digitos", Toast.LENGTH_SHORT).show();
        });
        regresar.setOnClickListener(v -> finish());
    }

    private void guardarUsuario() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String miDB = sharedPref.getString("key", "Usuarios");
        try {
            MyDatabase database = new MyDatabase(this, miDB, null, 1);
            SQLiteDatabase mibase = database.getWritableDatabase();
            //mibase.execSQL(QuerysUtils.insertarUsuario(usuario.getText().toString(), contraseña.getText().toString()));
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre", usuario.getText().toString());
            contentValues.put("pass", contraseña.getText().toString());
            if (!mibase.rawQuery("Select * from Usuario where nombre = '" + usuario.getText().toString() + "'", null).moveToFirst()) {
                if (mibase.insert("Usuario", null, contentValues) != -1) {
                    usuario.setText("");
                    contraseña.setText("");
                    Toast.makeText(getApplicationContext(), "Guardado Correctamente...", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else
                Toast.makeText(getApplicationContext(), "Usuario ya Registrado", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Log.e(TAG, "Error" + e);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
