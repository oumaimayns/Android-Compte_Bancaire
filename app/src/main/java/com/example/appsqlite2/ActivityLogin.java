package com.example.appsqlite2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityLogin extends AppCompatActivity {
    EditText etUser,etPwd ;
    Button btnLogin;
    DBHelper dbHelper;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        etUser=findViewById(R.id.etUser);
        etPwd=findViewById(R.id.etPwd);
        btnLogin=findViewById(R.id.btnLogin);
        dbHelper=new DBHelper(this);
        btnLogin.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view){
                String user,pwd,rePwd;
                user=etUser.getText().toString();
                pwd=etPwd.getText().toString();
                Integer id= dbHelper.CheckUser(user,pwd);
                bundle.putInt("id", id);
                if(id!=0) {
                    Intent intent = new Intent(ActivityLogin.this, ActivityAccount.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ActivityLogin.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
