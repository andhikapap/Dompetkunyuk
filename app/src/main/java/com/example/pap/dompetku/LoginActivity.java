package com.example.pap.dompetku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity ;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etEmail, etPassword; //manggil ID layout
    Button btnLogin;
    TextView evRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword=(EditText) findViewById(R.id.etPassword);
        btnLogin=(Button) findViewById(R.id.btnLogin);
        evRegister=(TextView) findViewById(R.id.evRegister);
        //ini variabel                          //ini parameter

        evRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view == evRegister){
            Intent i = new Intent(this,RegisterActivity.class);
            startActivity(i);
        }
    }
}
