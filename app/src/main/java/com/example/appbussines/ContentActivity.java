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
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.appbussines.Fragments.ListCargosFragment;
import com.example.appbussines.Fragments.ListPaisesFragment;
import com.example.appbussines.Fragments.ListPersonalFragment;
import com.example.appbussines.Fragments.User.UserFragment;
import com.google.android.material.navigation.NavigationView;

public class ContentActivity extends AppCompatActivity  {

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
    /*
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
            switch (item.getItemId()){
                case R.id.my_acount:
                    loadFragment( new UserFragment() );
                    return true;
                case R.id.Personal:
                    loadFragment( new ListPersonalFragment() );
                    return true;
            }

        return false;
    }
    */

    private final NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            drawerLayout.closeDrawer(GravityCompat.START);
            switch (item.getItemId()){
                case R.id.my_acount:
                    Log.d("TO","my cuenta");
                    LoadFrag( new UserFragment());
                    return true;
                case R.id.Personal:
                    Log.d("TO","Personal");
                    LoadFrag( new ListPersonalFragment());
                    return true;
                case R.id.Cargo:
                    LoadFrag( new ListCargosFragment());
                    return  true;
                case R.id.Paises:
                    LoadFrag( new ListPaisesFragment());
                    return true;
                case R.id.LogOut:
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
    /*
    private void loadFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        if (fragment.isAdded()) {
            if (currentFragment != null)
                transaction.hide(currentFragment).show(fragment);
        } else {
            if (currentFragment != null)
                transaction.hide(currentFragment).add(R.id.container, fragment, "Hl");
            else
                transaction.add(R.id.container, fragment, "HL");
        }
        //transaction.replace(R.id.container, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
        currentFragment = fragment;

    }*/


}