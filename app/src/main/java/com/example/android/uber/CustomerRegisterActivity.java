package com.example.android.uber;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CustomerRegisterActivity extends AppCompatActivity {

    private EditText mName, mEmail, mPassword,mNumber,mCity;
    private Button nRegister;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);
      mAuth = FirebaseAuth.getInstance();

        mEmail=(EditText) findViewById(R.id.CustEmail);
        mPassword=(EditText) findViewById(R.id.CustPassword);
        mName=(EditText) findViewById(R.id.CustName);
        mNumber=(EditText) findViewById(R.id.CustNumber);
        mCity=(EditText)findViewById(R.id.CustCity);

        nRegister=(Button) findViewById(R.id.Register);

        nRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = mEmail.getText().toString();
                final String Password = mPassword.getText().toString();
                final String Name = mName.getText().toString();
                final String City = mCity.getText().toString();
                final String Number = mNumber.getText().toString();
                mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(CustomerRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            Toast.makeText(CustomerRegisterActivity.this,"Sign-up Error",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            String User_id= mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db= FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(User_id);
                            DatabaseReference cdb=FirebaseDatabase.getInstance().getReference().child("UserDetails").child("Name").child(Name);
                            cdb=FirebaseDatabase.getInstance().getReference().child("UserDetails").child("Email").child(Email);
                            cdb=FirebaseDatabase.getInstance().getReference().child("UserDetails").child("City").child(City);
                            cdb=FirebaseDatabase.getInstance().getReference().child("UserDetails").child("PhoneNo").child(Number);
                            current_user_db.setValue(true);
                            cdb.setValue(true);

                            final ProgressDialog progressDialog = new ProgressDialog(CustomerRegisterActivity.this);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage("Creating Account...");
                            progressDialog.show();
                        }
                    }
                });


            }
        });



    }
}
