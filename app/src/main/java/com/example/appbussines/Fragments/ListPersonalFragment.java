package com.example.appbussines.Fragments;

import android.app.Person;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.appbussines.Adapters.AdapterCountry;
import com.example.appbussines.Adapters.AdapterPersonal;
import com.example.appbussines.Entities.Pais;
import com.example.appbussines.Entities.Personal;
import com.example.appbussines.Fragments.Personal.AddPersonalFragment;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListPersonalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListPersonalFragment extends Fragment {
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    List<Personal> personalList;
    RecyclerView recyclerView;
    AdapterPersonal adapterPersonal;
    SearchView searchView;
    View view;
    FloatingActionButton floatingActionButton;
        // ignorar , secreo automaticamente
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    // deja de ignorar
    private onFragmentBtnSelected listener;
    private FirebaseDatabase database;

    public ListPersonalFragment() {}

    public static ListPersonalFragment newInstance(String param1, String param2) {
        ListPersonalFragment fragment = new ListPersonalFragment();
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
       // getDataBase();
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_personal, container, false);
        init();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if( context instanceof  onFragmentBtnSelected ){
            listener=(onFragmentBtnSelected) context;
        }
        else{
            Log.d("LPF","Implemeentar listener");
        }

    }

    private  void init(){
        //lista de personal
        recyclerView = view.findViewById(R.id.recyclerPersonal);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        getDataBase(); // asignar al personallist la lista de personal que se obtenga de la base de datos :v

        // boton para añadir  personal
        floatingActionButton = view.findViewById(R.id.floatingActionButton_add_personal);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onButtonSelected( new AddPersonalFragment());
                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });




    }
    // Aqui poner metodito getDatosss
    private void getDataBase(){
        personalList = new ArrayList<>();
        mDatabase.child("Personal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds: snapshot.getChildren()){
                        String id = ds.child("id").getValue().toString();
                        String firstname = ds.child("firstname").getValue().toString();
                        String lastname = ds.child("lastname").getValue().toString();
                        String email = ds.child("email").getValue().toString();
                        String position = ds.child("position").getValue().toString();
                        String incomingdate = ds.child("incomingdate").getValue().toString();
                        String birthdate = ds.child("birthdate").getValue().toString();
                        String country = ds.child("country").getValue().toString();
                        String age = ds.child("age").getValue().toString();
                        int status = Integer.parseInt(ds.child("state").getValue().toString());

                       // System.out.println((new Pais(code,name,status)).toString());
                        personalList.add(new Personal(id,firstname,lastname,email,position,incomingdate,birthdate,country,age,status));
                    }
                    adapterPersonal = new AdapterPersonal(personalList, getContext());
                    recyclerView.setAdapter(adapterPersonal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private List<Personal> getDataBaseFEO(){
        List<Personal> per = new ArrayList<Personal>();
        database = FirebaseDatabase.getInstance();
        /*        database.getReference().child("usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    personalList.clear();
                    for (DataSnapshot ds: snapshot.getChildren()){
                        String name = ds.child("nombre").getValue().toString();

                        Personal p = new Personal("i",name,"n","n","n","n","n","n","n","n");
                        personalList.add(p);
                    }
                    adapterPersonal = new AdapterPersonal(personalList,getContext());
                    recyclerView.setAdapter(adapterPersonal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        */

        per.add( new Personal("1","Juan","Perez","ktorres@gmail.com","Auxiliar","12/12/2020","12/12/2020","Peru","30",2));
        per.add( new Personal("1","Maria","Cardenas","mcardenas@gmail.com","Gerente","12/12/2020","12/12/2020","Peru","25",1));
        per.add( new Personal("1","Juan","Perez","ktorres@gmail.com","Secretaria","12/12/2020","12/12/2020","Peru","36",1));
        return per;

    }
}