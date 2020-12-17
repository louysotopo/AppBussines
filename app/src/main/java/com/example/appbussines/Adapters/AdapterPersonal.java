package com.example.appbussines.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbussines.Entities.Personal;

import com.example.appbussines.Fragments.Personal.ViewPersonalFragment;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;

import java.util.List;

public class AdapterPersonal  extends  RecyclerView.Adapter<AdapterPersonal.ViewHolder> {
    private List<Personal> personalList;
    private LayoutInflater layoutInflater;
    private Context context;
    private onFragmentBtnSelected listener;

    public AdapterPersonal( List<Personal> personalList, Context context){
        if(context  == null){
            System.out.println(" AQUI CONTEXT NULL ********************************************" );
        }
        try{
        this.personalList = personalList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        if( context instanceof  onFragmentBtnSelected ){
            listener= (onFragmentBtnSelected) context;
        }
        else{
            Log.d("LPF","Implemeentar listener");
        }
        }catch(Exception e){
            System.out.println(" EXCEPTION ********************************************" );
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_personal,null);
        return new AdapterPersonal.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(this.personalList.get(position) );
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected( new ViewPersonalFragment( personalList.get(position) ));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.personalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_lastname;
        TextView txt_position;
        TextView txt_dni;
        TextView txt_state;
        LinearLayoutCompat linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_lastname = itemView.findViewById(R.id.text_item_personal_nombre);
            txt_position = itemView.findViewById(R.id.text_item_personal_cargo);
            txt_dni = itemView.findViewById(R.id.text_item_personal_dni);
            txt_state = itemView.findViewById(R.id.text_item_personal_state);
            linearLayout =itemView.findViewById(R.id.item_persona_layout);

        }
        void bindData(final Personal personal){
            txt_lastname.setText(personal.getLastname());
            txt_position.setText(personal.getPosition());
            txt_dni.setText(personal.getId());
            txt_state.setText(personal.getStatusLabel());
        }

    }
}

