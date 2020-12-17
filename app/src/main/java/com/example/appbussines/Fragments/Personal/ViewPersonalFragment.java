package com.example.appbussines.Fragments.Personal;

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

import com.example.appbussines.Entities.Personal;
import com.example.appbussines.Fragments.ListPaisesFragment;
import com.example.appbussines.Fragments.ListPersonalFragment;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewPersonalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPersonalFragment extends Fragment {
    //transacciones
    private View view;
    private onFragmentBtnSelected listener;
    private Personal personal;

    // wigets y campos de texto
    private TextView textViewFirstName;
    private TextView textViewLastName;
    private TextView textViewEmail;
    private TextView textViewPosition;
    private TextView textViewDateBirthday;
    private TextView textViewDateincome;
    private TextView textViewCountry;
    private TextView textViewAge;
    private SwitchMaterial switchStatus;
    // botones
    private Button buttonModificar;
    private Button buttonEliminar;
    private Button buttonSalir;




    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public ViewPersonalFragment() {}
    public ViewPersonalFragment(Personal personal) {
        this.personal = personal;
        Log.d("FEP",personal.getFirstname());
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewPersonalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewPersonalFragment newInstance(String param1, String param2) {
        ViewPersonalFragment fragment = new ViewPersonalFragment();
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
        view = inflater.inflate(R.layout.fragment_view_personal, container, false);
        initComponents();
        setData();
        initButtons();
        return view;
    }
    private void initComponents(){
         textViewFirstName = view.findViewById(R.id.textView_viewperson_firstname);
         textViewLastName = view.findViewById(R.id.textView_viewperson_lastname);
         textViewEmail = view.findViewById(R.id.textView_viewperson_email);
         textViewPosition = view.findViewById(R.id.textView_viewperson_position);
         textViewDateBirthday =  view.findViewById(R.id.textView_viewperson_DateBirthday);
         textViewDateincome = view.findViewById(R.id.textView_viewperson_DateIncome);
         textViewCountry = view.findViewById(R.id.textView_viewperson_country);
         textViewAge = view.findViewById(R.id.textView_viewperson_age);
         switchStatus = view.findViewById(R.id.switch_view_personal);
         // buttons
         buttonModificar= view.findViewById(R.id.button_editar_view_personal);
         buttonEliminar= view.findViewById(R.id.button_eliminar_view_personal);
         buttonSalir= view.findViewById(R.id.button_salir_view_personal);


    }
    private void setData(){
        if(personal != null){
            textViewFirstName.setText(personal.getFirstname());
            textViewLastName.setText(personal.getLastname());
            textViewEmail.setText(personal.getEmail());
            textViewPosition.setText(personal.getPosition());
            textViewDateBirthday.setText(personal.getBirthdate());
            textViewDateincome.setText(personal.getIncomingdate());
            textViewCountry.setText(personal.getCountry());
            textViewAge.setText(personal.getAge());
            // no se que haCer con el switchStatus :'v

        }


    }private  void initButtons(){
        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected( new EditPersonalFragment(personal));
            }
        });
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Alert dialog de eliminación
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("¿Desea eliminar el registro?")
                        .setCancelable(false)
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // aqui actualizar
                                // mDatabase.child("Cargo").child(cargo.getCode()).child("status").setValue(0);
                                Toast.makeText(getActivity().getApplicationContext(),"Se eliminó el registro", Toast.LENGTH_SHORT).show();
                                listener.onButtonSelected( new ListPersonalFragment());
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



            }
        });
        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected( new ListPersonalFragment());
            }
        });
    }
}