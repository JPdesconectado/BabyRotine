package com.example.babyrotine.entity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babyrotine.EditSchemeActivity;
import com.example.babyrotine.MainActivity;
import com.example.babyrotine.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class BabySchemeAdapter extends RecyclerView.Adapter {

    private List<BabyScheme> babySchemeList;
    private int posicaoRemovidoRecentemente;
    private BabyScheme atividadeRemovidaRecentemente;
    private AppCompatActivity activity;
    private AppDatabase db;


    public BabySchemeAdapter(AppCompatActivity activity){
        this.activity = activity;
        this.babySchemeList = new ArrayList<>();
        db = AppDatabase.getDatabase(activity);
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<BabyScheme> lista = db.babySchemeDAO().getAll();
                for(BabyScheme b : lista)
                    babySchemeList.add(b);
            }
        });
    }

    public void mostrarTudo(){
        babySchemeList.clear();
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<BabyScheme> lista = new ArrayList<>();
                if(!lista.isEmpty())
                    lista.clear();
                lista = db.babySchemeDAO().getAll();
                for(int i = 0; i < lista.size(); i++){
                    babySchemeList.add(lista.get(i));
                }
            }
        });
        notifyDataSetChanged();
    }

    public void unicaAtividade(final String atividade1){
        babySchemeList.clear();
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < db.babySchemeDAO().findByActivityBaby(atividade1).size(); i++){
                    babySchemeList.add(db.babySchemeDAO().findByActivityBaby(atividade1).get(i));
                }
            }
        });
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        BabySchemeViewHolder viewHolder = new BabySchemeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        BabySchemeViewHolder viewHolder = (BabySchemeViewHolder) holder;
        viewHolder.activityTextView.setText(babySchemeList.get(position).getActivity());
        viewHolder.hourTextView.setText(babySchemeList.get(position).getHour());
        viewHolder.imageView.setImageResource(babySchemeList.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getBaseContext(), EditSchemeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("babyScheme", babySchemeList.get(holder.getAdapterPosition()));
                bundle.putInt("position", holder.getAdapterPosition());
                bundle.putInt("request code", 1);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return babySchemeList.size();
    }


    public void inserir(final BabyScheme babyScheme){
        if(!babySchemeList.isEmpty()){
            if(babySchemeList.get(getItemCount() - 1).getActivity().equals("ATIVIDADE: DORMIU") && !babyScheme.getActivity().equals("ATIVIDADE: ACORDOU")){
                AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        BabyScheme bs = new BabyScheme("ATIVIDADE: ACORDOU");
                        bs.setHour(bs.makeHour());
                        bs.setImage(R.drawable.waking_up);
                        db.babySchemeDAO().insertActivityBaby(bs);
                        babySchemeList.add(bs);
                    }
                });
            }
        }
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                db.babySchemeDAO().insertActivityBaby(babyScheme);
                babySchemeList.add(babyScheme);
            }

        });
        notifyItemInserted(getItemCount());

    }

    public void remover(int position){
        posicaoRemovidoRecentemente = position;
        atividadeRemovidaRecentemente = babySchemeList.get(position);

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.babySchemeDAO().deleteActivityBaby(atividadeRemovidaRecentemente);
            }
        });
        babySchemeList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,this.getItemCount());

        Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.constraintLayout), "Item deletado",Snackbar.LENGTH_LONG);
        snackbar.setAction("Desfazer?", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        db.babySchemeDAO().insertActivityBaby(atividadeRemovidaRecentemente);
                    }
                });
                babySchemeList.add(posicaoRemovidoRecentemente, atividadeRemovidaRecentemente);
                notifyItemInserted(posicaoRemovidoRecentemente);
            }
        });
        snackbar.show();
    }

    public void editar(final BabyScheme babyScheme, int position){
        babySchemeList.get(position).setHour(babyScheme.getHour());

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.babySchemeDAO().updateActivityBaby(babyScheme);
            }
        });
        notifyItemChanged(position);
    }

    public static class BabySchemeViewHolder extends RecyclerView.ViewHolder{

        TextView activityTextView;
        TextView hourTextView;
        ImageView imageView;


        public BabySchemeViewHolder(@NonNull View itemView){
            super(itemView);
            itemView.setTag(this);
            activityTextView = itemView.findViewById(R.id.activityTextView);
            hourTextView = itemView.findViewById(R.id.hourTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

}


