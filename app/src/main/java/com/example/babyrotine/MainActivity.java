package com.example.babyrotine;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.babyrotine.entity.BabyScheme;
import com.example.babyrotine.entity.BabySchemeAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BabySchemeAdapter adapter;
    public final static int REQUEST_EDITAR_ATIVIDADE = 1;
    public final static int REQUEST_INSERIR_ATIVIDADE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new BabySchemeAdapter(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelp(adapter));
        touchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_INSERIR_ATIVIDADE){
            Bundle bundle = data.getExtras();
            BabyScheme babyScheme = bundle.getParcelable("babyScheme");
            adapter.inserir(babyScheme);
        }

        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_EDITAR_ATIVIDADE){
            Bundle bundle = data.getExtras();
            BabyScheme babyScheme = bundle.getParcelable("babyScheme");
            int position = bundle.getInt("position");
            adapter.editar(babyScheme, position);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.inserirMenu){
            Bundle bundle = new Bundle();
            bundle.putInt("request_code", REQUEST_INSERIR_ATIVIDADE);
            Intent intent = new Intent(this, AddSchemeActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_INSERIR_ATIVIDADE);
        }

        if(id == R.id.mostrarTudoMenu){
            adapter.mostrarTudo();
        }

        if(id == R.id.acordarMenu){
            adapter.unicaAtividade("ATIVIDADE: ACORDOU");
        }

        if(id == R.id.dormirMenu){
            adapter.unicaAtividade("ATIVIDADE: DORMIU");
        }

        if(id == R.id.trocarMenu){
            adapter.unicaAtividade("ATIVIDADE: TROCOU");
        }

        if(id == R.id.alimentarMenu){
            adapter.unicaAtividade("ATIVIDADE: ALIMENTOU");
        }

        return super.onOptionsItemSelected(item);
    }

}
