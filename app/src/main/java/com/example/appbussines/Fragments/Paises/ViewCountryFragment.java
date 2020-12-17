package com.example.appbussines.Fragments.Paises;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbussines.Entities.Pais;
import com.example.appbussines.Fragments.ListCargosFragment;
import com.example.appbussines.Fragments.ListPaisesFragment;
import com.example.appbussines.Fragments.ListPersonalFragment;
import com.example.appbussines.Fragments.Personal.EditPersonalFragment;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewCountryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewCountryFragment extends Fragment {
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]
    //transacciones
    private View view;
    private onFragmentBtnSelected listener;
    private Pais pais;

    // wigets y campos de texto
    private TextView textViewCode;
    private TextView textViewName;
    private SwitchMaterial switchStatus;

    // botones
    private Button buttonModificar;
    private Button buttonEliminar;
    private Button buttonSalir;

    //ignorar
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    // dejar de ignorar

    public ViewCountryFragment() {}
    public ViewCountryFragment( Pais pais) {
        this.pais = pais;
    }

    public static ViewCountryFragment newInstance(String param1, String param2) {
        ViewCountryFragment fragment = new ViewCountryFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_view_country, container, false);
        initComponents();
        setData();
        initButtons();
        return view;

    }
    private void initComponents(){
        //widget
        textViewCode = view.findViewById(R.id.textView_viewcountry_code);
        textViewName = view.findViewById(R.id.textView_viewcountry_name);
        switchStatus = view.findViewById(R.id.switch_view_country);


        //button
        buttonModificar= view.findViewById(R.id.button_editar_view_country);
        buttonEliminar= view.findViewById(R.id.button_eliminar_view_country);
        buttonSalir= view.findViewById(R.id.button_salir_view_country);
    }
    private  void initButtons(){
        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected( new EditCountryFragment(pais));
            }
        });
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //
                // Alert dialog de eliminación
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("¿Desea eliminar el registro?")
                        .setCancelable(false)
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // aqui actualizar
                                mDatabase.child("Pais").child(pais.getCode()).child("status").setValue(0);
                                Toast.makeText(getActivity().getApplicationContext(),"Se eliminó el registro", Toast.LENGTH_SHORT).show();
                                listener.onButtonSelected( new ListPaisesFragment());
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                // Create the AlertDialog object and return it
                AlertDialog titulo = builder.create();
                titulo.setTitle("Eliminar registro");
                titulo.show();

                //


            }
        });
        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected( new ListPaisesFragment());
            }
        });
    }
    private void setData(){
        if(this.pais != null){
            textViewCode.setText(pais.getCode());
            textViewName.setText(pais.getName());
            boolean sw = false;
            switch (pais.getStatus()) {
                case 1:   sw = true ; break;
                case 2:   sw = false; break;
                default:
            }
            switchStatus.setChecked(sw);
        }
    }

}