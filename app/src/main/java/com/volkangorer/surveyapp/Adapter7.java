package com.volkangorer.surveyapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter7 extends RecyclerView.Adapter<Adapter7.PostHolder> {

    private ArrayList<Post7> postArrayList7;

    public Adapter7(ArrayList<Post7> postArrayList7) {
        this.postArrayList7 = postArrayList7;
    }
    @NonNull

    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row7, parent, false);
        return new PostHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter7.PostHolder holder, int position) {
        holder.project1.setText("Proje: "+postArrayList7.get(position).project);
        holder.point1.setText("Puan: "+postArrayList7.get(position).point);
        holder.rank.setText(postArrayList7.get(position).rank+".");


    }

    @Override
    public int getItemCount() {
        return postArrayList7.size();
    }

    public class PostHolder  extends RecyclerView.ViewHolder{
        private TextView project1;
        private TextView point1;
        private TextView rank;



        public PostHolder(@NonNull  View itemView) {
            super(itemView);
            this.project1 = itemView.findViewById(R.id.project);
            this.point1 = itemView.findViewById(R.id.point);
            this.rank = itemView.findViewById(R.id.rank);



        }
    }
}