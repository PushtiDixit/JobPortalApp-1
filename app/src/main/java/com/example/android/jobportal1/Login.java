package com.example.android.jobportal1;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    private TextView theDate;
    private Button btnGoCalender;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        theDate=(TextView) findViewById(R.id.DOB);//r uniquely identifies each object in the view
        btnGoCalender=(Button) findViewById(R.id.date);
        Intent incomingintent=getIntent();//To get incoming data
        String date=incomingintent.getStringExtra("date");
        theDate.setText(date);
        btnGoCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Login.this,CalenderActivity.class);
                startActivityForResult(intent,0);

            }
        });
        Spinner mySpinner=(Spinner)findViewById(R.id.spinner2);

        //Container to hold the values
        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(Login.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.qualifications));
        // To make it a drop down list
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        //If we forget this line then the data in drop down will not be shown in the spinner
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
}
