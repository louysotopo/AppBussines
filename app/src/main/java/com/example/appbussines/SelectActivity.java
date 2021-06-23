package com.example.appbussines;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectActivity extends AppCompatActivity {
    private CardView cardViewTeacher;
    private CardView cardViewStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        initComponents();
        initButtons();
    }
    private void initComponents() {
        cardViewStudent = findViewById(R.id.cardView_estudiante);
        cardViewTeacher = findViewById(R.id.cardView_profesor);

    }
    private  void initButtons(){
        cardViewTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { StartRegisterActivity();
            }
        });
        cardViewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { StartRegisterActivity();
            }
        });
    }
    public  void StartRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


}