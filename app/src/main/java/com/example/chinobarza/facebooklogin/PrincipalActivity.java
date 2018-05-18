package com.example.chinobarza.facebooklogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.chinobarza.facebooklogin.callback.GetUserCallback;
import com.example.chinobarza.facebooklogin.fragments.Camera;
import com.example.chinobarza.facebooklogin.fragments.Galeria;
import com.example.chinobarza.facebooklogin.fragments.SlideShow;
import com.example.chinobarza.facebooklogin.fragments.Tools;
import com.example.chinobarza.facebooklogin.request.UserRequest;
import com.example.chinobarza.facebooklogin.to.User;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.login.LoginManager;

/*import com.facebook.login.LoginManager;*/

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GetUserCallback.IGetUserResponse {
    private SimpleDraweeView mProfilePhotoView;
    private TextView mName;
    private TextView mId;
    private TextView mEmail;
    private TextView mPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        mProfilePhotoView = header.findViewById(R.id.profile_photo);
        mName = header.findViewById(R.id.nombre_face);
        mId = header.findViewById(R.id.id_facebokk);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            getFragmentManager().beginTransaction().replace(R.id.contentenedor_principal, new Camera()).commit();

        } else if (id == R.id.nav_gallery) {
            getFragmentManager().beginTransaction().replace(R.id.contentenedor_principal, new Galeria()).commit();
        } else if (id == R.id.nav_slideshow) {
            getFragmentManager().beginTransaction().replace(R.id.contentenedor_principal, new SlideShow()).commit();
        } else if (id == R.id.nav_manage) {
            getFragmentManager().beginTransaction().replace(R.id.contentenedor_principal, new Tools()).commit();
        } else if (id == R.id.nav_share) {
            getFragmentManager().beginTransaction().replace(R.id.contentenedor_principal, new Camera()).commit();
        } else if (id == R.id.cerrar_sesion) {
            LoginManager.getInstance().logOut();
            Intent loginIntent = new Intent(PrincipalActivity.this, MainActivity.class);
            startActivity(loginIntent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCompleted(User user) {
        mProfilePhotoView.setImageURI(user.getPicture());
        mName.setText(user.getName());
        mId.setText(user.getId());
        /*if (user.getEmail() == null) {
            mEmail.setText("no Email");
            mEmail.setTextColor(Color.RED);
        } else {
            mEmail.setText(user.getEmail());
            mEmail.setTextColor(Color.BLACK);
        }
        mPermissions.setText(user.getPermissions());*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserRequest.makeUserRequest(new GetUserCallback(PrincipalActivity.this).getCallback());
    }
}
