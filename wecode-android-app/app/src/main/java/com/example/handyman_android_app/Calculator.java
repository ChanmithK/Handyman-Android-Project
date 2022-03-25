package com.example.handyman_android_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Calculator extends AppCompatActivity {

    //  private static final String TAG = "MainActivity";
    private static final String KEY_TITLE = "title";
    private static final String KEY_INCOME = "income";
    private static final String KEY_EXPENSES = "expenses";
    private static final String KEY_PROFIT = "profit";
    private static final String KEY_PROFIT_PERCENTAGE = "profitPercentage";

    private EditText editTextTitle;
    private EditText editTextIncome;
    private EditText editTextExpenses;
    private TextView textViewP;
    private TextView textViewPp;

    String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();

    Random r = new Random();
    int randomNumber = r.nextInt(10000);

    DocumentReference noteRef=FirebaseFirestore.getInstance().collection("Calculator").document(String.valueOf(randomNumber));
    //  .document(userId).collection("RequestedServices").document("Calculator");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        editTextTitle = findViewById(R.id.calculator_et_title);
        editTextIncome = findViewById(R.id.calculator_et_income);
        editTextExpenses = findViewById(R.id.calculator_et_expenses);
        textViewP = findViewById(R.id.calculator_et_profit);
        textViewPp = findViewById(R.id.calculator_et_profit_percentage);
    }

    public void loadNote(View v) {
        String title = editTextTitle.getText().toString();
        String income = editTextIncome.getText().toString();
        String expenses = editTextExpenses.getText().toString();

        double incomeInt = Double.parseDouble(income);
        double expenseInt = Double.parseDouble(expenses);
        double profitInt =  incomeInt - expenseInt;
        String s1=Double.toString(profitInt);
        textViewP.setText(s1);

        int profitPerInt =  (int)((profitInt / incomeInt)*100);
        String s2=Integer.toString( profitPerInt);
        String s3 = "%";
        textViewPp.setText(s2+""+s3);

        String profit = textViewP.getText().toString();
        String profitPer = textViewPp.getText().toString();

        Map<String, Object> note = new HashMap<>();
        note.put(KEY_TITLE, title);
        note.put(KEY_INCOME, income);
        note.put(KEY_EXPENSES, expenses);
        note.put(KEY_PROFIT, profit);
        note.put(KEY_PROFIT_PERCENTAGE, profitPer);

        noteRef.set(note);
    }
}

