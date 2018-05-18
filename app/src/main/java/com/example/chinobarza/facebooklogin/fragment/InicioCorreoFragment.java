package com.example.chinobarza.facebooklogin.fragment;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chinobarza.facebooklogin.PrincipalActivity;
import com.example.chinobarza.facebooklogin.R;
import com.example.chinobarza.facebooklogin.db.MyDatabase;

public class InicioCorreoFragment extends DialogFragment {

    public static InicioCorreoFragment newInstance() {
        return new InicioCorreoFragment();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.iniciar, container, false);
        TextView cancelar = vista.findViewById(R.id.btn_cancelar);
        TextView btnIniciar = vista.findViewById(R.id.btn_iniciar);
        EditText usuario = vista.findViewById(R.id.usuario);
        EditText contrasea = vista.findViewById(R.id.contraseña);

        cancelar.setOnClickListener(v1 -> dismiss());
        btnIniciar.setOnClickListener(v -> {
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            String miDB = sharedPref.getString("key", "Usuarios");
            MyDatabase database = new MyDatabase(getContext(), miDB, null, 1);
            SQLiteDatabase mibase = database.getWritableDatabase();

            if (mibase.rawQuery(
                    "Select * from Usuario where nombre = '" +
                            usuario.getText().toString() + "' " +
                            " And pass = '" + contrasea.getText().toString() + "' "
                    , null).moveToFirst()) {
                Intent intent = new Intent(getContext(), PrincipalActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else
                Toast.makeText(getContext(), "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();

        });
        return vista;
    }
}
