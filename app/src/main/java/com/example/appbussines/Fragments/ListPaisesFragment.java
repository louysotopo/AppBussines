package com.example.appbussines.Fragments;

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

import com.example.appbussines.Adapters.AdapterCountry;
import com.example.appbussines.Adapters.AdapterPosition;
import com.example.appbussines.Entities.Cargo;
import com.example.appbussines.Entities.Pais;
import com.example.appbussines.Fragments.Paises.AddCountryFragment;
import com.example.appbussines.Fragments.Personal.AddPersonalFragment;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListPaisesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListPaisesFragment extends Fragment {
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    //transations
    private View view;
    private onFragmentBtnSelected listener;

    //widgets
    private List<Pais> paisList;
    private RecyclerView recyclerView;
    private AdapterCountry adapterCountry;
    FloatingActionButton floatingActionButton;

    // ignorar
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    //dejar de ignorar

    public ListPaisesFragment() { }

    public static ListPaisesFragment newInstance(String param1, String param2) {
        ListPaisesFragment fragment = new ListPaisesFragment();
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
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_paises, container, false);
        initList();

        return view;
    }
    private void initList(){
        recyclerView = view.findViewById(R.id.recyclerCountries);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        getDataBase(); // asignar al personallist la lista de personal que se obtenga de la base de datos :v

        floatingActionButton = view.findViewById(R.id.floatingActionButton_add_pais);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onButtonSelected( new AddCountryFragment());
                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
    /*
    private  List<Pais> getDataBase(){
        List<Pais> paises = new ArrayList<>();
        paises.add(new Pais("001","Peru",1));
        paises.add(new Pais("003","Dinamarca",0));
        paises.add(new Pais("004","Costa Rica",0));
        paises.add(new Pais("006","Ecuador",0));
        return paises;

    }
    */

    private void getDataBase(){
        paisList = new ArrayList<>();
        mDatabase.child("Pais").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds: snapshot.getChildren()){
                        String code = ds.child("code").getValue().toString();
                        String name = ds.child("name").getValue().toString();
                        int status = Integer.parseInt(ds.child("status").getValue().toString());

                        System.out.println((new Pais(code,name,status)).toString());
                        paisList.add(new Pais(code,name,status));
                    }
                    adapterCountry = new AdapterCountry(paisList, getContext());
                    recyclerView.setAdapter(adapterCountry);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}