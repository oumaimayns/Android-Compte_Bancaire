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

public class ActivityDebit extends AppCompatActivity {

    Button btnprevious, btndebit;

    TextView balanceText,usernameText;

    EditText btnAmount;
    Bundle bundle;
    int balance;
    DBHelper dbHelper;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debit);
        btnAmount = findViewById(R.id.etAmount);
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

        btndebit=findViewById(R.id.btnDebit);
        btndebit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String amount = btnAmount.getText().toString();
                if(Integer.parseInt(amount) <= balance){
                    boolean response = dbHelper.debit(String.valueOf(id),amount);
                    if (response) {
                        Toast.makeText(ActivityDebit.this, "The transaction completed successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ActivityDebit.this, ActivityAccount.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(ActivityDebit.this, "Failed", Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(ActivityDebit.this, "The amount is too large for the account balance.", Toast.LENGTH_LONG).show();

            }
        });
        btnprevious=findViewById(R.id.btnPrevious);
        btnprevious.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent =new Intent(ActivityDebit.this, ActivityAccount.class);
                startActivity(intent);
            }
        });
    }
}
