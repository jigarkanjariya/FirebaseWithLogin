package com.example.devanshi.firebasewithlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.devanshi.firebasewithlogin.util.Mypref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.devanshi.firebasewithlogin.util.Mypref.Email;
import static com.example.devanshi.firebasewithlogin.util.Mypref.Name;

public class SigninActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private String Name,Email,Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        auth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_signin);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_setpassword);



        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this, SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                Intent i=(new Intent(SigninActivity.this, Resetpassword.class));
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SigninActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete( Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(SigninActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else
                                    {

                                        Intent intent = new Intent(SigninActivity.this, HomeActivity.class);

                                    Mypref mypref=new Mypref();
                                    mypref.SharedPreferenceUtils(SigninActivity.this);
                                    mypref.setValue(Email,email);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
