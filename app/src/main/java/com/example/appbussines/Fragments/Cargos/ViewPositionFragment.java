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
import android.widget.TextView;

import com.example.appbussines.Entities.Cargo;
import com.example.appbussines.Entities.Pais;
import com.example.appbussines.Fragments.ListCargosFragment;
import com.example.appbussines.Fragments.ListPaisesFragment;
import com.example.appbussines.Fragments.Paises.EditCountryFragment;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewPositionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPositionFragment extends Fragment {
    //transacciones
    private View view;
    private onFragmentBtnSelected listener;
    private  Cargo cargo;

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
    //dejar de ignorar
    public ViewPositionFragment() {
        // Required empty public constructor
    }
    public ViewPositionFragment(Cargo cargo) {
     this.cargo = cargo;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewPositionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewPositionFragment newInstance(String param1, String param2) {
        ViewPositionFragment fragment = new ViewPositionFragment();
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
        view  = inflater.inflate(R.layout.fragment_view_position, container, false);
        initComponents();
        initButtons();
        setData();
        return  view;
    }


    private void initComponents(){
        //widget
        textViewCode = view.findViewById(R.id.textView_viewposition_code);
        textViewName = view.findViewById(R.id.textView_viewposition_name);
        switchStatus = view.findViewById(R.id.switch_view_position);

        //button
        buttonModificar= view.findViewById(R.id.button_editar_view_position);
        buttonEliminar= view.findViewById(R.id.button_eliminar_view_position);
        buttonSalir= view.findViewById(R.id.button_salir_view_position);
    }
    private  void initButtons(){
        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected( new EditPositionFragment(cargo));
            }
        });
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected( new ListCargosFragment());
            }
        });
        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected( new ListCargosFragment());
            }
        });
    }
    private void setData(){
        if(this.cargo != null){
            textViewCode.setText(cargo.getCode());
            textViewName.setText(cargo.getName());
        }
    }
}