package com.example.pap.dompetku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pap.dompetku.Config.Config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etUsername, etEmail, etPassword, etRepassword; //manggil ID layout
    Button btnDaftar;
    TextView evLogin;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword=(EditText) findViewById(R.id.etPassword);
        btnDaftar=(Button) findViewById(R.id.btnDaftar);
        evLogin=(TextView) findViewById(R.id.evLogin);
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Register");
        progressDialog.setMessage("Loading...");

        //Listener
        evLogin.setOnClickListener(this);
        btnDaftar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==evLogin){
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
        }
        else if (v==btnDaftar){
            String usernameStr = etUsername.getText().toString();
            String emailStr = etEmail.getText().toString();
            String passwordStr = etPassword.getText().toString();
            String repasswordStr = etRepassword.getText().toString(); /** * Cek seluruh field tidak kosong */
            if (usernameStr.matches("") || emailStr.matches("") || passwordStr.matches("") || repasswordStr.matches("")) {
                Toast.makeText(this, "Field Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
            } else { /** * Cek Valid Email Format */if (!SyncStateContract.Helpers.isValidEmail(emailStr)) {
                Toast.makeText(this, "Email Tidak Sesuai", Toast.LENGTH_LONG).show();
            } else {
                if (passwordStr.length() < 6) {
                    Toast.makeText(this, "Kata Sandi Minimal 6 digit", Toast.LENGTH_LONG).show();
                } else { /** * Cek Password dan Repassword Sesuai */
                    if (!passwordStr.equals(repasswordStr)) {
                        Toast.makeText(this, "Kata Sandi Tidak Sesuai", Toast.LENGTH_LONG).show();
                    } else { /** * Cek From API */progressDialog.show();
                        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.getServerUrl()).client(client).addConverterFactory(GsonConverterFactory.create()).build();
                        LoginService loginService = retrofit.create(LoginService.class);
                        Call registerCall = loginService.postRegister(emailStr, usernameStr, passwordStr, Config.getApiKey());
                        registerCall.enqueue(this);
                    }
                }
            }
            }
        }
    }
}
