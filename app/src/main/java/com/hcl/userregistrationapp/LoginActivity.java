package com.hcl.userregistrationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText eml,pass;
    TextView create_btns;
    Button log_btns;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eml = findViewById(R.id.email_login);
        pass = findViewById(R.id.password_login);
        create_btns = findViewById(R.id.create);

        log_btns = findViewById(R.id.login_button);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

        log_btns.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String user_email = eml.getText().toString().trim();
                String passw = pass.getText().toString().trim();

                //TextUtils is used to check the validations.
                if(TextUtils.isEmpty(user_email)){

                    eml.setError("Please enter your email id");
                    return;
                }

                if(TextUtils.isEmpty(passw)){
                    pass.setError("Please enter your password");
                    return;
                }

                if(pass.length() <6){
                    pass.setError("Password must be greater than 6 characters");
                    return;
                }

                //authenticating the user.
                mAuth.signInWithEmailAndPassword(user_email,passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));

                        }

                        else{

                            Toast.makeText(LoginActivity.this,"Error Occured:" + task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }

                    }
                });

            }
        });


        create_btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}