package com.example.appsqlite2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityAccount extends AppCompatActivity {

    Button btnDebit,btnCredit,btnPayment,btnPrevious;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        btnDebit=findViewById(R.id.btnDebit);
        btnCredit=findViewById(R.id.btnCredit);
        btnPayment=findViewById(R.id.btnPayment);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // Récupérez l'ID
        int id = bundle.getInt("id");

//        // Définissez le texte du TextView
//        textid.setText(String.valueOf(id));
        btnPrevious=findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent =new Intent(ActivityAccount.this, ActivityAccount.class);
                startActivity(intent);
            }
        });
        btnDebit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent =new Intent(ActivityAccount.this, ActivityDebit.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        btnCredit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent =new Intent(ActivityAccount.this, ActivityCredit.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        btnPayment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent =new Intent(ActivityAccount.this, ActivityPayment.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


}
