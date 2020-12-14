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
import com.example.appbussines.Fragments.Cargos.ViewPositionFragment;
import com.example.appbussines.Fragments.Paises.ViewCountryFragment;
import com.example.appbussines.Fragments.Personal.ViewPersonalFragment;
import com.example.appbussines.Interfaces.onFragmentBtnSelected;
import com.example.appbussines.R;

import java.util.List;

public class AdapterPosition extends RecyclerView.Adapter<AdapterPosition.ViewHolder>  {
    List<Cargo> listCargos;
    private final LayoutInflater layoutInflater;
    private Context context;
    private onFragmentBtnSelected listener;

    public AdapterPosition( List<Cargo> cargosList, Context context){
        this.listCargos = cargosList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        if( context instanceof  onFragmentBtnSelected ){
            listener= (onFragmentBtnSelected) context;
        }
        else{
            Log.d("AP","Implemeentar listener");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_position,null);
        return new AdapterPosition.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(this.listCargos.get(position) );
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected( new ViewPositionFragment(listCargos.get(position) ));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCargos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_code;
        TextView txt_name;
        TextView txt_state;
        LinearLayoutCompat linearLayout;
        public ViewHolder(@NonNull View itemView) {
           super(itemView);
            txt_code = itemView.findViewById(R.id.text_item_position_code);
            txt_name = itemView.findViewById(R.id.text_item_position_nombre);
            txt_state = itemView.findViewById(R.id.text_item_position_state);
            linearLayout = itemView.findViewById(R.id.item_position_layout);
       }
        void bindData(final Cargo cargo){
            txt_code.setText(cargo.getCode());
            txt_name.setText(cargo.getName());
            txt_state.setText(cargo.getStatus()+"");
        }
   }


}
