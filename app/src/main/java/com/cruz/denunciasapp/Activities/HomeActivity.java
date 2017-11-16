package com.cruz.denunciasapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cruz.denunciasapp.Models.User;
import com.cruz.denunciasapp.R;

/**
 * Created by FERNANDO on 16/11/2017.
 */

public class HomeActivity extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private static final String TAG = HomeActivity.class.getSimpleName();
    // SharedPreferences
    private SharedPreferences sharedPreferences;


    private TextView usernameText;
    private TextView passwordText;
    private TextView user_txt;
    private TextView tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        usernameText = (TextView)findViewById(R.id.fullname_text);
        passwordText=(TextView)findViewById(R.id.pass_text);
        user_txt=(TextView)findViewById(R.id.username_text);
        tip=(TextView)findViewById(R.id.tip);


        // init SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // get username from SharedPreferences
        String fullname = sharedPreferences.getString("username", null);


        Log.d(TAG, "username: " + fullname);

      //  User user = UserRepository.getUser(fullname);

        usernameText.setText(sharedPreferences.getString("username", null));
        passwordText.setText(sharedPreferences.getString("password", null));
        user_txt.setText(sharedPreferences.getString("email", null));
        // user_txt.setText(user.getEmail());


        //Cambiando estilo de letra demo
        if(sharedPreferences.getBoolean("islogged", false)){

        }else {
            startActivity(new Intent(this, LoginActivity.class));

        }

/*
*/
        //fin


        // Setear Toolbar como action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Set DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Set drawer toggle icon
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, android.R.string.ok, android.R.string.cancel);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // Set NavigationItemSelectedListener
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Do action by menu item id
                switch (menuItem.getItemId()){

                    case R.id.nav_user:
                        Toast.makeText(HomeActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                        break;

                    case R.id.nav_logout:
                        Toast.makeText(HomeActivity.this, "Logout...", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(MenuActivity.this, MainActivity.class));

                        // remove from SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        boolean success = editor.putBoolean("islogged", false).commit();
                        //        boolean success = editor.clear().commit(); // not recommended
                        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                        finish();
                        break;

                    case R.id.nav_settings:

                        Toast.makeText(HomeActivity.this, "Listar Denuncias...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeActivity.this,MainActivity.class));
                        break;
                    case R.id.nav_createDenuncia:

                        Toast.makeText(HomeActivity.this, "Crear Denuncia...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeActivity.this,RegisterActivity.class));
                        break;

                }
                // Close drawer
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // Change navigation header information
        ImageView photoImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.menu_photo);
        TextView fullnameText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.menu_fullname);
        fullnameText.setText(sharedPreferences.getString("email", null));

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // Option open drawer
                if(!drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.openDrawer(GravityCompat.START);   // Open drawer
                else
                    drawerLayout.closeDrawer(GravityCompat.START);    // Close drawer
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
