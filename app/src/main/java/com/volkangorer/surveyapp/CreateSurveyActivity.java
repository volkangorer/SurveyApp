package com.volkangorer.surveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.volkangorer.surveyapp.databinding.ActivityCreateSurveyBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreateSurveyActivity extends AppCompatActivity {
    ActivityCreateSurveyBinding binding;
    FirebaseFirestore firebaseFirestore;
    int year;
    int month;
    int dayOfMonth;

    int year2;
    int month2;
    int dayOfMonth2;

    String surveyname;
    String explanatiion;

    Random rand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateSurveyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        rand = new Random();
        firebaseFirestore = FirebaseFirestore.getInstance();

        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateSurveyActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                binding.start.setText(day + "/" + month + "/" + year);
                                dayOfMonth = day;


                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();


            }
        });

        binding.finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year2 = calendar.get(Calendar.YEAR);
                month2 = calendar.get(Calendar.MONTH);
                dayOfMonth2 = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateSurveyActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                binding.finish.setText(day + "/" + month + "/" + year);
                                dayOfMonth2 = day;

                            }
                        }, year2, month2, dayOfMonth2);
                datePickerDialog.show();


            }
        });
    }

    public void saveOnClicked(View view){
        explanatiion = binding.explanation.getText().toString();
        surveyname = binding.surveyName.getText().toString();
        String d1 =String.valueOf(dayOfMonth);
        String m1 = String.valueOf(month);
        String y1 = String.valueOf(year);

        String d2 = String.valueOf(dayOfMonth2);
        String m2 = String.valueOf(month2);
        String y2 = String.valueOf(year2);

        String date1 = d1+"/"+m1+"/"+y1;
        String date2 = d2+"/"+m2+"/"+y2;

        String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(Calendar.getInstance().getTime());

        int id = rand.nextInt(999999);

        Map<String,Object> map = new HashMap<>();
        map.put("surveyName",surveyname);
        map.put("explanation",explanatiion);
        map.put("date1",date1);
        map.put("date2",date2);
        map.put("preparedTime",timeStamp);
        map.put("id",id);
        map.put("status","yayınlanmadı");

        firebaseFirestore.collection("Surveys").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Intent intent = new Intent(CreateSurveyActivity.this,HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


    }
}