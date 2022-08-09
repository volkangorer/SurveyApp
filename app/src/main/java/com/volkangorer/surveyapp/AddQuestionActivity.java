package com.volkangorer.surveyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.volkangorer.surveyapp.databinding.ActivityAddQuestionBinding;

import java.util.HashMap;
import java.util.Map;

public class AddQuestionActivity extends AppCompatActivity {
    ActivityAddQuestionBinding binding;
    FirebaseFirestore firebaseFirestore;
    Intent intent;
    String id;
    String question;
    String option;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddQuestionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        intent = getIntent();
        id = intent.getStringExtra("id");

        firebaseFirestore = FirebaseFirestore.getInstance();



    }

    public  void saveOnClicked(View view){
        question  = binding.question.getText().toString();
        radioGroup = (RadioGroup) binding.radioGroup;
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);

        option =radioButton.getText().toString();

        Map<String,Object> map = new HashMap<>();
        map.put("question",question);
        map.put("option",option);
        map.put("id",id);
        map.put("bir",0);
        map.put("iki",0);
        map.put("uc",0);
        map.put("dort",0);
        map.put("bes",0);


        firebaseFirestore.collection("Questions").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(AddQuestionActivity.this,"Soru Başarıyla Eklendi", Toast.LENGTH_LONG).show();
                binding.question.setHint("soruyu buraya giriniz");

            }
        });
    }

    public void exitOnClicked(View view){
        Intent intent = new Intent(AddQuestionActivity.this,DetailActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}