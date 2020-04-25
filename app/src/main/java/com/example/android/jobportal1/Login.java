package com.example.android.jobportal1;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {
    private EditText theDate,name,email,university;
    private Spinner ed;
    private Button btnGoCalender;
    private Button Nextbutton;
    //Create a firebase firestore reference
    private FirebaseFirestore db;
    private FirebaseUser firebaseref;
    private String Userid;

//To store our data on to the database we need to create a collection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();

        name = (EditText) findViewById(R.id.editText);
        email = (EditText) findViewById(R.id.editText2);
        university = (EditText) findViewById(R.id.textView5);
        ed = (Spinner) findViewById(R.id.spinner2);
        firebaseref =FirebaseAuth.getInstance().getCurrentUser();
        final String Userid= firebaseref.getUid();
        Nextbutton = (Button) findViewById(R.id.nextbutton);


        theDate = (EditText) findViewById(R.id.DOB);//r uniquely identifies each object in the view
        btnGoCalender = (Button) findViewById(R.id.date);
        Intent incomingintent = getIntent();//To get incoming data
        String date = incomingintent.getStringExtra("date");
        theDate.setText(date);
        btnGoCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, CalenderActivity.class);
                startActivityForResult(intent, 0);

            }
        });
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner2);

        //Container to hold the values
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Login.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.qualifications));
        // To make it a drop down list
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        //If we forget this line then the data in drop down will not be shown in the spinner


        Nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=name.getText().toString();
                String EmailId=email.getText().toString();
                String DateOfBirth=theDate.getText().toString();
                String University=university.getText().toString();
                String Education=ed.getSelectedItem().toString();
                String Type="employee";

                if(!validateinputs(Name,EmailId,DateOfBirth,University,Education)){
                      Map<String, Object> empmap=new HashMap<>();
                      empmap.put("Name",Name);
                       empmap.put("Email",EmailId);
                    empmap.put("DOB",DateOfBirth);
                    empmap.put("Uni",University);
                    empmap.put("Qualification",Education);
                    empmap.put("UID",Userid);
                    empmap.put("type",Type);

                        db.collection("employee").add(empmap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(Login.this,"Employee details stored",Toast.LENGTH_SHORT);
                            Intent i1=new Intent(Login.this,HaveAnAccount.class);
                            startActivity(i1);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT);
                        }

                    });

                }


            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode){
            case 0:
                if(resultCode==RESULT_OK){
                    String date= data.getStringExtra("date");
                    theDate.setText(date);
                }
        }

    }
    private boolean validateinputs(String n,String e,String dob,String uni,String edu)
    {
        if(n.isEmpty())
        {
           name.setError("Enter your name");
            name.requestFocus();
            return true;
        }
        else if(e.isEmpty())
        {
            email.setError("Enter your email");
            email.requestFocus();
            return true;
        }
        else if(dob.isEmpty())
        {
            theDate.setError("Please select your date of birth");
            theDate.requestFocus();
            return true;
        }
        else if(uni.isEmpty())
        {
            university.setError("Enter the name of your university");
            university.requestFocus();
            return true;
        }
return false;

    }






}
