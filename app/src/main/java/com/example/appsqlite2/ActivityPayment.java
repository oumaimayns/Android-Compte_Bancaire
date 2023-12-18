package com.example.appsqlite2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityPayment extends AppCompatActivity {
    Button btnprevious, btnpayment;

    TextView balanceText,usernameText;

    EditText btnAmount,NumAccount;
    Bundle bundle;
    int balance;
    DBHelper dbHelper;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        btnAmount = findViewById(R.id.etAmount);
        NumAccount=findViewById(R.id.acNumber);
        btnpayment=findViewById(R.id.btnPayment);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // Récupérez l'ID
        int id = bundle.getInt("id");
        dbHelper=new DBHelper(this);
        Cursor data= dbHelper.ViewDataById(String.valueOf(id));
        if(data != null && data.moveToFirst()) {
            balance = data.getInt(data.getColumnIndexOrThrow("balance"));
            String username=data.getString(data.getColumnIndexOrThrow("username"));
            balanceText = findViewById(R.id.balance);
            balanceText.setText(String.valueOf(balance));
            usernameText=findViewById(R.id.username);
            usernameText.setText(String.valueOf(username));
        }

        btnpayment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String amount = btnAmount.getText().toString();
                String Number=NumAccount.getText().toString();
                if(Integer.parseInt(amount) <= balance){
                    boolean response = dbHelper.payment(String.valueOf(Number),String.valueOf(id),amount);
                    if (response) {
                        Toast.makeText(ActivityPayment.this, "The transaction completed successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ActivityPayment.this, ActivityAccount.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(ActivityPayment.this, "Failed", Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(ActivityPayment.this, "The amount is too large for the account balance.", Toast.LENGTH_LONG).show();

            }
        });
        btnprevious=findViewById(R.id.btnPrevious);
        btnprevious.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent =new Intent(ActivityPayment.this, ActivityAccount.class);
                startActivity(intent);
            }
        });
    }
}
