package com.volkangorer.surveyapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.volkangorer.surveyapp.databinding.RecyclerRowBinding;

import java.util.ArrayList;
import java.util.Map;

import io.grpc.internal.JsonUtil;

public class Adapter extends RecyclerView.Adapter<Adapter.PostHolder>{

    @NonNull

    private ArrayList<Post> postArrayList;

    public Adapter(ArrayList<Post> postArrayList) {
        this.postArrayList = postArrayList;
    }

    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PostHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter.PostHolder holder, int position) {
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        holder.recyclerRowBinding.surveyName.setText(postArrayList.get(position).surveyname);
        holder.recyclerRowBinding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("Surveys").document(postArrayList.get(position).id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent =  new Intent(holder.itemView.getContext(),ShowSurveysActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        holder.itemView.getContext().startActivity(intent);
                    }
                });

                /*

                firebaseFirestore.collection("Questions").whereEqualTo("id",postArrayList.get(position).id).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable  QuerySnapshot value, @Nullable  FirebaseFirestoreException error) {

                        if(error != null){
                            Toast.makeText(holder.itemView.getContext(),error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }

                        if(value != null){

                            for(DocumentSnapshot snapshot : value.getDocuments()){
                                Map<String, Object> data = snapshot.getData();

                                String questionId =  snapshot.getId();
                                System.out.println(questionId);
                                System.out.println("buradayız");
                                /*firebaseFirestore.collection("Questions").document(questionId).delete();


                            }


                        }

                    }
                });*/
            }
        });
        holder.recyclerRowBinding.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(holder.itemView.getContext(),DetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id",postArrayList.get(position).id);
                holder.itemView.getContext().startActivity(intent);
                System.out.println(postArrayList.get(position).id);

            }
        });

        /*holder.recyclerRowBinding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(holder.itemView.getContext(),SettingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                holder.itemView.getContext().startActivity(intent);
                intent.putExtra("id",postArrayList.get(position).id);
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder {
        RecyclerRowBinding recyclerRowBinding;
        public PostHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }
}
