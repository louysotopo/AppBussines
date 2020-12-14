package com.example.appbussines.Fragments.Paises;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appbussines.Fragments.ListPaisesFragment;
import com.example.appbussines.Fragments.ListPersonalFragment;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;

public class AddCountryFragment extends Fragment {
    //transacciones
    private View view;
    private onFragmentBtnSelected listener;

    //botones
    private Button buttonAceptar;
    private Button buttonCancelar;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public AddCountryFragment() {
        // Required empty public constructor
    }

    public static AddCountryFragment newInstance(String param1, String param2) {
        AddCountryFragment fragment = new AddCountryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        view = inflater.inflate(R.layout.fragment_add_country, container, false);
        initCompoenents();
        initbuttons();

        return  view;

    }
    private void  initCompoenents(){
        buttonAceptar = view.findViewById(R.id.button_aceptar_add_country);
        buttonCancelar =view.findViewById(R.id.button_cancelar_add_country);
    }
    private void initbuttons(){
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 listener.onButtonSelected( new ListPaisesFragment());
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected( new ListPaisesFragment());
            }
        });

    }
}