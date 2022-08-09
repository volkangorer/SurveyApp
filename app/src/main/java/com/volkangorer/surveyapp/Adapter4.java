package com.volkangorer.surveyapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.transition.Hold;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter4 extends RecyclerView.Adapter<Adapter4.PostHolder> {

    private ArrayList<Post4> postArrayList4;

    public Adapter4(ArrayList<Post4> postArrayList4) {
        this.postArrayList4 = postArrayList4;
    }
    @NonNull

    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row4, parent, false);
        return new PostHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter4.PostHolder holder, int position) {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        holder.surveyName.setText(postArrayList4.get(position).surveyName);
        holder.preparedTime.setText(postArrayList4.get(position).preparedTime);
        //holder.explanation.setText(postArrayList4.get(position).explanation);

        if (postArrayList4.get(position).act.equals("1")){
            holder.imageView.setVisibility(View.GONE);
        }else {
            holder.imageView2.setVisibility(View.GONE);
        }

        Map<String,Object> map = new HashMap<>();
        map.put("status","Dolu");
        map.put("survey",postArrayList4.get(position).id);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (postArrayList4.get(position).act.equals("0")){
                    firebaseFirestore.collection("Device").document(postArrayList4.get(position).deviceId).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Map<String,Object> map2 = new HashMap<>();
                            map2.put("status","Yayınlandı");
                            firebaseFirestore.collection("Surveys").document(postArrayList4.get(position).id).update(map2);
                            Intent intent = new Intent(holder.itemView.getContext(),SendSurveyActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            holder.itemView.getContext().startActivity(intent);
                        }
                    });
                }

            }
        });

        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(),ShowStatisticsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id",postArrayList4.get(position).id);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        



    }

    @Override
    public int getItemCount() {
        return postArrayList4.size();
    }

    public class PostHolder  extends RecyclerView.ViewHolder{
        TextView surveyName;
        //TextView explanation;
        TextView preparedTime;
        ImageView imageView;
        ImageView imageView2;



        public PostHolder(@NonNull  View itemView) {
            super(itemView);
            this.surveyName = itemView.findViewById(R.id.surveyName);
            this.imageView = itemView.findViewById(R.id.submit);
            //this.explanation = itemView.findViewById(R.id.explanation);
            this.preparedTime = itemView.findViewById(R.id.preparedTime);
            this.imageView2 = itemView.findViewById(R.id.examine);




        }
    }
}