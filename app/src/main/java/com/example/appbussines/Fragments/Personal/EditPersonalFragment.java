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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbussines.Entities.Pais;
import com.example.appbussines.Entities.Personal;
import com.example.appbussines.Fragments.ListPersonalFragment;
import com.example.appbussines.Interfaces.Validaciones;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPersonalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPersonalFragment extends Fragment {
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]
    Validaciones objValidar; //objeto de nuestro clase Validaciones

    //transacciones
    private View view;
    private onFragmentBtnSelected listener;
    private Personal personal;

    // wigets y campos de texto
    private EditText textEditFirstName;
    private EditText textEditLastName;
    private EditText textEditEmail;
    private EditText textEditAge;

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

    // ignorar
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    // ignorar

    public EditPersonalFragment() {
        // Required empty public constructor
    }
    public EditPersonalFragment(Personal personal){
        this.personal = personal;
    }

    public static EditPersonalFragment newInstance(String param1, String param2) {
        EditPersonalFragment fragment = new EditPersonalFragment();
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
        view = inflater.inflate(R.layout.fragment_edit_personal, container, false);
        // el orden de invocacion es importante :v
        initComponentes();;
        initPositions();
        initCountries();
        initDates();
        initButtons();

        setData();
        return view;
    }

    private void initComponentes(){
        //EditText
        textEditFirstName = view.findViewById(R.id.editText_EditPerson_firstName);
        textEditLastName = view.findViewById(R.id.editText_editperson_Lastname);
        textEditEmail =  view.findViewById(R.id.editText_editperson_Email);
        textEditAge = view.findViewById(R.id.editText_editperson_Age);

        //fechas
        editTextDateBirthday = view.findViewById(R.id.editText_editperson_DateBirthday);
        editTextDateincome = view.findViewById(R.id.editText_editperson_DateIncome);

        //spinners
        spinnerPositions = view.findViewById(R.id.spinnerPosition_edit_personal);
        spinnerCountries= view.findViewById(R.id.spinnerCountries_edit_personal);

        //buttons
        buttonAceptar = view.findViewById(R.id.button_guardar_edit_personal);
        buttonCancelar = view.findViewById(R.id.button_cancelar_edit_personal);

        //swithc
        switchMaterialstate = view.findViewById(R.id.switch_edit_personal);
    }
    private void initPositions(){
        //campos
        //spinner de cargos
        listPositions = getListPositions();
        arrayAdapterPositions= new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,listPositions);
        arrayAdapterPositions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPositions.setAdapter(arrayAdapterPositions);
    }
    private void  initCountries(){
        //spinner paises
        listCountries = getListCountries();
        arrayAdapterCountries = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,listCountries);
        arrayAdapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountries.setAdapter(arrayAdapterCountries);
    }
    private void initDates(){

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

        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countrySelected = spinnerCountries.getSelectedItem().toString();
                positionSelected = spinnerPositions.getSelectedItem().toString();
                stateSelected = 1;
                Log.d("APF",countrySelected);
                Log.d("APF",positionSelected);
                Log.d("APF",stateSelected+"");
              //  if(actualizarPersonal())
                listener.onButtonSelected( new ViewPersonalFragment(personal));
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected( new ViewPersonalFragment( personal));
            }
        });
    }
    private void setData(){
        if(personal != null){
            textEditFirstName.setText(personal.getFirstname());
            textEditLastName.setText(personal.getLastname());
            textEditEmail.setText(personal.getEmail());
            textEditAge.setText(personal.getAge());
            editTextDateBirthday.setText(personal.getBirthdate());
            editTextDateincome.setText(personal.getIncomingdate());
            //spinnerPositions
            //añadir los demas, os spinner y el swithc no me acuerdo :v

            boolean sw = false;
            switch (personal.getState()) {
                case 1:   sw = true ; break;
                case 2:   sw = false; break;
                default:
            }
            switchMaterialstate.setChecked(sw);



        }

    }
    private List<String> getListPositions(){
        //si es posible crear un sigleton para no tener que estar pidiendo muchas veces  a la base de datos la lista de posiciones
        //
        List<String> positions = new ArrayList<>();
        positions.add("Consultor");
        positions.add("Personal");
        positions.add("Asistente");
        positions.add("Director");
        return positions;
    }
    private List<String> getListCountries(){
        List<String> countries = new ArrayList<>();
        countries.add("Peru");
        countries.add("Chile");
        countries.add("Colombia");
        countries.add("Brazil");
        return countries;
    }

    private boolean actualizarPersonal(){
        String id=""; boolean sw = false; int status = 1000, cod=0;

        if(!objValidar.Vacio(textEditFirstName) && !objValidar.Vacio(textEditLastName) && !objValidar.Vacio(textEditEmail)  ) {
            //
            String firstname = textEditFirstName.getText().toString();
            String lastname = textEditLastName.getText().toString();
            String email = textEditEmail.getText().toString();
            String position = positionSelected;
            String incomingdate = editTextDateincome.getText().toString();
            String birthdate = editTextDateBirthday.getText().toString();
            String country = countrySelected;
            String age = textEditAge.getText().toString();
            sw = switchMaterialstate.isChecked();
            // evaluando estado
            if (sw) status = 1; // activo
            else status = 2;    // inactivo

            // generando codigo de trabajador
           cod = ((int) (Math.random() * 100) + 1)*100 + ((int) (Math.random() * 100) + 1)*10+((int) (Math.random() * 100) + 1);
           id = firstname.substring(0,2).toUpperCase() + lastname.substring(0,2).toUpperCase() +cod ;

            updatePerson(id,firstname,lastname,email,position,incomingdate,birthdate,country,age,status);;

            return true;

        }else{
            Toast.makeText(getActivity().getApplicationContext(),"Ingrese valores", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private void updatePerson(String id, String firstname, String lastname, String email, String position, String incomingdate, String birthdate, String country, String age, int status)  {
        personal = new Personal(id,firstname,lastname,email,position,incomingdate,birthdate,country,age,status);
        Map<String, Object> personalMap = personal.toMap();
        try {
            mDatabase.child("Personal").child(id).setValue(personalMap);
            // mDatabase.child("Personal").child(id).updateChildren(personalMap);
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),"El Pais "+ id+" a sido actualizado", Toast.LENGTH_SHORT).show();
        }catch (Exception e ) {}

    }



}