package com.example.android.jobportal1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    String comp;
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
        textViewData=findViewById(R.id.textView6);
        job=findViewById(R.id.jobsearch);
        btnsearch=findViewById(R.id.buttonforsearch);
        btnsearch.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             final String titlej=job.getText().toString();
                                             if(titlej.isEmpty())
                                                 job.setError("Please enter the job title");
                                             else
                                             {
                                                 Query query=ref.whereEqualTo("JobTitle",titlej);
                                                 query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                     @Override
                                                     public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                                         String data="";
                                                          Toast.makeText(Homepage.this,"Success!",Toast.LENGTH_LONG).show();//works till here
                                                          for(QueryDocumentSnapshot document:queryDocumentSnapshots){

                                                              String t=document.getString("JobTitle");
                                                              Toast.makeText(Homepage.this,t,Toast.LENGTH_LONG).show();
                                                              String s=document.getString("Skills");
                                                              String c=document.getString("Contact");
                                                              String cn=document.getString("Company name");
                                                               data+=cn +"\n"+"Title: " +t+"\n"+"Skills required: "+s+"\n"+"Contact: "+c+"\n";

                                                          }
                                                          textViewData.setText(data);



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
