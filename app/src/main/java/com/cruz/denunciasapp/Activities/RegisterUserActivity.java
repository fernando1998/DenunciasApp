package com.cruz.denunciasapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class RegisterUserActivity extends AppCompatActivity {



    private static final String TAG = RegisterUserActivity.class.getSimpleName();


    private EditText usrInput;
    private EditText passInput;
    private EditText emailInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);


        usrInput = (EditText) findViewById(R.id.username_input);
        passInput = (EditText) findViewById(R.id.password_input);
        emailInput = (EditText) findViewById(R.id.email_input);
    }

    public  void callRegister(View view){

        String username = usrInput.getText().toString();
        String password = passInput.getText().toString();
        String email = emailInput.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username y Password son campos requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<ResponseMessage> call = null;
        // Si no se incluye imagen hacemos un envío POST simple
            call = service.createUsuario(username, password, email);

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        ResponseMessage responseMessage = response.body();
                        Log.d(TAG, "responseMessage: " + responseMessage);

                        Toast.makeText(RegisterUserActivity.this, responseMessage.getMessage(), Toast.LENGTH_LONG).show();
                        finish();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(RegisterUserActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(RegisterUserActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

              startActivity(new Intent(RegisterUserActivity.this, LoginActivity.class));
                finish();
        }
}


