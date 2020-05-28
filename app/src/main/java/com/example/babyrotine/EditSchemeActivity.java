package com.example.babyrotine;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.babyrotine.entity.BabyScheme;

import java.util.Calendar;

public class EditSchemeActivity  extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    TextView hourTextView;
    TextView textView;
    Button pickButton;
    BabyScheme babyScheme;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        textView = findViewById(R.id.textView);
        pickButton = findViewById(R.id.pickButton);
        pickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimerPicker();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        Bundle bundle = getIntent().getExtras();
        babyScheme = bundle.getParcelable("babyScheme");

        if(babyScheme.getActivity().equals("ATIVIDADE: DORMIU") || babyScheme.getActivity().equals("ATIVIDADE: ACORDOU")){
            textView.setText(R.string.textinho);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hourTextView = findViewById(R.id.hourTextView);
        Calendar c = Calendar.getInstance();
        int second = c.get(Calendar.SECOND);
        if(hourOfDay < 10 && minute < 10){
            hourTextView.setText("0" + hourOfDay + ":0" + minute + ":" + second);
        }
        if(hourOfDay > 10 && minute < 10){
            hourTextView.setText(hourOfDay + ":0" + minute + ":" + second);
        }

        if (hourOfDay < 10 && minute > 10){
            hourTextView.setText("0" + hourOfDay + ":" + minute + ":" + second);
        }

        if(hourOfDay > 10 && minute > 10){
            hourTextView.setText(hourOfDay + ":" + minute + ":" + second);
        }
    }

    public void concluir(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        babyScheme.setHour("HORA: " + hourTextView.getText());
        bundle.putParcelable("babyScheme", babyScheme);
        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }
}
