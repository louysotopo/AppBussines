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

import com.example.appbussines.Adapters.AdapterPersonal;
import com.example.appbussines.Entities.Personal;
import com.example.appbussines.Fragments.Personal.AddPersonalFragment;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
        getDataBase();
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
        personalList =  getDataBase(); // asignar al personallist la lista de personal que se obtenga de la base de datos :v
        adapterPersonal = new AdapterPersonal(personalList, getContext());
        recyclerView.setAdapter(adapterPersonal);

        searchView = view.findViewById(R.id.SearchPersonal);
        // boton para añadir  personal
        floatingActionButton = view.findViewById(R.id.floatingActionButton_add_personal);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onButtonSelected( new AddPersonalFragment());
                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        initSearch();



    }
    // Aqui poner metodito getDatosss
    private List<Personal> getDataBase(){
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
        //per.add( new Personal("1","Maria","Cardenas","mcardenas@gmail.com","Gerente","12/12/2020","12/12/2020","Peru","25","activo"));
        //per.add( new Personal("1","Juan","Perez","ktorres@gmail.com","Secretaria","12/12/2020","12/12/2020","Peru","36","activo"));
        return per;

    }
    private  void initSearch(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapterPersonal.filter(newText);
                return false;
            }
        });
    }
}