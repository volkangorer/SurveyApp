package com.volkangorer.surveyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.volkangorer.surveyapp.databinding.ActivityCreateSurveyBinding;
import com.volkangorer.surveyapp.databinding.ActivityShowSurveysBinding;

import java.util.ArrayList;
import java.util.Map;

public class ShowSurveysActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    ArrayList<Post> postArrayList;
    Adapter adapter;
    @NonNull ActivityShowSurveysBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowSurveysBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        postArrayList = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        postArrayList.clear();
        getData();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(postArrayList);
        binding.recyclerView.setAdapter(adapter);
    }




    public void getData(){

        firebaseFirestore.collection("Surveys").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable  QuerySnapshot value, @Nullable  FirebaseFirestoreException error) {
                postArrayList.clear();
                if(error != null){
                    Toast.makeText(ShowSurveysActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }

                if(value != null){

                    for(DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> data = snapshot.getData();


                        String surveyname = (String) data.get("surveyName");
                        String explanation = (String) data.get("explanation");
                        String date1 = (String) data.get("date1");
                        String date2 = (String) data.get("date2");
                        String id = snapshot.getId();


                        Post post = new Post(surveyname,explanation,date1,date2,id);
                        postArrayList.add(post);

                    }
                    adapter.notifyDataSetChanged();

                }
            }
        });
    }
}