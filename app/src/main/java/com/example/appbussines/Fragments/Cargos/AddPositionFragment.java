package com.example.appbussines.Fragments.Cargos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbussines.Entities.Cargo;
import com.example.appbussines.Fragments.ListCargosFragment;
import com.example.appbussines.Fragments.ListPaisesFragment;
import com.example.appbussines.Interfaces.Validaciones;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AddPositionFragment extends Fragment {

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

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
    Validaciones objValidar; //objeto de nuestro clase Validaciones

    //ignorar
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    public AddPositionFragment() {
        // Required empty public constructor
    }

    public static AddPositionFragment newInstance(String param1, String param2) {
        AddPositionFragment fragment = new AddPositionFragment();
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
        // validaciones
        objValidar = new Validaciones();

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
        view = inflater.inflate(R.layout.fragment_add_position, container, false);
        initCompoenents();
        initbuttons();

        return view;
    }
    private void  initCompoenents(){
        buttonAceptar = view.findViewById(R.id.button_aceptar_add_cargo);
        buttonCancelar =view.findViewById(R.id.button_cancelar_add_cargo);

        txtCode = view.findViewById(R.id.editText_addposition_code);
        txtName = view.findViewById(R.id.editText_addposition_name);
        swStatus = view.findViewById(R.id.switch_add_cargo);
        swStatus.setChecked(true); // por defecto, Activo = 1, Inactivo =2


    }
    private void initbuttons(){
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(registrarCargo()){
                    listener.onButtonSelected( new ListCargosFragment());
                }
                // regreso a mi lista de cargos

            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onButtonSelected( new ListCargosFragment());
            }
        });

    }
private boolean registrarCargo(){
    String co="", nm=""; boolean sw = false; int status = 1000;

    if(!objValidar.Vacio(txtCode) && !objValidar.Vacio(txtName)) {
        co = txtCode.getText().toString();
        nm = txtName.getText().toString();
        sw = swStatus.isChecked();

        if (sw) status = 1; // activo
        else status = 2;    // inactivo

        addCargo(co, nm, status);
        return true;

    }else{
        Toast.makeText(getActivity().getApplicationContext(),"Ingrese valores", Toast.LENGTH_SHORT).show();
        return false;
    }
}
    private void addCargo(String code, String name, int status) {
        Cargo cargo = new Cargo(code, name, status);
        mDatabase.child("Cargo").child(code).setValue(cargo);
        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),"El Cargo de: "+ name +" a sido agregado", Toast.LENGTH_SHORT).show();

    }

}
