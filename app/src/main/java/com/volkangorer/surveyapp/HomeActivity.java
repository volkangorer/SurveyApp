package com.volkangorer.surveyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;
import com.volkangorer.surveyapp.databinding.ActivityHomeBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    ActivityHomeBinding binding;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //bottomNavigationView = binding.bottomNavigation;




        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        getData();

    }

    public void getData(){
        firebaseFirestore.collection("Users").document(user.getEmail()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable  FirebaseFirestoreException error) {
                Map<String,Object> map = value.getData();
                String name = (String) map.get("name");
                binding.user.setText("Hoşgeldiniz "+name);
                //String reference = (String) map.get("reference");
                //Picasso.get().load(reference).into(binding.userView);
            }
        });
    }



    public void createOnClicked(View view){
        Intent intent = new Intent(HomeActivity.this,CreateSurveyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void showOnClicked(View view){
        Intent intent = new Intent(HomeActivity.this,ShowSurveysActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void sendSurveyOnClicked(View view){
        Intent intent = new Intent(HomeActivity.this,SendSurveyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void staOnClicked(View view){
        Intent intent = new Intent(HomeActivity.this,StatisticsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.option_menu,menu);

        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.signout){
            auth.signOut();

            Intent intentToMain = new Intent(HomeActivity.this,MainActivity.class);
            startActivity(intentToMain);
            finish();

        } 


        return super.onOptionsItemSelected(item);
    }
}