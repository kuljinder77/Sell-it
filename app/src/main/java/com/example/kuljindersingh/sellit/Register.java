package com.example.kuljindersingh.sellit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class Register extends AppCompatActivity {
    private Button regiter , cancel;
    private EditText email , password , confirm_password;
    private TextView alert;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        regiter = (Button) findViewById(R.id.submit);
        cancel = (Button) findViewById(R.id.cancel);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        confirm_password = (EditText)findViewById(R.id.confirmpassword);
        alert = (TextView) findViewById(R.id.alert);
        progressDialog = new ProgressDialog(this);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_login();
            }
        });
        regiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register_user();
                progressDialog.dismiss();
            }
        });

    }

    private void intent_login() {
        Intent intent = new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
        finish();

    }

    private void register_user() {

        if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confirm_password.getText().toString().isEmpty())
        {
            alert.setText("Please fill out every field");

        }else
        if(password.getText().toString().equalsIgnoreCase(confirm_password.getText().toString()))
        {
            if(password.getText().toString().length() >= 6) {
                progressDialog.setMessage("Registering User !");
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                             progressDialog.dismiss();
                             confirm_notification();

                        } else {
                            Log.w("ki hoya", "signInWithemail password:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_LONG).show();
                            alert.setText("Please enter valid email address !");

                        }
                    }

                });
            }
            else
                alert.setText("Password should be at least 6 characters");

        }
        else
        {
            alert.setText("Password does not match !");
        }
    }
    // send an verification email and redirect to login
    private void confirm_notification()
    {
        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Please verify first to login", Toast.LENGTH_LONG).show();
                    intent_login();

                }
                else
                {
                    Log.w("ki hoya", "failure", task.getException());

                }

            }
        });
    }
}
