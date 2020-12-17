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
import android.widget.Toast;

import com.example.appbussines.Entities.Cargo;
import com.example.appbussines.Entities.Pais;
import com.example.appbussines.Fragments.Paises.ViewCountryFragment;
import com.example.appbussines.Interfaces.Validaciones;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.MainActivity;
import com.example.appbussines.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPositionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPositionFragment extends Fragment {
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

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

    Validaciones objValidar; //objeto de nuestro clase Validaciones

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

                if( actualizarCargo())
                listener.onButtonSelected( new ViewPositionFragment(cargo));
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected( new ViewPositionFragment(cargo));
            }
        });

    }
    private void setData(){
        if(cargo != null){
            editTextCode.setText(cargo.getCode());
            editTextNombre.setText(cargo.getName());
            boolean sw = false;
            switch (cargo.getStatus()) {
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

            updateCargo(co, nm, status);
            listener.onButtonSelected( new ViewPositionFragment(cargo));

            return true;

        }else{
            Toast.makeText(getActivity().getApplicationContext(),"Ingrese valores", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private void updateCargo(String code, String name, int status) {
        cargo = new Cargo(code, name, status);
        Map<String, Object> cargoMap = cargo.toMap();
        try {
            mDatabase.child("Cargo").child(code).updateChildren(cargoMap);
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),"El Cargo "+ code+" a sido actualizado", Toast.LENGTH_SHORT).show();
        }catch (Exception e ) {}

    }


}