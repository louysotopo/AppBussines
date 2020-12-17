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
import android.widget.Toast;

import com.example.appbussines.Entities.Pais;
import com.example.appbussines.Fragments.ListPaisesFragment;
import com.example.appbussines.Interfaces.Validaciones;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AddCountryFragment extends Fragment {
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]
    Validaciones objValidar; //objeto de nuestro clase Validaciones
    //transacciones
    private View view;
    private onFragmentBtnSelected listener;

    //botones
    private Button buttonAceptar;
    private Button buttonCancelar;

    //datos
    private EditText txtCode;
    private EditText txtName;
    private SwitchMaterial swStatus;

    // ignorar
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
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]
        // validaciones
        objValidar = new Validaciones();
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

        txtCode = view.findViewById(R.id.editText_addcountry_code);
        txtName = view.findViewById(R.id.editText_addcountry_name);
        swStatus = view.findViewById(R.id.switch_add_country);
        swStatus.setChecked(true); // por defecto, Activo = 1, Inactivo =2
    }
    private void initbuttons(){
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(registrarPais())
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

    private boolean registrarPais(){
        String co="", nm=""; boolean sw = false; int status = 1000;

        if(!objValidar.Vacio(txtCode) && !objValidar.Vacio(txtName)) {
            co = txtCode.getText().toString();
            nm = txtName.getText().toString();
            sw = swStatus.isChecked();

            if (sw) status = 1; // activo
            else status = 2;    // inactivo

            addCountry(co, nm, status);
            return true;

        }else{
            Toast.makeText(getActivity().getApplicationContext(),"Ingrese valores", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private void addCountry(String code, String name, int status) {
        Pais pais = new Pais(code, name, status);
        mDatabase.child("Pais").child(code).setValue(pais);
        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),"El País: "+ name +" a sido agregado", Toast.LENGTH_SHORT).show();

    }
}