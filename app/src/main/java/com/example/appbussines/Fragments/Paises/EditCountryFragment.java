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
import android.widget.EditText;

import com.example.appbussines.Entities.Pais;
import com.example.appbussines.Fragments.Personal.ViewPersonalFragment;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditCountryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditCountryFragment extends Fragment {
    //transacciones
    private View view;
    private onFragmentBtnSelected listener;
    private Pais pais;

    //widgets
    private EditText editTextCode;
    private EditText editTextNombre;
    private SwitchMaterial switchMaterialstate;

    //botones
    private Button buttonAceptar;
    private Button buttonCancelar;

    // ignorar
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    // dejar de ignorar



    public EditCountryFragment() { }

    public EditCountryFragment(Pais pais ){
        this.pais = pais;
    }

    public static EditCountryFragment newInstance(String param1, String param2) {
        EditCountryFragment fragment = new EditCountryFragment();
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
        view = inflater.inflate(R.layout.fragment_edit_country, container, false);
        initComponents();
        initButtons();
        setData();
        return view;
    }
    private void initComponents(){
         editTextCode = view.findViewById(R.id.editText_editpais_code);
         editTextNombre =  view.findViewById(R.id.editText_editpais_name);
         switchMaterialstate = view.findViewById(R.id.switch_edit_pais); ;

        //botones
         buttonAceptar = view.findViewById(R.id.button_guardar_edit_pais);
         buttonCancelar = view.findViewById(R.id.button_cancelar_edit_pais);;

    }private void initButtons(){
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onButtonSelected( new ViewCountryFragment(pais));
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected( new ViewCountryFragment(pais));
            }
        });

    }private void setData(){
        if(pais != null){
            editTextCode.setText(pais.getCode());
            editTextNombre.setText(pais.getName());
        }
    }
}