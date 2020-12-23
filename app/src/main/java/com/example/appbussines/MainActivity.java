package com.example.appbussines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editText_password;
    EditText editText_nameuser;
    Button button_login;
    Button button_register;

    private String email = "";
    private String password = "";
    FirebaseAuth usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario = FirebaseAuth.getInstance();
        init();

    }
    private  void init(){

        button_login = findViewById(R.id.button_login);
        button_register = findViewById(R.id.button_register);
        editText_nameuser = findViewById(R.id.editText_login_user_name); // email
        editText_password = findViewById(R.id.editText_login_user_password);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui intentar evaluar si existe o no en el sistema como usuario :v
                email = editText_nameuser.getText().toString();
                password = editText_password.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()){
                    loginUser();
                }else{
                    Toast.makeText(getApplicationContext(),"Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

       button_register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               StartRegisterActivity();
           }
       });

    }
    public  void StartContentActivity(){
        Intent intent = new Intent(this, ContentActivity.class);
        startActivity(intent);
    }

    public  void StartRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    private void loginUser() {

        usuario.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    StartContentActivity();
                }else{
                    Toast.makeText(MainActivity.this, "No se pudo iniciar sesión, porfavor comprueba los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}