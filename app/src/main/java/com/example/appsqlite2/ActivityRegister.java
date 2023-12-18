package com.example.appsqlite2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityRegister extends AppCompatActivity {
EditText etUser,etPwd,etRepwd ;
Button btnRegister,btnLogin;
DBHelper dbHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUser=findViewById(R.id.etUser);
        etPwd=findViewById(R.id.etPwd);
        etRepwd=findViewById(R.id.etRepwd);
        btnRegister=findViewById(R.id.btnRegister);
        btnLogin=findViewById(R.id.btnLogin);
        dbHelper=new DBHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent =new Intent(ActivityRegister.this, ActivityLogin.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                String user,pwd,rePwd;
                user=etUser.getText().toString();
                pwd=etPwd.getText().toString();
                rePwd=etRepwd.getText().toString();
                if(user.equals("")|| pwd.equals("") || rePwd.equals("")){
                    Toast.makeText(ActivityRegister.this,"Please fill all the fields",Toast.LENGTH_LONG).show();
                }else {
                    if(pwd.equals(rePwd)){

                      boolean RegisteredSuccess= dbHelper.addAccount(user,pwd);

                        if(RegisteredSuccess ==true)
                            Toast.makeText(ActivityRegister.this,"user Registered Successfully",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ActivityRegister.this,"user Registered Failed",Toast.LENGTH_LONG).show();

                    }else
                        Toast.makeText(ActivityRegister.this,"Password do not match",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
