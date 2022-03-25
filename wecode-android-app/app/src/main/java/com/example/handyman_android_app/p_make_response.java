package com.example.handyman_android_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.handyman_android_app.ui.gallery.GalleryFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class p_make_response extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "CreateGig";
    public EditText cusNameTV;
    String customerName;

    private static final String KEY_TITLE= "title";
    private static final String KEY_CATEGORY= "category";
    private static final String KEY_DESCRIPTION= "description";
    private static final String KEY_LOCATION= "location";
    private static final String KEY_ResID= "ResponseID";
    private static final String KEY_CUS_NAME= "customerName";


    private Spinner category_spinner;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner location_spinner;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmake_response);

        Intent intent=getIntent();
        customerName=intent.getStringExtra("CustomerName");


        category_spinner = findViewById(R.id.p_spn_makeResponse_category);
        category_spinner.setOnItemSelectedListener(this);

        location_spinner = findViewById(R.id.p_et_makeResponse_location);
        location_spinner.setOnItemSelectedListener(this);

        editTextTitle = findViewById(R.id.p_et_makeResponse_title);
        editTextDescription = findViewById(R.id.p_et_makeResponse_description);

        cusNameTV=findViewById(R.id.p_et_makeResponse_cusName);
        cusNameTV.setText(customerName);



    }

    public double generateDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd.HHmmss");
        double id = Double.parseDouble(String.valueOf(simpleDateFormat.format(Calendar.getInstance().getTime())));

        return id;
    }

    public void saveNote(View v ){
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String customerName = cusNameTV.getText().toString();
        String category = category_spinner.getSelectedItem().toString();
        String location = location_spinner.getSelectedItem().toString();
        double ResponseID = generateDate();

        if(title.isEmpty()){
            editTextTitle.setError("Title require");
            editTextTitle.requestFocus();
            return;
        }

        if(description.isEmpty()){
            editTextDescription.setError("Description require");
            editTextDescription.requestFocus();
            return;
        } else {

            String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
            DocumentReference documentReference=FirebaseFirestore.getInstance().collection("Users")
                    .document(userId).collection("Responses").document(String.valueOf(ResponseID));

            Map<String, Object> response = new HashMap<>();
            response.put(KEY_ResID,ResponseID);
            response.put(KEY_TITLE,title);
            response.put(KEY_DESCRIPTION,description);
            response.put(KEY_CATEGORY,category);
            response.put(KEY_LOCATION,location);
            response.put(KEY_CUS_NAME,customerName);

            documentReference.set(response);
            Toast.makeText(p_make_response.this,"Successfully Responded",Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, GalleryFragment.class);
            startActivity(i);
            finish();

        }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this,adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}