package com.example.android.uber;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.ubertest.com.*;

public class CustomerLoginActivity extends AppCompatActivity {

    private EditText nEmail, nPassword;
    private Button nLogin, nRegister;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!= null)
                {
                    Intent intent= new Intent(CustomerLoginActivity.this, MapActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };
        nEmail=(EditText) findViewById(R.id.Email);
        nPassword=(EditText) findViewById(R.id.Password);

        nLogin=(Button) findViewById(R.id.Login);
        nRegister=(Button) findViewById(R.id.Register);

        nRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final String Email = nEmail.getText().toString();
//                final String Password = nPassword.getText().toString();
//                mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(CustomerLoginActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(!task.isSuccessful())
//                        {
//                            Toast.makeText(CustomerLoginActivity.this,"Sign-up Error",Toast.LENGTH_SHORT).show();
//                        }
//                        else{
//                            String User_id= mAuth.getCurrentUser().getUid();
//                            DatabaseReference current_user_db= FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(User_id);
//                            current_user_db.setValue(true);
//
//                        }
//                    }
//                });
                Intent intent= new Intent(CustomerLoginActivity.this, CustomerRegisterActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });
        nLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = nEmail.getText().toString();
                final String Password = nPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(CustomerLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            Toast.makeText(CustomerLoginActivity.this,"Sign-in Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}
