package com.example.handyman_android_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handyman_android_app.Model.ResponseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.example.handyman_android_app.ui.gallery.GalleryFragment.arrayList;
import static com.example.handyman_android_app.ui.gallery.GalleryFragment.gigAdapter;
import static com.example.handyman_android_app.ui.gallery.GalleryFragment.recyclerView;

public class p_update_response  extends AppCompatActivity implements AdapterView.OnItemSelectedListener {




    private static final String KEY_TITLE= "title";
    private static final String KEY_CATEGORY= "category";
    private static final String KEY_DESCRIPTION= "description";
    private static final String KEY_LOCATION= "location";
    private static final String KEY_CUS_NAME= "customerName";
    private static final String KEY_ResID= "ResponseID";

    private EditText editTextTitle;
    private EditText editTextCusName;
    private EditText editTextDescription;
    private Spinner spinnerCategory;
    private Spinner spinnerLocation;
    private TextView ididid;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    String category,description,title, Location,customerName;
    public double responseID;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pupdate_response);

        Spinner resCategory = findViewById(R.id.p_spn_update_response_category);
        resCategory.setOnItemSelectedListener(this);

        Spinner resLocation = findViewById(R.id.p_spn_update_response_location);
        resLocation.setOnItemSelectedListener(this);

        editTextTitle = findViewById(R.id.p_et_update_response_title);
        editTextCusName = findViewById(R.id.p_tv_update_response_cusName);
        editTextDescription = findViewById(R.id.p_et_update_response_description);
        spinnerCategory = findViewById(R.id.p_spn_update_response_category);
        spinnerLocation = findViewById(R.id.p_spn_update_response_location);
        ididid=findViewById(R.id.p_tv_update_response_resID);

        Intent intent=getIntent();
        responseID=intent.getDoubleExtra("ResponseID",0);
        category=intent.getStringExtra("Category");
        Location=intent.getStringExtra("Location");
        description=intent.getStringExtra("Description");
        title=intent.getStringExtra("Title");
        customerName=intent.getStringExtra("CustomerName");
        position=intent.getIntExtra("Position",0);


        ididid.setText(String.valueOf(responseID));
        editTextCusName.setText(customerName);
        editTextDescription.setText(description);
        editTextTitle.setText(title);

    }

    public void updateResponse(View v){

        Intent intent=getIntent();
        double  responseID=intent.getDoubleExtra("ResponseID",0);
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String customerName = editTextCusName.getText().toString();
        String category = spinnerCategory.getSelectedItem().toString();
        String location = spinnerLocation.getSelectedItem().toString();

        String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference=FirebaseFirestore.getInstance().collection("Users")
                .document(userId).collection("Responses").document(String.valueOf(responseID));

        Map<String, Object> gig = new HashMap<>();
        gig.put(KEY_TITLE,title);
        gig.put(KEY_DESCRIPTION,description);
        gig.put(KEY_CATEGORY,category);
        gig.put(KEY_LOCATION,location);
        gig.put(KEY_CUS_NAME,customerName);
        gig.put(KEY_ResID,responseID);

        documentReference.update(gig).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(p_update_response.this,"Response Updated!",Toast.LENGTH_SHORT).show();
                    ResponseModel newModel=new ResponseModel(responseID,category,description,location,title,customerName);
                    if (arrayList!=null) {
                        arrayList.set(position, newModel);
                        if (recyclerView!=null){
                            recyclerView.post(new Runnable() {
                                @Override
                                public void run() {
                                    gigAdapter.notifyItemChanged(position);
                                }
                            });
                        }
                    }else{
                        Toast.makeText(p_update_response.this,"null!",Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });

    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this,adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}