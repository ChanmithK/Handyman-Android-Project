package com.example.handyman_android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class n_view_gig extends AppCompatActivity {

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n_activity_view_gig);

        button = (Button) findViewById(R.id.n_btn_view_gig_request_service);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openRequestServices();
            }
        });
    }
    private void openRequestServices() {
        Intent intent = new Intent(this, n_request_services.class);
        startActivity(intent);
    }
}