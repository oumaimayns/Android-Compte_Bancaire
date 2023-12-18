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

public class ActivityCredit extends AppCompatActivity {

    Button btnprevious, btnCredit;

    TextView balanceText,usernameText;

    EditText  btnAmount;
    Bundle bundle;

    DBHelper dbHelper;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit);
        btnAmount = findViewById(R.id.etAmount);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // Récupérez l'ID
        int id = bundle.getInt("id");
        dbHelper=new DBHelper(this);
        Cursor data= dbHelper.ViewDataById(String.valueOf(id));
        if(data != null && data.moveToFirst()) {
            int balance = data.getInt(data.getColumnIndexOrThrow("balance"));
            String username=data.getString(data.getColumnIndexOrThrow("username"));
            balanceText = findViewById(R.id.balance);
            balanceText.setText(String.valueOf(balance));
            usernameText=findViewById(R.id.username);
            usernameText.setText(String.valueOf(username));
        }

        btnCredit=findViewById(R.id.btnCredit);
        btnCredit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String amount = btnAmount.getText().toString();
                boolean response = dbHelper.credit(String.valueOf(id), amount);
                if (response) {
                    Toast.makeText(ActivityCredit.this, "The transaction completed successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ActivityCredit.this, ActivityAccount.class);
                    startActivity(intent);
                } else
                    Toast.makeText(ActivityCredit.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });
        btnprevious=findViewById(R.id.btnPrevious);
        btnprevious.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent =new Intent(ActivityCredit.this, ActivityAccount.class);
                startActivity(intent);
            }
        });


    }
}
