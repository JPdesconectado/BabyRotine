package com.example.babyrotine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.babyrotine.entity.BabyScheme;

public class AddSchemeActivity extends AppCompatActivity {

    TextView addActivityTextView;
    BabyScheme babyScheme;
    int position;
    int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addActivityTextView = findViewById(R.id.addActivityTextView);
        babyScheme = new BabyScheme();

    }

    public void wakingUp(View view){
        addActivityTextView.setText(R.string.acordou);
        babyScheme.setImage(R.drawable.waking_up);

    }
    public void sleeping(View view){
        addActivityTextView.setText(R.string.dormiu);
        babyScheme.setImage(R.drawable.sleeping);
    }
    public void changed(View view){
        addActivityTextView.setText(R.string.trocou);
        babyScheme.setImage(R.drawable.changed);
    }
    public void feed(View view){
        addActivityTextView.setText(R.string.alimentou);
        babyScheme.setImage(R.drawable.eat);
    }

    public void concluir(View view){
        Bundle bundle = new Bundle();
        babyScheme.setActivity("ATIVIDADE: " + addActivityTextView.getText().toString());
        babyScheme.setHour("HORA: " + babyScheme.makeHour());
        bundle.putParcelable("babyScheme", babyScheme);
        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }
}
