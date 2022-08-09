package com.volkangorer.surveyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.volkangorer.surveyapp.databinding.ActivityDetailBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    FirebaseFirestore firebaseFirestore;
    Intent intent;
    String id;

    Adapter2 adapter2;
    ArrayList<Post2> postArrayList2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        postArrayList2 = new ArrayList<>();
        intent = getIntent();
        id = intent.getStringExtra("id");
        firebaseFirestore=FirebaseFirestore.getInstance();

        //postArrayList2.clear();


        getData();


        getQuestions();




        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter2 = new Adapter2(postArrayList2);
        binding.recyclerView2.setAdapter(adapter2);

        System.out.println(postArrayList2+"1");



        /*  */
    }

    public void getData(){
        firebaseFirestore.collection("Surveys").document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable  DocumentSnapshot value, @Nullable  FirebaseFirestoreException error) {
                Map<String,Object> map = value.getData();

                String surveyName = (String) map.get("surveyName");
                String status =  (String) map.get("status");
                String date1 = (String) map.get("date1");
                String date2 = (String) map.get("date2");
                String preparedTime = (String) map.get("preparedTime");
                String explanation  = (String) map.get("explanation");

                binding.explanation.setText(explanation);
                binding.finish.setText(date2);
                binding.start.setText(date1);
                binding.preparedTime.setText(preparedTime);
                binding.status.setText(status);
                binding.surveyName.setText(surveyName);

            }
        });
    }

    public void getQuestions(){

        System.out.println(postArrayList2);
        postArrayList2.clear();
        firebaseFirestore.collection("Questions").whereEqualTo("id",id).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable  QuerySnapshot value, @Nullable  FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(DetailActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }

                if(value != null){

                    for(DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> data = snapshot.getData();

                        String id = (String) data.get("id");
                        String question = (String) data.get("question");
                        String option = (String) data.get("option");
                        String questionId =  snapshot.getId();



                        Post2 post2 = new Post2(question,option,id,questionId);
                        postArrayList2.add(post2);

                    }
                    adapter2.notifyDataSetChanged();

                }
            }
        });
    }



    public void addQuestionOnClicked(View view){
        Intent intent = new Intent(DetailActivity.this,AddQuestionActivity.class);
        intent.putExtra("id",id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


}