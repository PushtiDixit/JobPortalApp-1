package com.example.android.jobportal1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Homepage extends AppCompatActivity {
    Button logout,btnsearch;
    EditText job;
    FirebaseAuth mFirebaseAuth;
    private TextView textViewData;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference ref=db.collection("Jobs");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        job=findViewById(R.id.jobsearch);
        btnsearch=findViewById(R.id.buttonforsearch);
        btnsearch.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             String titlej=job.getText().toString();
                                             if(titlej.isEmpty())
                                                 job.setError("Please enter the job title");
                                             else
                                             {
                                                 String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                 Query q1=ref.whereEqualTo("UID",uid);
                                                 q1.get().addOnCompleteListener()

                                                 Query query=ref.whereEqualTo("JobTitle",titlej);
                                                 query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                     @Override
                                                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                      if(task.isSuccessful())
                                                      {
                                                          for(QueryDocumentSnapshot document:task.getResult()){
                                                              String t=document.getString("JobTitle");
                                                              String s=document.getString("Skills");
                                                              String c=document.getString("Contact");
                                                              textViewData.setText("Title: " +t+"\n"+"Skills required: "+s+"\n"+"Contact: "+c+"\n");

                                                          }
                                                      }
                                                      else
                                                      {
                                                          Toast.makeText(Homepage.this,"Error in searching",Toast.LENGTH_SHORT).show();
                                                      }
                                                     }
                                                 });

                                             }
                                         }
                                     });


                logout = findViewById(R.id.buttontologout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent inttomain=new Intent(Homepage.this,MainActivity.class);
                startActivity(inttomain);
            }
        });
    }
}
