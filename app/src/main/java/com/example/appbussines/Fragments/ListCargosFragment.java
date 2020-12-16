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

import com.example.appbussines.Adapters.AdapterPersonal;
import com.example.appbussines.Adapters.AdapterPosition;
import com.example.appbussines.Entities.Cargo;
import com.example.appbussines.Fragments.Cargos.AddPositionFragment;
import com.example.appbussines.Fragments.Paises.AddCountryFragment;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListCargosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListCargosFragment extends Fragment {
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    //transacciones
    private onFragmentBtnSelected listener;
    private View view;

    private List<Cargo> cargoList;
    private RecyclerView recyclerView;
    private AdapterPosition adapterPosition;
    FloatingActionButton floatingActionButton;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public ListCargosFragment() { }

    public static ListCargosFragment newInstance(String param1, String param2) {
        ListCargosFragment fragment = new ListCargosFragment();
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
        view = inflater.inflate(R.layout.fragment_list_cargos, container, false);
        initList();
        initButton();
        return view;
    }

    private  void initList(){
        recyclerView = view.findViewById(R.id.recyclerPositions);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        getDataBase();


    }

    private void getDataBase(){
        cargoList = new ArrayList<>();
        mDatabase.child("Cargo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds: snapshot.getChildren()){
                        String code = ds.child("code").getValue().toString();
                        String name = ds.child("name").getValue().toString();
                        int status = Integer.parseInt(ds.child("status").getValue().toString());

                        System.out.println((new Cargo(code,name,status)).toString());
                        cargoList.add(new Cargo(code,name,status));
                    }
                    adapterPosition = new AdapterPosition(cargoList, getContext());
                    recyclerView.setAdapter(adapterPosition);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /* List<Cargo> cargos = new ArrayList<>();
        cargos.add( new Cargo("001","Contratista",1));
        cargos.add( new Cargo("002","SuperIntendente",0));
        cargos.add( new Cargo("003","Secretario",1));
        */

       // return cargos;

    }
    private void  initButton(){
        floatingActionButton = view.findViewById(R.id.floatingActionButton_add_cargo);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onButtonSelected( new AddPositionFragment());
                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
}