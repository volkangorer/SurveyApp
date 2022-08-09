package com.volkangorer.surveyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.volkangorer.surveyapp.databinding.ActivityStatisticsBinding;

import java.util.ArrayList;
import java.util.Map;

public class StatisticsActivity extends AppCompatActivity {
    ActivityStatisticsBinding binding;
    FirebaseFirestore firebaseFirestore;

    Intent intent;
    String id;
    String deviceId;
    Adapter4 adapter4;
    ArrayList<Post4> postArrayList4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatisticsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        firebaseFirestore = FirebaseFirestore.getInstance();

        postArrayList4 = new ArrayList<>();

        //intent = getIntent();
        //deviceId = intent.getStringExtra("deviceId");



        getData();

        binding.recyclerView5.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter4 = new Adapter4(postArrayList4);
        binding.recyclerView5.setAdapter(adapter4);
    }

    public void getData(){
        firebaseFirestore.collection("Surveys").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(StatisticsActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }

                if(value != null){

                    for(DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> data = snapshot.getData();

                        String id = snapshot.getId();
                        String surveyName = (String) data.get("surveyName");
                        String explanation = (String) data.get("explanation");
                        String preparedTime = (String) data.get("preparedTime");




                        Post4 post4 = new Post4(surveyName,id,preparedTime,explanation,"null","1");
                        postArrayList4.add(post4);

                    }
                    adapter4.notifyDataSetChanged();

                }
            }
        });
    }
}