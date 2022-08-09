package com.volkangorer.surveyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.volkangorer.surveyapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
        ActivityMainBinding binding;
        private FirebaseAuth auth;
        FirebaseFirestore firebaseFirestore;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding =  ActivityMainBinding.inflate(getLayoutInflater());
            View view = binding.getRoot();
            setContentView(view);

            auth = FirebaseAuth.getInstance();

            FirebaseUser user = auth.getCurrentUser();
            firebaseFirestore = FirebaseFirestore.getInstance();

            if(user != null){
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }

        public void enterOnClicked(View view){
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();

            if(email.equals("") || password.equals("")){
                Toast.makeText(this,"E mail veya Şifrenizi doğru giriniz",Toast.LENGTH_LONG).show();
            }else{
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        /*public void registerOnClicked(View view){
            Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
        }*/
}
