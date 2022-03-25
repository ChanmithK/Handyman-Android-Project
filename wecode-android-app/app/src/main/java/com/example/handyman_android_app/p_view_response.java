package com.example.handyman_android_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handyman_android_app.ui.gallery.GalleryFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.handyman_android_app.ui.gallery.GalleryFragment.recyclerView;
import static com.example.handyman_android_app.ui.gallery.GalleryFragment.arrayList;
import static com.example.handyman_android_app.ui.gallery.GalleryFragment.gigAdapter;


public class p_view_response  extends AppCompatActivity {

    public double responseID;
    String category,description,title, location,ResponseID,customerName;
    public static TextView titleTV,responseIdTV,categoryTV,descriptionTV,locationTV,cusNameTV;
    private Button update;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pview_response);

        Intent intent=getIntent();
        responseID=intent.getDoubleExtra("ResponseID",0);
        customerName=intent.getStringExtra("CustomerName");
        category=intent.getStringExtra("Category");
        title=intent.getStringExtra("Title");
        description=intent.getStringExtra("Description");
        location=intent.getStringExtra("Location");
        position=intent.getIntExtra("Position",0);

        ResponseID = Double.toString(responseID);

        responseIdTV=findViewById(R.id.p_tv1_viewResponse_responseId);
        cusNameTV=findViewById(R.id.p_tv1_viewResponse_cusName);
        categoryTV=findViewById(R.id.p_tv1_viewResponse_category);
        titleTV=findViewById(R.id.p_tv1_viewResponse_title);
        descriptionTV=findViewById(R.id.p_tv1_viewResponse_description);
        locationTV=findViewById(R.id.p_tv1_viewResponse_location);

        responseIdTV.setText(ResponseID);
        cusNameTV.setText(customerName);
        categoryTV.setText(category);
        titleTV.setText(title);
        descriptionTV.setText(description);
        locationTV.setText(location);

        update=findViewById(R.id.p_btn_viewResponse_updateBtn);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), r_updateGig.class));
                Intent intent=new Intent(getApplicationContext(), p_update_response.class);
                intent.putExtra("ResponseID",Double.parseDouble(ResponseID));
                intent.putExtra("Title",title);
                intent.putExtra("Category",category);
                intent.putExtra("Description",description);
                intent.putExtra("Location",location);
                intent.putExtra("CustomerName",customerName);
                intent.putExtra("Position",position);
                startActivity(intent);
                finish();
            }
        });
    }

//    public void deleteResponse(View v){
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DocumentReference responseRef = db.document("Users/"+userId+"/Responses/"+responseID);
//        responseRef.delete();
////        Intent i = new Intent(this, GalleryFragment.class);
////        startActivity(i);
//    }

    public void deleteResponse(View v){

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_menu_delete)
                .setTitle("Are you sure,You want to delete this response?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
                        DocumentReference gigref = db.document("Users/"+userId+"/Responses/"+responseID);
                        gigref.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    if (recyclerView!=null){

                                        arrayList.remove(position);
                                        recyclerView.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                gigAdapter.notifyItemRemoved(position);
                                                gigAdapter.notifyItemRangeChanged(position,arrayList.size());
                                            }
                                        });
                                        Toast.makeText(p_view_response.this,"Response Deleted",Toast.LENGTH_SHORT).show();

                                    }
                                    finish();
                                }
                            }
                        });
                    }
                })
//set negative button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        Toast.makeText(getApplicationContext(),"Nothing Happened",Toast.LENGTH_LONG).show();
                    }
                })
                .show();



    }
}