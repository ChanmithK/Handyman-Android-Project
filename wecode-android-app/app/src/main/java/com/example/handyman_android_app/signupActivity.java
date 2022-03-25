package com.example.handyman_android_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handyman_android_app.ui.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class signupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    EditText editTextFirstName, editTextLastName, editTextEmail, editTextPhone, editTextPassword,
            editTextRePassword;

    Spinner spinnerLocation, spinnerRole;

    private ProgressBar progressBar;
    private FirebaseFirestore firebaseFirestore;

    private FirebaseAuth mAuth;
    private Button createAccBtn;
    private TextView loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Spinner location_spinner = findViewById(R.id.r_spin_signup_location);
        spinnerLocation = location_spinner;
        location_spinner.setOnItemSelectedListener(this);

        Spinner Role_spinner = findViewById(R.id.r_spin_signup_role);
        spinnerRole = Role_spinner;
        Role_spinner.setOnItemSelectedListener(this);

        editTextFirstName   = (EditText) findViewById(R.id.r_et_signup_firstName);
        editTextLastName    = (EditText) findViewById(R.id.r_et_signup_lastName);
        editTextEmail       = (EditText) findViewById(R.id.r_et_signup_email2);
        editTextPhone       = (EditText) findViewById(R.id.r_et_signup_phone2);
        editTextPassword    = (EditText) findViewById(R.id.r_et_signup_pw);
        editTextRePassword  = (EditText) findViewById(R.id.r_et_signup_rePw);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        if (mAuth.getCurrentUser()!=null){
            mAuth.signOut();
        }

        createAccBtn=findViewById(R.id.r_btn_signup_createAcc);
        loginBtn=findViewById(R.id.r_tv_signup_alrdyHvAcc);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signupActivity.this, loginActivity.class));
            }
        });

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                Toast.makeText(signupActivity.this,"Register",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this,adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void registerUser(){
        final String FirstName    = editTextFirstName.getText().toString().trim();
        final String LastName     = editTextLastName.getText().toString().trim();
        final String Email        = editTextEmail.getText().toString().trim();
        final String Phone        = editTextPhone.getText().toString().trim();
        final String Location     = spinnerLocation.getSelectedItem().toString().trim();
        String Password     = editTextPassword.getText().toString().trim();
        String RePassword   = editTextRePassword.getText().toString().trim();
        final String Role         = spinnerRole.getSelectedItem().toString().trim();

        if(FirstName.isEmpty()){
            editTextFirstName.setError("First name require");
            editTextFirstName.requestFocus();
            return;
        }

        if(LastName.isEmpty()){
            editTextLastName.setError("Last name require");
            editTextLastName.requestFocus();
            return;
        }

        if(Email.isEmpty()){
            editTextEmail.setError("Email require");
            editTextEmail.requestFocus();
            return;
        }

        if(Phone.isEmpty()){
            editTextPhone.setError("First name require");
            editTextPhone.requestFocus();
            return;
        }

        if(Password.isEmpty()){
            editTextPassword.setError("First name require");
            editTextPassword.requestFocus();
            return;
        }



        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(
                                    FirstName,
                                    LastName,
                                    Email,
                                    Phone,
                                    Location,
                                    Role
                            );
                            updateDatabase(user);

                        }
                        else{
                            Toast.makeText(signupActivity.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void updateDatabase(User user){

        if (FirebaseAuth.getInstance().getCurrentUser()!=null){

            String userId=FirebaseAuth.getInstance().getCurrentUser().getUid();

            DocumentReference documentReference=FirebaseFirestore.getInstance().collection("Users").document(userId);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("FirstName",user.getFirstName());
            hashMap.put("LastName",user.getLastName());
            hashMap.put("Email",user.getEmail());
            hashMap.put("Phone",user.getPhone());
            hashMap.put("Location",user.getLocation());
            hashMap.put("Role",user.getRole());
            hashMap.put("UserID",userId);

            documentReference.set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(signupActivity.this,("Registered Successfully!"), Toast.LENGTH_SHORT).show();
//                        startActivity();
//                        finish();
                    }else{
                        Toast.makeText(signupActivity.this,("Register Failed!"), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{
            Toast.makeText(signupActivity.this,"Failed",Toast.LENGTH_SHORT).show();
        }


    }

}