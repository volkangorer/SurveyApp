package com.volkangorer.surveyapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.volkangorer.surveyapp.databinding.ActivityShowStatisticsBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ShowStatisticsActivity extends AppCompatActivity {
    ActivityShowStatisticsBinding binding;
    FirebaseFirestore firebaseFirestore;
    ArrayList<Post7> postArrayList7;
    Intent intent;
    String id;
    Adapter7 adapter7;
    int i;
    int bir;
    int iki;
    int uc;
    int dort;
    int bes;
    int total;

    ArrayList<PostClassic> list4;

    ArrayList<BarEntry> visitors;
    BarChart barChart;

    ArrayList<BarEntry> visitors1;
    BarChart barChart1;

    ArrayList<BarEntry> visitors2;
    BarChart barChart2;

    ArrayList<Post5> list;
    ArrayList<Post6> list2;
    ArrayList<Integer> list3;
    ArrayList<String> listProje;
    ArrayList<String> listProje2;

    String option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowStatisticsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        firebaseFirestore = FirebaseFirestore.getInstance();
        intent = getIntent();
        id = intent.getStringExtra("id");
        list = new ArrayList();
        list2 = new ArrayList<>();
        list3 = new ArrayList();
        list4 = new ArrayList<>();
        listProje = new ArrayList<>();
        listProje2 = new ArrayList<>();

        postArrayList7 = new ArrayList<>();

        barChart = findViewById(R.id.idBarChart);
        visitors = new ArrayList<>();

        barChart1 = findViewById(R.id.idBarChart2);
        visitors1 = new ArrayList<>();

        barChart2 = findViewById(R.id.idBarChart3);
        visitors2 = new ArrayList<>();
        option = "emoji";

        getData();


    }

    public void getData(){
        Map<String,Object> data2 = new HashMap<>();
        total=0;
        firebaseFirestore.collection("Questions").whereEqualTo("id",id).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable  QuerySnapshot value, @Nullable  FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(ShowStatisticsActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }

                if(value != null){
                    for(DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> data = snapshot.getData();
                        option = (String) data.get("option");


                        if (option.equals("Klasik")){
                            String questionId = snapshot.getId();
                            String question = (String) data.get("question");

                            firebaseFirestore.collection("Answers").document(questionId).collection("Students").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onEvent(@Nullable  QuerySnapshot value, @Nullable  FirebaseFirestoreException error) {
                                    for (DocumentSnapshot snapshot1:value.getDocuments()){
                                        Map<String,Object> data=snapshot1.getData();

                                        String proje = (String) data.get("proje");

                                        if (proje == null){

                                        }else {
                                            //System.out.println(proje);
                                            listProje.add(proje);
                                            total++;

                                            if (listProje2.contains(proje)){

                                            }else {
                                                listProje2.add(proje);
                                            }


                                        }

                                    }

                                    lastFunc(questionId);

                                }
                            });
                        }else {

                            String questionId = snapshot.getId();
                            String question = (String) data.get("question");
                            System.out.println(question);
                            int bir = snapshot.getLong("bir").intValue();
                            int iki = snapshot.getLong("iki").intValue();
                            int uc = snapshot.getLong("uc").intValue();
                            int dort = snapshot.getLong("dort").intValue();
                            int bes = snapshot.getLong("bes").intValue();
                            int total = bir+iki+uc+dort+bes;
                            int average0 = (1*bir)+(2*iki)+(3*uc)+(4*dort)+(5*bes);
                            int average;

                            if (total==0 & average0==0){
                                average = 0;
                            }else {
                                average = average0/total;
                            }

                            //int average =1;



                            Post5 post5 = new Post5(questionId,question,option,bir,iki,uc,dort,bes,total,average);

                            list.add(post5);

                            setData();
                        }
                    }

                    /*
                    for(DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> data = snapshot.getData();
                        option = (String) data.get("option");
                        System.out.println(option);

                        if (option.equals("Klasik")){
                            String questionId = snapshot.getId();
                            String question = (String) data.get("question");
                            System.out.println(questionId);

                            firebaseFirestore.collection("Answers").document(questionId).collection(questionId).orderBy("puan", Query.Direction.DESCENDING).limit(5).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable  FirebaseFirestoreException error) {
                                    int a = 0;
                                    if (value != null){
                                        for (DocumentSnapshot snapshot1: value.getDocuments()){
                                            Map<String,Object> data1 = snapshot1.getData();
                                            String key = snapshot1.getId();
                                            System.out.println(key);
                                            int point = snapshot1.getLong("puan").intValue();
                                            System.out.println(point);
                                            a++;

                                            PostClassic postClassic = new PostClassic(question,key,point,a);
                                            list4.add(postClassic);
                                        }

                                        setData4();
                                    }

                                }
                            });
                        }else {

                            String questionId = snapshot.getId();
                            String question = (String) data.get("question");
                            System.out.println(question);
                            int bir = snapshot.getLong("bir").intValue();
                            int iki = snapshot.getLong("iki").intValue();
                            int uc = snapshot.getLong("uc").intValue();
                            int dort = snapshot.getLong("dort").intValue();
                            int bes = snapshot.getLong("bes").intValue();
                            int total = bir+iki+uc+dort+bes;
                            int average0 = (1*bir)+(2*iki)+(3*uc)+(4*dort)+(5*bes);
                            int average;

                            if (total==0 & average0==0){
                                average = 0;
                            }else {
                                average = average0/total;
                            }

                            //int average =1;



                            Post5 post5 = new Post5(questionId,question,option,bir,iki,uc,dort,bes,total,average);

                            list.add(post5);
                        }


                    }*/

                }

                /*
                if (option.equals("Klasik")){
                    setData4();

                }else {
                    setData();
                }*/




            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void lastFunc(String qId){
        postArrayList7.clear();
        ArrayList<String>denemeList = new ArrayList<>();
        ArrayList<Integer> newList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        for (int i = 0; i <listProje2.size(); i++){
            int deger = Collections.frequency(listProje,listProje2.get(i));
            newList.add(deger);
            map.put(listProje2.get(i),deger);


            //System.out.println(listProje2.get(i)+"/"+deger);
        }
        newList.sort(Comparator.reverseOrder());
        int a = 1;
        System.out.println(newList);
        for (int b =0; b<newList.size(); b++){
            System.out.println(newList.get(b));
            for (int c = 0; c<listProje2.size();c++){
                if (denemeList.contains(listProje2.get(c))){

                }else {
                    if (map.get(listProje2.get(c)).equals(newList.get(b))){
                        denemeList.add(listProje2.get(c));
                        System.out.println(listProje2.get(c)+"///"+newList.get(b));
                        Post7 post7 = new Post7(listProje2.get(c),newList.get(b),a);
                        postArrayList7.add(post7);
                        a++;

                    }
                }

            }
        }

        setData4();




    }



    public void setData(){
        System.out.println(list.size());
        if (list.size()>0){

            binding.question.setText("Soru :"+list.get(0).question);
            binding.total.setText("Katılımcı Sayısı :"+list.get(0).total);
            binding.average.setText("Ortalama Puan :"+list.get(0).average);
            binding.option.setText("Seçenek : "+list.get(0).option);

            List<Object> colors= new ArrayList<>();
            colors.add(Color.BLUE);
            colors.add(Color.RED);
            colors.add(Color.BLACK);
            colors.add(Color.GREEN);
            colors.add(Color.YELLOW);

            String label = "1/2/3/4/5";

            visitors.add(new BarEntry(0,list.get(0).bir));
            visitors.add(new BarEntry(1,list.get(0).iki));
            visitors.add(new BarEntry(2,list.get(0).uc));
            visitors.add(new BarEntry(3,list.get(0).dort));
            visitors.add(new BarEntry(4,list.get(0).bes));

            BarDataSet barDataSet = new BarDataSet(visitors,label);

            barDataSet.setValueTextSize(16f);
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);



            BarData barData =new BarData(barDataSet);
            barChart.setFitBars(true);

            barChart.setData(barData);


            barChart.animateY(2000);
            barChart.invalidate();
        }else {

            barChart.setVisibility(View.GONE);
            binding.average.setVisibility(View.GONE);
            binding.total.setVisibility(View.GONE);
            binding.option.setVisibility(View.GONE);
            binding.question.setText("Ankette herhangi bir soru bulunmamaktadır");
            System.out.println("we are here");
            binding.classicLinear.setVisibility(View.GONE);


        }

        if (list.size()>1){
            System.out.println("we are here");
            setData2();
        }else {
            barChart1.setVisibility(View.GONE);
            binding.question2.setVisibility(View.GONE);
            binding.average2.setVisibility(View.GONE);
            binding.total2.setVisibility(View.GONE);
            binding.option2.setVisibility(View.GONE);

            barChart2.setVisibility(View.GONE);
            binding.question3.setVisibility(View.GONE);
            binding.average3.setVisibility(View.GONE);
            binding.total3.setVisibility(View.GONE);
            binding.option3.setVisibility(View.GONE);
            binding.classicLinear.setVisibility(View.GONE);
        }

    }

    public void setData2(){

        if (list.size()>1){

            binding.question2.setText("Soru :"+list.get(1).question);
            binding.total2.setText("Katılımcı Sayısı :"+list.get(1).total);
            binding.average2.setText("Ortalama Puan :"+list.get(1).average);
            binding.option2.setText("Seçenek : "+list.get(1).option);

            List<Object> colors= new ArrayList<>();
            colors.add(Color.BLUE);
            colors.add(Color.RED);
            colors.add(Color.BLACK);
            colors.add(Color.GREEN);
            colors.add(Color.YELLOW);

            String label = "1/2/3/4/5";

            visitors1.add(new BarEntry(0,list.get(1).bir));
            visitors1.add(new BarEntry(1,list.get(1).iki));
            visitors1.add(new BarEntry(2,list.get(1).uc));
            visitors1.add(new BarEntry(3,list.get(1).dort));
            visitors1.add(new BarEntry(4,list.get(1).bes));

            BarDataSet barDataSet = new BarDataSet(visitors1,label);

            barDataSet.setValueTextSize(16f);
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);



            BarData barData =new BarData(barDataSet);
            barChart1.setFitBars(true);

            barChart1.setData(barData);


            barChart1.animateY(2000);
            barChart1.invalidate();
        }

        if (list.size()>2){
            setData3();
        }else {
            barChart2.setVisibility(View.GONE);
            binding.question3.setVisibility(View.GONE);
            binding.average3.setVisibility(View.GONE);
            binding.total3.setVisibility(View.GONE);
            binding.option3.setVisibility(View.GONE);
            binding.classicLinear.setVisibility(View.GONE);
        }


    }

    public void setData3() {
        if (list.size() > 2) {

            binding.question3.setText("Soru :" + list.get(2).question);
            binding.total3.setText("Katılımcı Sayısı :" + list.get(2).total);
            binding.average3.setText("Ortalama Puan :" + list.get(2).average);
            binding.option3.setText("Seçenek : "+list.get(2).option);

            List<Object> colors = new ArrayList<>();
            colors.add(Color.BLUE);
            colors.add(Color.RED);
            colors.add(Color.BLACK);
            colors.add(Color.GREEN);
            colors.add(Color.YELLOW);

            String label = "1/2/3/4/5";

            visitors2.add(new BarEntry(0, list.get(2).bir));
            visitors2.add(new BarEntry(1, list.get(2).iki));
            visitors2.add(new BarEntry(2, list.get(2).uc));
            visitors2.add(new BarEntry(3, list.get(2).dort));
            visitors2.add(new BarEntry(4, list.get(2).bes));

            BarDataSet barDataSet = new BarDataSet(visitors2, label);

            barDataSet.setValueTextSize(16f);
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);


            BarData barData = new BarData(barDataSet);
            barChart2.setFitBars(true);

            barChart2.setData(barData);


            barChart2.animateY(2000);
            barChart2.invalidate();
        }


    }

    public void setData4(){
        binding.linear1.setVisibility(View.GONE);
        binding.linear2.setVisibility(View.GONE);
        binding.linear3.setVisibility(View.GONE);

        if (listProje2.size()==0){
            binding.question4.setText("Ankette Soru Bulunmamaktadır");

        }else{
            binding.recyclerView7.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            adapter7 = new Adapter7(postArrayList7);
            binding.recyclerView7.setAdapter(adapter7);

            binding.totalApplicant.setText("Toplam Katılımcı: "+total);
        }


        /*
        //binding.total4.setText("Total Katılımcı :"+list4.get(0).);
        if (list4.size()==0){
            binding.question4.setText("Ankette Soru Bulnmamaktadır");
        }else if (list4.size()==1){
            binding.question4.setText(list4.get(0).question);
            binding.first.setText("1. Numara: "+list4.get(0).key+"   Puan:"+list4.get(0).point);

        }else if (list4.size()==2){
            binding.question4.setText(list4.get(0).question);
            binding.first.setText("1. Numara: "+list4.get(0).key+"   Puan:"+list4.get(0).point);
            binding.second.setText("2. Numara: "+list4.get(1).key+"   Puan:"+list4.get(1).point);
        }else if (list4.size()==3){
            binding.question4.setText(list4.get(0).question);
            binding.first.setText("1. Numara: "+list4.get(0).key+"   Puan:"+list4.get(0).point);
            binding.second.setText("2. Numara: "+list4.get(1).key+"   Puan:"+list4.get(1).point);
            binding.third.setText("3. Numara: "+list4.get(2).key+"   Puan:"+list4.get(2).point);
        }else if (list4.size()==4){
            binding.question4.setText(list4.get(0).question);
            binding.first.setText("1. Numara: "+list4.get(0).key+"   Puan:"+list4.get(0).point);
            binding.second.setText("2. Numara: "+list4.get(1).key+"   Puan:"+list4.get(1).point);
            binding.third.setText("3. Numara: "+list4.get(2).key+"   Puan:"+list4.get(2).point);
            binding.fourth.setText("4. Numara: "+list4.get(3).key+"   Puan:"+list4.get(3).point);
        }else if (list4.size()==5){
            binding.question4.setText(list4.get(0).question);
            binding.first.setText("1. Numara: "+list4.get(0).key+"   Puan:"+list4.get(0).point);
            binding.second.setText("2. Numara: "+list4.get(1).key+"   Puan:"+list4.get(1).point);
            binding.third.setText("3. Numara: "+list4.get(2).key+"   Puan:"+list4.get(2).point);
            binding.fourth.setText("4. Numara: "+list4.get(3).key+"   Puan:"+list4.get(3).point);
            binding.fifth.setText("5. Numara: "+list4.get(4).key+"   Puan:"+list4.get(4).point);
        }*/

    }
}