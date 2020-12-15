package com.example.appbussines.Fragments.Cargos;

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

import com.example.appbussines.Entities.Cargo;
import com.example.appbussines.Entities.Pais;
import com.example.appbussines.Fragments.Paises.ViewCountryFragment;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPositionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPositionFragment extends Fragment {
    //transacciones
    private View view;
    private onFragmentBtnSelected listener;
    private Cargo cargo;

    //widgets
    private EditText editTextCode;
    private EditText editTextNombre;
    private SwitchMaterial switchMaterialstate;

    //botones
    private Button buttonAceptar;
    private Button buttonCancelar;

    //ignorar
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    //ignorar

    public EditPositionFragment() { }

    public EditPositionFragment( Cargo cargo) {
        this.cargo = cargo;
    }
    public static EditPositionFragment newInstance(String param1, String param2) {
        EditPositionFragment fragment = new EditPositionFragment();
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
        view = inflater.inflate(R.layout.fragment_edit_position, container, false);
        initComponents();
        initButtons();
        setData();
        return view;
    }
    private void initComponents(){
        //widgets
        editTextCode = view.findViewById(R.id.editText_editcargo_code );
        editTextNombre =  view.findViewById(R.id.editText_editcargo_name);
        switchMaterialstate = view.findViewById(R.id.switch_edit_cargo); ;
        //botones
        buttonAceptar = view.findViewById(R.id.button_guardar_edit_cargo);
        buttonCancelar = view.findViewById(R.id.button_cancelar_edit_cargo);;



    }private void initButtons(){
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onButtonSelected( new ViewPositionFragment(cargo));
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected( new ViewPositionFragment(cargo));
            }
        });

    }private void setData(){
        if(cargo != null){
            editTextCode.setText(cargo.getCode());
            editTextNombre.setText(cargo.getName());
        }
    }
}