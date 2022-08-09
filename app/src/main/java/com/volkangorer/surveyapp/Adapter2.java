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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.PostHolder> {

    private ArrayList<Post2> postArrayList2;

    public Adapter2(ArrayList<Post2> postArrayList2) {
        this.postArrayList2 = postArrayList2;
    }
    @NonNull

    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row2, parent, false);
        return new PostHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter2.PostHolder holder, int position) {
        System.out.println(postArrayList2.get(position).question);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        holder.question.setText(postArrayList2.get(position).question);
        holder.option.setText(postArrayList2.get(position).option);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("Questions").document(postArrayList2.get(position).questionId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(holder.itemView.getContext(),"Başarıyla Silindi",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(holder.itemView.getContext(),DetailActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("id",postArrayList2.get(position).id);
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return postArrayList2.size();
    }

    public class PostHolder  extends RecyclerView.ViewHolder{
        private TextView question;
        private TextView option;
        ImageView imageView;


        public PostHolder(@NonNull  View itemView) {
            super(itemView);
            this.question = itemView.findViewById(R.id.question);
            this.option = itemView.findViewById(R.id.type);
            this.imageView = itemView.findViewById(R.id.delete2);


        }
    }
}
