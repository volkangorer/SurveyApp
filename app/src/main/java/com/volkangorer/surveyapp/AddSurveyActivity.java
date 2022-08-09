package com.volkangorer.surveyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.volkangorer.surveyapp.databinding.ActivityAddSurveyBinding;

import java.util.ArrayList;
import java.util.Map;

public class AddSurveyActivity extends AppCompatActivity {
    ActivityAddSurveyBinding binding;
    FirebaseFirestore firebaseFirestore;

    Intent intent;
    String id;

    Adapter4 adapter4;
    ArrayList<Post4> postArrayList4;
    String deviceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddSurveyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseFirestore = FirebaseFirestore.getInstance();

        postArrayList4 = new ArrayList<>();

        intent = getIntent();
        deviceId = intent.getStringExtra("deviceId");



        getData();

        binding.recyclerView4.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter4 = new Adapter4(postArrayList4);
        binding.recyclerView4.setAdapter(adapter4);
    }

    public void getData(){
        firebaseFirestore.collection("Surveys").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(AddSurveyActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }

                if(value != null){

                    for(DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> data = snapshot.getData();

                        String id = snapshot.getId();
                        String surveyName = (String) data.get("surveyName");
                        String explanation = (String) data.get("explanation");
                        String preparedTime = (String) data.get("preparedTime");




                        Post4 post4 = new Post4(surveyName,id,preparedTime,explanation,deviceId,"0");
                        postArrayList4.add(post4);

                    }
                    adapter4.notifyDataSetChanged();

                }
            }
        });
    }
}