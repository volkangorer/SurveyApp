package com.volkangorer.surveyapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter3 extends RecyclerView.Adapter<Adapter3.PostHolder> {

    private ArrayList<Post3> postArrayList3;

    public Adapter3(ArrayList<Post3> postArrayList3) {
        this.postArrayList3 = postArrayList3;
    }
    @NonNull

    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row3, parent, false);
        return new PostHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter3.PostHolder holder, int position) {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        holder.deviceName.setText(postArrayList3.get(position).id);
        holder.status.setText(postArrayList3.get(position).status);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(),AddSurveyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("deviceId",postArrayList3.get(position).id);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<>();
                map.put("status","Müsait");
                map.put("survey","null");

                firebaseFirestore.collection("Device").document(postArrayList3.get(position).id).update(map);

                Intent intent = new Intent(holder.itemView.getContext(),SendSurveyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                holder.itemView.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return postArrayList3.size();
    }

    public class PostHolder  extends RecyclerView.ViewHolder{
        private TextView deviceName;
        private TextView status;
        ImageView imageView;
        ImageView imageView2;


        public PostHolder(@NonNull  View itemView) {
            super(itemView);
            this.deviceName = itemView.findViewById(R.id.deviceName);
            this.status = itemView.findViewById(R.id.status);
            this.imageView = itemView.findViewById(R.id.addSurvey);
            this.imageView2 = itemView.findViewById(R.id.delete3);


        }
    }
}
