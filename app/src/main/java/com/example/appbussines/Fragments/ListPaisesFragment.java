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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListPaisesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListPaisesFragment extends Fragment {
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
        paisList =  getDataBase(); // asignar al personallist la lista de personal que se obtenga de la base de datos :v
        adapterCountry = new AdapterCountry(paisList, getContext());
        recyclerView.setAdapter(adapterCountry);

        floatingActionButton = view.findViewById(R.id.floatingActionButton_add_pais);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onButtonSelected( new AddCountryFragment());
                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
    private  List<Pais> getDataBase(){
        List<Pais> paises = new ArrayList<>();
        paises.add(new Pais("001","Peru",1));
        paises.add(new Pais("003","Dinamarca",0));
        paises.add(new Pais("004","Costa Rica",0));
        return paises;

    }
}