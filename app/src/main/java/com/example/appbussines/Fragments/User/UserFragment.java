package com.example.appbussines.Fragments.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.appbussines.ContentActivity;
import com.example.appbussines.MainActivity;
import com.example.appbussines.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UserFragment extends Fragment {
    // ignorar
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    // ignorar

    // datos
    private Button btn_CerrarSesion;
    private Button btn_editar;
    private TextView txt_miname;
    private TextView txt_miemail;
    private SwitchMaterial switchStatus;
    private View view;

    FirebaseAuth usuario;
    DatabaseReference dataBase;

    public UserFragment() {   }

    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        usuario = FirebaseAuth.getInstance();
        dataBase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_user, container, false);
        initComponents();
        setData();
        initButtons();
        return view;
    }
    private void initComponents() {
        txt_miname = (TextView) view.findViewById(R.id.textView_user_name);
        txt_miemail = (TextView) view.findViewById(R.id.textView_user_email);
        switchStatus = view.findViewById(R.id.switch_view_user);
        btn_CerrarSesion = view.findViewById(R.id.button_logout_user);
        btn_editar = view.findViewById(R.id.button_edit_user);
    }
    private void setData() {
        getUSerInfo();
        switchStatus.setChecked(true);
    }
    private void initButtons() {
        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // aqui nada
            }
        });

        btn_CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutUser();

            }
        });
    }

    private void getUSerInfo(){
        String id = Objects.requireNonNull(usuario.getCurrentUser()).getUid();

        dataBase.child("Usuario").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                txt_miname.setText(name);
                txt_miemail.setText(email);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void logOutUser(){
        usuario.signOut();
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish(); // para que no pueda voler hacia atras cuando cierre sesión
    }
}



