package com.example.appbussines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    /**
     * Variables widget
     **/
    private EditText miname;
    private EditText miemail;
    private EditText mipassword;
    private SwitchMaterial switchStatus;
    private Button btn_registrar;
    private Button btn_sendToLogin;


    /**
     * Variables de datos
     **/

    private String name = "";
    private String email = "";
    private String password = "";

    FirebaseAuth usuario;
    DatabaseReference dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //
        /** Variables Registrar -Widget
         * */
        usuario = FirebaseAuth.getInstance();
        dataBase = FirebaseDatabase.getInstance().getReference();

        miname = (EditText) findViewById(R.id.editText_user_name);
        miemail = (EditText) findViewById(R.id.editText_user_email);
        mipassword = (EditText) findViewById(R.id.editText_user_password);
        switchStatus = findViewById(R.id.switch_register_user);
        btn_registrar = (Button) findViewById(R.id.button_aceptar_user);
        btn_sendToLogin = (Button) findViewById(R.id.button_cancelar_user);
        switchStatus.setChecked(true);

        btn_registrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                name = miname.getText().toString();
                email = miemail.getText().toString();
                password = mipassword.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {  // aqui lo de los terminos
                    if (password.length() >= 6) {
                        registerUser();
                    } else {
                        Toast.makeText(RegisterActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(RegisterActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_sendToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
        //

    }

    private void registerUser() {
        System.out.println(" nombre: " + name + " correo: " + email + " contra: " + password);
        usuario.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    //map
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("password", password);

                    // variables de id
                    String id = Objects.requireNonNull(usuario.getCurrentUser()).getUid();

                    // creacion de usuario en la base de datos
                    dataBase.child("Usuario").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                StartContentActivity();
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "No se pudo completar regitrar al usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void StartContentActivity() {
        Intent intent = new Intent(this, ContentActivity.class);
        startActivity(intent);
    }

}