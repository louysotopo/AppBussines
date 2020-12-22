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

import com.example.appbussines.Adapters.AdapterPosition;
import com.example.appbussines.Entities.Cargo;
import com.example.appbussines.Entities.Personal;
import com.example.appbussines.Fragments.ListPersonalFragment;
import com.example.appbussines.Interfaces.Validaciones;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPersonalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPersonalFragment extends Fragment {
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]
    Validaciones objValidar; //objeto de nuestro clase Validaciones

    // datos
   //private EditText _id;
    private EditText _firstname;
    private EditText _lastname;
    private EditText _email;
    private EditText _age;

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
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]
        // validaciones
        objValidar = new Validaciones();
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
        //database=FirebaseDatabase.getInstance();
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
        //listPositions = getListPositions();
        //
        listPositions = new ArrayList<>();
        mDatabase.child("Cargo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds: snapshot.getChildren()){
                        String name;
                        int status = Integer.parseInt(ds.child("status").getValue().toString());
                        if(status==1){
                            name = ds.child("name").getValue().toString();
                        listPositions.add(name);
                        }
                    }
                    spinnerPositions = view.findViewById(R.id.spinnerPosition_add_personal);
                    arrayAdapterPositions= new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,listPositions);
                    arrayAdapterPositions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerPositions.setAdapter(arrayAdapterPositions);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //


    }
    private void  initCountries(){

        //
        listCountries= new ArrayList<>();
        mDatabase.child("Pais").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds: snapshot.getChildren()){
                        String name;
                        int status = Integer.parseInt(ds.child("status").getValue().toString());
                        if(status==1){
                            name = ds.child("name").getValue().toString();
                            listCountries.add(name);
                        }
                    }
                    spinnerCountries= view.findViewById(R.id.spinnerCountries_add_personal);
                    arrayAdapterCountries = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,listCountries);
                    arrayAdapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCountries.setAdapter(arrayAdapterCountries);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //
        //spinner paises


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

                if(registrarPersonal())
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
        switchMaterialstate.setChecked(true); // por defecto, Activo = 1, Inactivo =2

        // datos que falta capturar
        _firstname  = view.findViewById(R.id.editText_TextPersonName);
        System.out.println(" AQAUI **************************"+_firstname.getText().toString());
        _lastname = view.findViewById(R.id.editText_TextPersonLastname);
        System.out.println(" AQAUI **************************"+_lastname.getText().toString());
        _age = view.findViewById(R.id.editText_TextPersonAge);
        System.out.println(" AQAUI **************************"+_age.getText().toString());
        _email = view.findViewById(R.id.editText_TextPersonEmail);
        System.out.println(" AQAUI **************************"+_age.getText().toString());

    }


    private boolean registrarPersonal(){
         boolean sw = false; int cod=0;
        String id = "";
        String firstname = "";
        String lastname = "";
        String email = "";
        String position = "";
        String incomingdate = "00/00/0000";
        String birthdate = "00/00/0000";
        String country = "";
        String age = "";
        int status = 1000;
        if(!objValidar.Vacio(_firstname) && !objValidar.Vacio(_lastname) && !objValidar.Vacio(_email)  ) {

            firstname = _firstname.getText().toString();
            lastname = _lastname.getText().toString();
            email = _email.getText().toString();
            position = positionSelected;
            incomingdate = editTextDateincome.getText().toString();
            birthdate = editTextDateBirthday.getText().toString();
            country = countrySelected;
            age = _age.getText().toString();
            sw = switchMaterialstate.isChecked();

            // evaluando estado
            if (sw) status = 1; // activo
            else status = 2;    // inactivo

            // generando codigo de trabajador
            cod = ((int) (Math.random() * 100) + 1)*100 + ((int) (Math.random() * 100) + 1)*10+((int) (Math.random() * 100) + 1);
            id = firstname.substring(0,2).toUpperCase() + lastname.substring(0,2).toUpperCase() +cod ;

            addPersonal(id,firstname,lastname,email,position,incomingdate,birthdate,country,age,status);
            return true;

        }else{
            Toast.makeText(getActivity().getApplicationContext(),"Ingrese valores", Toast.LENGTH_SHORT).show();
           return false; }
    }
    private void addPersonal(String id, String firstname, String lastname, String email, String position, String incomingdate, String birthdate, String country, String age, int status)  {
        Personal personal = new Personal(id,firstname,lastname,email,position,incomingdate,birthdate,country,age,status);

        mDatabase.child("Personal").child(id).setValue(personal);
        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),"El Personal: "+ id +" a sido agregado", Toast.LENGTH_SHORT).show();

    }


}