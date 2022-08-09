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
import com.volkangorer.surveyapp.databinding.ActivitySendSurveyBinding;


import java.util.ArrayList;
import java.util.Map;

public class SendSurveyActivity extends AppCompatActivity {
    ActivitySendSurveyBinding binding;
    FirebaseFirestore firebaseFirestore;
    Intent intent;
    String id;

    Adapter3 adapter3;
    ArrayList<Post3> postArrayList3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendSurveyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        postArrayList3 = new ArrayList<>();

        firebaseFirestore=FirebaseFirestore.getInstance();

        getData();

        binding.recyclerView3.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter3 = new Adapter3(postArrayList3);
        binding.recyclerView3.setAdapter(adapter3);
    }

    public void getData(){

        firebaseFirestore.collection("Device").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable  QuerySnapshot value, @Nullable  FirebaseFirestoreException error) {
                postArrayList3.clear();

                if(error != null){
                    Toast.makeText(SendSurveyActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }

                if(value != null){

                    for(DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> data = snapshot.getData();

                        String id = snapshot.getId();
                        String password = (String) data.get("password");
                        String status = (String) data.get("status");
                        String survey =  (String) data.get("survey");



                        Post3 post3 = new Post3(id,password,status,survey);
                        postArrayList3.add(post3);

                    }
                    adapter3.notifyDataSetChanged();

                }
            }
        });
    }
}