package com.example.handyman_android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class p_view_request  extends AppCompatActivity {

    public double responseID;
    String category,description,title, location,ResponseID,customerName;
    public static TextView titleTV,responseIdTV,categoryTV,descriptionTV,locationTV,cusNameTV;
    private Button makeResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pview_request);

        Intent intent=getIntent();
        responseID=intent.getDoubleExtra("ResponseID",0);
        customerName=intent.getStringExtra("CustomerName");
        category=intent.getStringExtra("Category");
        title=intent.getStringExtra("Title");
        description=intent.getStringExtra("Description");
        location=intent.getStringExtra("Location");



        ResponseID = Double.toString(responseID);

        responseIdTV=findViewById(R.id.p_tv1_viewRequest_id
        );
        cusNameTV=findViewById(R.id.p_tv1_viewRequest_name);
        categoryTV=findViewById(R.id.p_tv1_viewRequest_category);
        titleTV=findViewById(R.id.p_tv1_viewRequest_title);
        descriptionTV=findViewById(R.id.p_tv1_viewRequest_description);
        locationTV=findViewById(R.id.p_tv1_viewRequest_location);

        responseIdTV.setText(ResponseID);
        cusNameTV.setText(customerName);
        categoryTV.setText(category);
        titleTV.setText(title);
        descriptionTV.setText(description);
        locationTV.setText(location);

        makeResponse=findViewById(R.id.p_btn_viewRequest_btn);

        makeResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), r_updateGig.class));
                Intent intent=new Intent(getApplicationContext(), p_make_response.class);
                intent.putExtra("CustomerName",customerName);
                startActivity(intent);
                finish();
            }
        });
    }

}