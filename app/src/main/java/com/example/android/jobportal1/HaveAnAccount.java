package com.example.android.jobportal1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HaveAnAccount extends AppCompatActivity {
    private Button button;
    private Button button1;
    public EditText emailid, password;
   private  FirebaseAuth mFirebaseAuth;
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

       mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            //To check if user's email id and password exsists

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                if( user!=null)
                {
                 Toast.makeText(HaveAnAccount.this,"You have successfully Logged in",Toast.LENGTH_SHORT);
                 Intent i=new Intent(HaveAnAccount.this,Homepage.class);
                 startActivity(i);

                }
                else
                {
                    Toast.makeText(HaveAnAccount.this,"Please try again",Toast.LENGTH_SHORT);
                }
            }
        };
        mFirebaseAuth=FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailid.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    emailid.setError("Please,Enter your Email ID");   //This is working
                    emailid.requestFocus();//Focusiing on the same box

                } else if (pwd.isEmpty()) {
                    password.setError("Please,Enter your password");
                    password.requestFocus();//Focusiing on the same box    //This is working

                }
                else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(HaveAnAccount.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(HaveAnAccount.this, " LOGIN ERROR.Try again", Toast.LENGTH_SHORT);
                            }
                            else
                            {
                                Intent inttohome=new Intent(HaveAnAccount.this,Homepage.class);
                                startActivity(inttohome);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(HaveAnAccount.this, "ERROR", Toast.LENGTH_SHORT);
                }


            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intsignup=new Intent(HaveAnAccount.this,MainActivity.class);
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