package com.example.handyman_android_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class loginActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    EditText editTextEmail,  editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextPassword    = (EditText) findViewById(R.id.r_et_login_pw);
        editTextEmail       = (EditText) findViewById(R.id.r_et_login_email2);


        findViewById(R.id.r_tv_login_reg).setOnClickListener(this);

        findViewById(R.id.r_btn_login_login).setOnClickListener(this);


    }

    private void userLogin(){
        final String Email        = editTextEmail.getText().toString().trim();
        String Password     = editTextPassword.getText().toString().trim();


        if(Email.isEmpty()){
            editTextEmail.setError("Email require");
            editTextEmail.requestFocus();
            return;
        }

        if(Password.isEmpty()){
            editTextPassword.setError("First name require");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    checkRole();
                    Intent intent = new Intent(loginActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkRole() {

        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        String userID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference=firebaseFirestore.collection("Users").document(userID);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {

            }
        });

        Intent intent = new Intent(loginActivity.this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.r_tv_login_reg:
                startActivity(new Intent(this, signupActivity.class));
                break;
            case R.id.r_btn_login_login:
                userLogin();
                break;
        }
    }
}