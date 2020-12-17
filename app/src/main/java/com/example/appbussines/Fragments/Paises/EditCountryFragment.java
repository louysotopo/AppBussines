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

import com.example.appbussines.Entities.Cargo;
import com.example.appbussines.Entities.Pais;
import com.example.appbussines.Fragments.Cargos.ViewPositionFragment;
import com.example.appbussines.Fragments.Personal.ViewPersonalFragment;
import com.example.appbussines.Interfaces.Validaciones;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditCountryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditCountryFragment extends Fragment {
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]
    Validaciones objValidar; //objeto de nuestro clase Validaciones

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
                if(actualizarCargo())
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
            boolean sw = false;
            switch (pais.getStatus()) {
                case 1:   sw = true ; break;
                case 2:   sw = false; break;
                default:
            }
            switchMaterialstate.setChecked(sw);
        }
    }
    private boolean actualizarCargo(){
        String co="", nm=""; boolean sw = false; int status = 1000;

        if(!objValidar.Vacio(editTextCode) && !objValidar.Vacio(editTextNombre)) {
            co = editTextCode.getText().toString();
            nm = editTextNombre.getText().toString();
            sw = switchMaterialstate.isChecked();

            if (sw) status = 1; // activo
            else status = 2;    // inactivo

            updateCountry(co, nm, status);

            return true;

        }else{
            Toast.makeText(getActivity().getApplicationContext(),"Ingrese valores", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private void updateCountry(String code, String name, int status) {
        pais = new Pais(code, name, status);
        Map<String, Object> cargoMap = pais.toMap();
        try {
            mDatabase.child("Pais").child(code).updateChildren(cargoMap);
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),"El Pais "+ code+" a sido actualizado", Toast.LENGTH_SHORT).show();
        }catch (Exception e ) {}

    }
}