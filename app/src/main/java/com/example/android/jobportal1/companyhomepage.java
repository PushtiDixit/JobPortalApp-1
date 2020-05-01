package com.example.android.jobportal1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class companyhomepage extends AppCompatActivity {
    Button logout, postit;
    EditText jobtitle, jobskills, elink;
    String companyname;

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference ref=db.collection("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyhomepage);
        db = FirebaseFirestore.getInstance();
        jobtitle = findViewById(R.id.jobpost);
        jobskills = findViewById(R.id.skillsrequ);
        elink = findViewById(R.id.jobapply);
        postit = findViewById(R.id.buttonforpst);
        logout = findViewById(R.id.buttontologout1);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent inttomain = new Intent(companyhomepage.this, MainActivity.class);
                startActivity(inttomain);
            }
        });
        postit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = jobtitle.getText().toString();
                String skills = jobskills.getText().toString();
                String cinfo = elink.getText().toString();

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Query q1 = ref.whereEqualTo("UID", uid);

                q1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(companyhomepage.this, "Found company info", Toast.LENGTH_LONG).show();
//This message is getting printed

                            for (QueryDocumentSnapshot doc : task.getResult()) {
                            companyname=doc.getString("Company name");
                            }
                            Toast.makeText(companyhomepage.this,companyname,Toast.LENGTH_LONG).show();

                        }
                    }
                });
                          //Toast.makeText(companyhomepage.this,companyname,Toast.LENGTH_LONG).show();


                if (!validateinputs(title, skills, cinfo)) {
                    Map<String, Object> cmap = new HashMap<>();
                    cmap.put("JobTitle", title);
                    cmap.put("Skills", skills);
                    cmap.put("Contact", cinfo);
                    cmap.put("Company name",companyname);
                    Log.i("Company name",companyname);

                    db.collection("Jobs").add(cmap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(companyhomepage.this, "job details posted", Toast.LENGTH_SHORT).show();
                            Intent i1 = new Intent(companyhomepage.this, companyhomepage.class);
                            startActivity(i1);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(companyhomepage.this, "error! not posted", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });

    }



    private boolean validateinputs(String t, String s, String c) {
        if (t.isEmpty()) {
            jobtitle.setError("Enter  job title ");
            jobtitle.requestFocus();
            return true;
        } else if (s.isEmpty()) {
            jobskills.setError("Enter the skills expected");
            jobskills.requestFocus();
            return true;
        } else if (c.isEmpty()) {
            elink.setError("Please enter contact details");
            elink.requestFocus();
            return true;
        }

        return false;

    }

    }


