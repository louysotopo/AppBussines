package com.example.appbussines.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbussines.Entities.Cargo;
import com.example.appbussines.Entities.Pais;
import com.example.appbussines.Fragments.Cargos.ViewPositionFragment;
import com.example.appbussines.Fragments.Paises.ViewCountryFragment;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;

import java.util.List;

public class AdapterCountry extends  RecyclerView.Adapter<AdapterCountry.ViewHolder>{
    List<Pais> listPaises;
    private LayoutInflater layoutInflater;
    private Context context;
    private onFragmentBtnSelected listener;

    public  AdapterCountry (List<Pais> listPaises, Context context){
        if(context  == null){
            System.out.println(" AQUI CONTEXT NULL ********************************************" );
        }
        try{
        this.listPaises = listPaises;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        if( context instanceof  onFragmentBtnSelected ){
            listener= (onFragmentBtnSelected) context;
        }
        else{
            Log.d("AC","Implemeentar listener");
        }
        }catch(Exception e){
            System.out.println(" EXCEPTION ********************************************" );
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_country,null);
        return new AdapterCountry.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(this.listPaises.get(position) );
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected( new ViewCountryFragment(listPaises.get(position) ));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPaises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_code;
        TextView txt_name;
        TextView txt_state;
        LinearLayoutCompat linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_code = itemView.findViewById(R.id.text_item_country_code);
            txt_name = itemView.findViewById(R.id.text_item_country_nombre);
            txt_state = itemView.findViewById(R.id.text_item_country_state);
            linearLayout = itemView.findViewById(R.id.item_country_layout);
        }
        void bindData(final Pais pais){
            txt_code.setText(pais.getCode());
            txt_name.setText(pais.getName());
            txt_state.setText(pais.getStatus()+"");
        }

    }
}
