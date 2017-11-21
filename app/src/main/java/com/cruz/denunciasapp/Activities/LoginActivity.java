package com.cruz.denunciasapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cruz.denunciasapp.Models.User;
import com.cruz.denunciasapp.R;
import com.cruz.denunciasapp.Services.ApiService;
import com.cruz.denunciasapp.Services.ApiServiceGenerator;
import com.cruz.denunciasapp.Services.ResponseMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by FERNANDO on 16/11/2017.
 */

public class LoginActivity  extends AppCompatActivity {


    // SharedPreferences
    private SharedPreferences sharedPreferences;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText user, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // init SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        user = (EditText) findViewById(R.id.inp_user);
        pwd = (EditText) findViewById(R.id.inp_password);

        // islogged remember
        if(sharedPreferences.getBoolean("islogged", false)){
            // Go to Dashboard
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

    }

    public void goLogin(View view){

        final String username = user.getText().toString();
        String password = pwd.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Debe completar todo los campos", Toast.LENGTH_SHORT).show();
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<User> call = null;
        call = service.loginUser(username, password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);
                    if (response.isSuccessful()) {
                        User user1 = response.body();
                        Log.d(TAG, "responseMessage: " + user1);

                        int id_usuario=user1.getId();
                        String ussnam=user1.getUsername();
                        String email=user1.getEmail();


                        Toast.makeText(LoginActivity.this, "Bienvenido Mr."+username, Toast.LENGTH_LONG).show();

                        // Save to SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        boolean success = editor
                                .putInt("user-id",user1.getId())
                                .putString("username", user1.getUsername())
                                .putString("email", user1.getEmail())
                                .putBoolean("islogged", true)
                                .commit();

                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();

                        /*Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("usuario_id",id_usuario);
                        intent.putExtra("username",ussnam);
                        intent.putExtra("email",email);
                        startActivity(intent);*/
                    } else {
                        //progressDialog.dismiss();
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        Toast.makeText(LoginActivity.this, "Correo o contraseña incorrectos!", Toast.LENGTH_SHORT).show();
                        //throw new Exception();
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void goRegister(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
        startActivity(intent);
    }


}
