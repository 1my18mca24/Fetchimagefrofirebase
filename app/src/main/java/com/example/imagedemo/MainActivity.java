package com.example.imagedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Widgets
    RecyclerView recyclerView;

    //firebase
    private DatabaseReference myRef;

    //Variables
    private ArrayList<Messages> messagesList;
    private RecyclerAdapter recyclerAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //Firebase
        myRef= FirebaseDatabase.getInstance().getReference();

        //ArrayList
        messagesList=new ArrayList<>();

        // Clear ArrayList
        clearAll();
        //Get Data Method
        GetDataFromFirebase();




    }

    private void GetDataFromFirebase() {

        Query query=myRef.child("Sneha");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAll();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Messages messages=new Messages();
                    messages.setImageUrl(snapshot1.child("imageUrl").getValue().toString());
                    messages.setName(snapshot1.child("name").getValue().toString());

                    messagesList.add(messages);
                }

                recyclerAdapter = new RecyclerAdapter(getApplicationContext(),messagesList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    private void clearAll(){
        if(messagesList!=null){
            messagesList.clear();

            if(recyclerAdapter !=null){
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        else{
            messagesList=new ArrayList<>();
        }
    }
}