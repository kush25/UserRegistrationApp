package com.hcl.userregistrationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText name,emailid,password,phone;
    Button registerbtns;
    TextView log_bt;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name= findViewById(R.id.name);
        emailid = findViewById(R.id.email_id);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        registerbtns = findViewById(R.id.register_button);
        log_bt = findViewById(R.id.log_btns);

        //to get current instance of the database.
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            finish();
        }


        registerbtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailid.getText().toString().trim();
                String pass = password.getText().toString().trim();

                //TextUtils is used to check the validations.
                if(TextUtils.isEmpty(email)){

                    emailid.setError("Please enter your email id");
                    return;
                }

                if(TextUtils.isEmpty(pass)){
                    password.setError("Please enter your password");
                    return;
                }

                if(pass.length() <6){
                    password.setError("Password must be greater than 6 characters");
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                        }

                        else{

                            Toast.makeText(RegisterActivity.this,"Error Occured:" + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });


        log_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }


}