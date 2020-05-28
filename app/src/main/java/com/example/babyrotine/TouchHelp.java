package com.example.babyrotine;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babyrotine.entity.BabySchemeAdapter;

public class TouchHelp extends ItemTouchHelper.SimpleCallback{

    private BabySchemeAdapter adapter;

    public TouchHelp(BabySchemeAdapter adapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);
        this.adapter = adapter;
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.remover(viewHolder.getAdapterPosition());
    }
}
