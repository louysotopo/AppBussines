package com.example.appbussines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editText_password;
    EditText editText_nameuser;
    Button button_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        create();
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
    public void create(){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Map<String,Object> map=new HashMap<>();
        map.put("Nombre","Francisco");
        map.put("Apellido","Ortega");
        map.put("Edad",25);
        map.put("Correo Electrónico","correo_prueba@gmail.com");
        map.put("Rol","Administrador");
        mDatabase.child("usuarios").push().setValue(map);

        DatabaseReference mDatabase2;
        mDatabase2 = FirebaseDatabase.getInstance().getReference();
        Map<String,Object> map2=new HashMap<>();
        map2.put("Codigo",51);
        map2.put("Descripción","Perú");
        mDatabase2.child("paises").push().setValue(map2);

        DatabaseReference mDatabase3;
        mDatabase3 = FirebaseDatabase.getInstance().getReference();
        Map<String,Object> map3=new HashMap<>();
        map3.put("Codigo",01);
        map3.put("Descripción","Administrador");
        mDatabase3.child("cargo").push().setValue(map3);
    }
}