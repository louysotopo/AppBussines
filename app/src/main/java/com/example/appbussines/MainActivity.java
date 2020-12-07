package com.example.appbussines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editText_password;
    EditText editText_nameuser;
    Button button_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private  void init(){

        button_login = findViewById(R.id.button_login);
        editText_nameuser = findViewById(R.id.editText_login_user_name);
        editText_password = findViewById(R.id.editText_login_user_password);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui intentar evaluar si existe o no en el sistema como usuario :v
                StartContentActivity();
            }
        });

    }
    public  void StartContentActivity(){
        Intent intent = new Intent(this, ContentActivity.class);
        startActivity(intent);
    }
}