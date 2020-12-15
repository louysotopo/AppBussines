package com.example.appbussines.Fragments.Personal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appbussines.Entities.Personal;
import com.example.appbussines.Fragments.ListPersonalFragment;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.MainActivity;
import com.example.appbussines.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPersonalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPersonalFragment extends Fragment {

    private FirebaseDatabase database;

    private TextView nombre;
    private TextView apellido;
    private TextView correo;
    private TextView ingreso;
    private TextView nacimiento;
    private TextView edad;

    //transacciones
    private View view;
    private onFragmentBtnSelected listener;

    //dates
    private TextView editTextDateBirthday;
    private TextView editTextDateincome;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private int editText;

    //botones
    private Button buttonAceptar;
    private Button buttonCancelar;

    //spinners
    private Spinner spinnerPositions;
    private Spinner spinnerCountries;
    private List<String> listPositions;
    private List<String> listCountries;
    private ArrayAdapter<String> arrayAdapterPositions;
    private ArrayAdapter<String> arrayAdapterCountries;

    // content of Personal
    private String positionSelected;
    private String countrySelected;
    private int stateSelected;
    private SwitchMaterial switchMaterialstate;

    private ArrayList<Personal> personalList;

    //
    // ignore
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    // fin ignore
    public AddPersonalFragment() { }

    public static AddPersonalFragment newInstance(String param1, String param2) {
        AddPersonalFragment fragment = new AddPersonalFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_personal, container, false);
        initPositions();
        initCountries();
        initDates();
        initEditText();
        initButtons();

        database=FirebaseDatabase.getInstance();
        nombre = (TextView) view.findViewById(R.id.editText_TextPersonName);
        apellido = (TextView) view.findViewById(R.id.editText_TextPersonLastname);
        correo = (TextView) view.findViewById(R.id.editText_TextPersonEmail);
        ingreso = (TextView) view.findViewById(R.id.editText_TextPersonDateIncome);
        nacimiento = (TextView) view.findViewById(R.id.editText_TextPersonDateBirthday);
        edad = (TextView) view.findViewById(R.id.editText_TextPersonAge);

        return view;

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

    private void initPositions(){
        //campos
        //spinner de cargos
        listPositions = getListPositions();
        spinnerPositions = view.findViewById(R.id.spinnerPosition_add_personal);
        arrayAdapterPositions= new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,listPositions);
        arrayAdapterPositions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPositions.setAdapter(arrayAdapterPositions);
    }
    private void  initCountries(){
        //spinner paises
        listCountries = getListCountries();
        spinnerCountries= view.findViewById(R.id.spinnerCountries_add_personal);
        arrayAdapterCountries = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,listCountries);
        arrayAdapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountries.setAdapter(arrayAdapterCountries);
    }
    private void initDates(){
        editTextDateBirthday = view.findViewById(R.id.editText_TextPersonDateBirthday);
        editTextDateBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = 1;
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(), R.style.DialogThemeCalendar, dateSetListener, year, month, day);

                dialog.show();
            }
        });

        editTextDateincome = view.findViewById(R.id.editText_TextPersonDateIncome);
        editTextDateincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = 2;
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(), R.style.DialogThemeCalendar , dateSetListener, year, month, day);

                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;

                if (editText == 1) editTextDateBirthday.setText(date);
                else {
                    editTextDateincome.setText(date);
                }
            }
        };

    }
    private void initButtons(){
        buttonAceptar = view.findViewById(R.id.button_aceptar_add_personal);
        buttonCancelar = view.findViewById(R.id.button_cancelar_add_personal);

        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countrySelected = spinnerCountries.getSelectedItem().toString();
                positionSelected = spinnerPositions.getSelectedItem().toString();
                stateSelected =  1;
                Log.d("APF",countrySelected);
                Log.d("APF",positionSelected);
                Log.d("APF",stateSelected+"");
                //listener.onButtonSelected( new ListPersonalFragment());

                Map<String,Object> map=new HashMap<>();
                map.put("codigo",1);
                map.put("nombre",nombre.getText().toString());
                map.put("apellido",apellido.getText().toString());
                map.put("correo",correo.getText().toString());
                map.put("position",positionSelected);
                map.put("ingreso",ingreso.getText().toString());
                map.put("nacimiento",nacimiento.getText().toString());
                map.put("pais",countrySelected);
                map.put("edad",edad.getText().toString());
                map.put("estado",stateSelected);

                database.getReference().child("usuarios").push().setValue(map);
                listener.onButtonSelected( new ListPersonalFragment());
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onButtonSelected( new ListPersonalFragment());
            }
        });
    }


    private  void initEditText(){
        switchMaterialstate = view.findViewById(R.id.switch_add_personal);
    }

    //  LLAMAR A LA BASE DE DATOS
    private List<String> getListPositions(){
        //  LLAMAR A LA BASE DE DATOS
        //
        List<String> positions = new ArrayList<>();
        positions.add("Consultor");
        positions.add("Personal");
        positions.add("Asistente");
        positions.add("Director");
        return positions;
    }
    private List<String> getListCountries(){
        //  LLAMAR A LA BASE DE DATOS
        List<String> countries = new ArrayList<>();
        countries.add("Peru");
        countries.add("Chile");
        countries.add("Colombia");
        countries.add("Brazil");
        return countries;
    }


}