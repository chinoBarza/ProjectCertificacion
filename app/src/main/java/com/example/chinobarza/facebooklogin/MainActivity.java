package com.example.chinobarza.facebooklogin;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.chinobarza.facebooklogin.fragment.InicioCorreoFragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {
    private LoginButton loginButton, loginCorreo;
    private CallbackManager callbackManager;
    private Button iniciarCorreo;
    private TextView noRegistrado;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.login_button);
        iniciarCorreo = findViewById(R.id.btn_iniciar_correo);
        noRegistrado = findViewById(R.id.btn_no_registrado);
        noRegistrado.setOnClickListener(v -> registrarse());
        iniciarCorreo.setOnClickListener(v -> iniciarSesionCorreo());
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("key", "Usuarios");
        editor.commit();

        if (AccessToken.getCurrentAccessToken() == null) {

            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                   /* Log.e(TAG, "OnSuccess");
                info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );*/
                    Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancel() {
                    //info.setText("Login attempt canceled.");
                    Snackbar.make(findViewById(R.id.login_button), "Login Cancelado", Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException e) {
                    //   info.setText("Login attempt failed.");
                    Snackbar.make(findViewById(R.id.login_button), "Hubo un error favor de Revisar", Snackbar.LENGTH_SHORT).show();

                }
            });
        } else {
            Intent loginIntent = new Intent(MainActivity.this, PrincipalActivity.class);
            startActivity(loginIntent);
            finish();
        }


    }

    private void iniciarSesionCorreo() {
        DialogFragment newFragment = InicioCorreoFragment.newInstance();
        newFragment.show(getFragmentManager(), "diálogo");
    }

    private void registrarse() {
        Intent intent = new Intent(this, LoginEmail.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
