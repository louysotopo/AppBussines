package com.example.appbussines;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.appbussines.Fragments.ListCargosFragment;
import com.example.appbussines.Fragments.ListPaisesFragment;
import com.example.appbussines.Fragments.ListPersonalFragment;
import com.example.appbussines.Fragments.User.UserFragment;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ContentActivity extends AppCompatActivity implements onFragmentBtnSelected {
// autenticacion
FirebaseAuth usuario;
//fin
    private static final String TAG = "MenuContainer";
    private Fragment currentFragment;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        init();
    }
    private void init() {
        //Start Navigation
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener( mOnNavigationItemSelectedListener);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        //start Display default Fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.add(R.id.frame_layout,new ListPersonalFragment());
        fragmentTransaction.commit();
    }
    private final NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            drawerLayout.closeDrawer(GravityCompat.START);
            switch (item.getItemId()){
                case R.id.my_acount:
                    LoadFrag( new UserFragment());
                    return true;
                case R.id.cursos:
                    LoadFrag( new ListPersonalFragment());
                    return true;
                case R.id.tareas:
                    LoadFrag( new ListCargosFragment());
                    return  true;
                case R.id.justificaciones:
                    LoadFrag( new ListPaisesFragment());
                    return true;
                case R.id.LogOut:
                    logOutUser();
                    return  true;
            }

            return false;
        }
    };
    public void LoadFrag(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onButtonSelected(Fragment fragment) {
        LoadFrag( fragment);
    }
    private void logOutUser(){
        usuario.signOut();
        startActivity(new Intent(ContentActivity.this, MainActivity.class));
        finish(); // para que no pueda voler hacia atras cuando cierre sesión
    }


}