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

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

public class HaveAnAccount extends AppCompatActivity {
    private Button button;
    private Button button1;
    public EditText emailid, password;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_have_an_account);

        //Get the instance for firebase auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailid = findViewById(R.id.textView4);
        password = findViewById(R.id.editText5);
        button = findViewById(R.id.buttonforhomepage);
        button1 = findViewById(R.id.Buttontosignup);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            //To check if user's email id and password exsists

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(HaveAnAccount.this, "You have successfully Logged in", Toast.LENGTH_SHORT);
                    Intent i = new Intent(HaveAnAccount.this, Homepage.class);
                    startActivity(i);

                } else {
                    Toast.makeText(HaveAnAccount.this, "Please try again", Toast.LENGTH_SHORT);
                }
            }
        };
        mFirebaseAuth = FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = emailid.getText().toString();
                final String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    emailid.setError("Please,Enter your Email ID");   //This is working
                    emailid.requestFocus();//Focusiing on the same box

                } else if (pwd.isEmpty()) {
                    password.setError("Please,Enter your password");
                    password.requestFocus();//Focusiing on the same box    //This is working

                } else if (!(email.isEmpty() && pwd.isEmpty())) {


                    final FirebaseFirestore rootref = FirebaseFirestore.getInstance();


                    final CollectionReference colRef = rootref.collection("Users");
                    Query query = colRef.whereEqualTo("email", email);
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(HaveAnAccount.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(HaveAnAccount.this, " LOGIN ERROR.Try again", Toast.LENGTH_SHORT).show();
                                        }//This if condition is working
                                        else if (task.isSuccessful()) {
                                            Toast.makeText(HaveAnAccount.this, "ActivitySuccess", Toast.LENGTH_LONG).show();//It is reaching the success part
                                            //The code is working till here
                                            String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                           Query nameQuery = colRef.whereEqualTo("UID", user);




                                            nameQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(HaveAnAccount.this, "Document success", Toast.LENGTH_LONG).show();//This also works

                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            String type = document.getString("type");
                                                            if (type.equals("employee")) {
                                                                Toast.makeText(HaveAnAccount.this, "Inside employee", Toast.LENGTH_LONG).show();

                                                                Intent emp = new Intent(HaveAnAccount.this, Homepage.class);
                                                                startActivity(emp);
                                                            } else if (type.equals("company"))
                                                                startActivity(new Intent(HaveAnAccount.this, companyhomepage.class));

                                                            else {//No problem here
                                                                Log.e(">>>>>>error", "document ! exists");
                                                                //Toast.makeText(HaveAnAccount.this,"Inside doc else error",Toast.LENGTH_LONG);
                                                            }
                                                        }
                                                       /* String user = FirebaseAuth.getInstance().getCurrentUser().toString();
                                                        Log.i("User", user);

                                                        Toast.makeText(HaveAnAccount.this, "Document get success", Toast.LENGTH_LONG).show();
                                                        //Works till here.Exists function not working
                                                        String doc = document.getString("type");
                                                        Log.i("TYPE", doc);*/

                                                        //This is not working and is giving error
                                                        /*if (document.exists() && document != null) {
                                                            Log.e(">>>>>>error", "document exists");
                                                            Toast.makeText(HaveAnAccount.this, "exists success", Toast.LENGTH_LONG).show();
                                                            String Type = document.getString("type");

                                                        } else {
                                                            Log.e(">>>>>>error", "documentsnap=NULL");
                                                            //Toast.makeText(HaveAnAccount.this, "Document error", Toast.LENGTH_LONG).show();
                                                        }
                                                    }*/
                                                    }
                                                    else //No problem here too

                                                    {
                                                        Toast.makeText(HaveAnAccount.this, "last else error!", Toast.LENGTH_LONG).show();
                                                    }

                                                }


                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });




        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intsignup = new Intent(HaveAnAccount.this, MainActivity.class);
                startActivity(intsignup);
            }
        });

    }



    public void onStart (int resultCode, Intent data) {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//To check if the user is checked in
        //You can add update UI later.
    }
}