package com.example.appbussines.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appbussines.Adapters.AdapterPersonal;
import com.example.appbussines.Adapters.AdapterPosition;
import com.example.appbussines.Entities.Cargo;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListCargosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListCargosFragment extends Fragment {
    //transacciones
    //private onFragmentBtnSelected listener;
    private View view;


    private List<Cargo> cargoList;
    private RecyclerView recyclerView;
    private AdapterPosition adapterPosition;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_cargos, container, false);
        initList();
        return view;
    }
    private  void initList(){

        recyclerView = view.findViewById(R.id.recyclerPositions);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        cargoList =  getDataBase(); // asignar al personallist la lista de personal que se obtenga de la base de datos :v
        adapterPosition = new AdapterPosition(cargoList, getContext());
        recyclerView.setAdapter(adapterPosition);

    }
    private List<Cargo> getDataBase(){
        List<Cargo> cargos = new ArrayList<>();
        cargos.add( new Cargo("001","Contratista",1));
        cargos.add( new Cargo("002","SuperIntendente",0));
        cargos.add( new Cargo("003","Secretario",1));
        return cargos;
    }
}