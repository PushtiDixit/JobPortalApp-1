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


public class MainActivity extends AppCompatActivity {
    //First we must create a variable for the activity
    private Button button;
    private Button button1;
    public EditText emailid,password;
    FirebaseAuth mFirebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button) findViewById(R.id.buttonformain1);
        button1=(Button) findViewById(R.id.buttontologin);
        //Get the instance for firebase auth
        mFirebaseAuth=FirebaseAuth.getInstance();
        emailid=findViewById(R.id.textView4);
        password=findViewById(R.id.editText5);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity.this,HaveAnAccount.class);
                startActivity(intent1);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          String email = emailid.getText().toString();
                                          String pwd = password.getText().toString();
                                          if (email.isEmpty()) {
                                              emailid.setError("Please,Enter your Email ID");
                                              emailid.requestFocus();//Focusiing on the same box

                                          } else if (pwd.isEmpty()) {
                                              password.setError("Please,Enter your password");
                                              password.requestFocus();//Focusiing on the same box

                                          } else if (email.isEmpty() && pwd.isEmpty()) {
                                              //A small message displayed on the screen
                                              Toast.makeText(MainActivity.this, "Both fields are empty", Toast.LENGTH_SHORT).show();
                                          } else if (!(email.isEmpty() && pwd.isEmpty())) {
                                              mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                                  @Override
                                                  public void onComplete(@NonNull Task<AuthResult> task) {
                                                      if (!task.isSuccessful()) {
                                                          Toast.makeText(MainActivity.this, "Authentication Unsuccessful", Toast.LENGTH_SHORT).show();
                                                      } else {
                                                          Intent intent = new Intent(MainActivity.this, Main1.class);
                                                          startActivity(intent);
                                                      }
                                                  }
                                              });
                                          } else {
                                              Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                                          }


                                      }
                                  });
                //View is the fundamental UI class in android.View view argument is to know which view has been selected -Button or spinner or text.




}
}