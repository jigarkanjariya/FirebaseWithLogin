package com.example.devanshi.firebasewithlogin;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devanshi.firebasewithlogin.model.UserDetails;
import com.example.devanshi.firebasewithlogin.util.Mypref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.devanshi.firebasewithlogin.util.Mypref.Email;
import static com.example.devanshi.firebasewithlogin.util.Mypref.Image;
import static com.example.devanshi.firebasewithlogin.util.Mypref.Name;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, mname, mphone;
    private Button btnSignIn, btnSignUp, btnResetPassword, cancel;
    private ProgressBar progressBar;
    private ImageView image;
    private String picpath;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "SignupActivity";
    private View view;
    private TextView mtvname, memail, mpassword, mtphone;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        auth = FirebaseAuth.getInstance();
        image = (ImageView) findViewById(R.id.image);
        mname = (EditText) findViewById(R.id.name);
        mphone = (EditText) findViewById(R.id.phone);
        cancel = (Button) findViewById(R.id.btn1);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);


        requestAccountPermission();


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                final String name = mname.getText().toString().trim();
                Intent i = (new Intent(SignupActivity.this, Resetpassword.class));
                i.putExtra("name", name);
                i.putExtra("email", email);
                i.putExtra("image", picpath);
                startActivity(i);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                final String name = mname.getText().toString().trim();
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("image", picpath);
                startActivity(intent);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagepicker();
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                final String name = mname.getText().toString().trim();

                validation();
                progressBar.setVisibility(View.VISIBLE);


                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent i = new Intent(SignupActivity.this, SigninActivity.class);
                                    i.putExtra("name", name);
                                    i.putExtra("email", email);
                                    i.putExtra("image", picpath);
                                    Mypref mypref = new Mypref();
                                    mypref.SharedPreferenceUtils(SignupActivity.this);
                                    mypref.setValue("Name", name);
                                    mypref.setValue("Email", email);
                                    mypref.setValue("Image", picpath);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    private void imagepicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_PICTURE);
    }

    private void requestAccountPermission() {

        if (ContextCompat.checkSelfPermission(SignupActivity.this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(SignupActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
                                     makerequest();
        }

    }

    private void makerequest(){
        ActivityCompat.requestPermissions(SignupActivity.this,
                new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {

                if (grantResults.length >0 )
                {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    {
                        Log.d(TAG, "onRequestPermissionsResult: deny " + requestCode);
                        Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                        // requestAccountPermission();
                    }
                    else
                    {

                    }
                }else
                    {
                        Log.d(TAG, "onRequestPermissionsResult: allow "+requestCode);
                 // requestAccountPermission();

                }
                return;
            }

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.d("HEY", "IMAGE path Filename:- " + picturePath);
                picpath = picturePath;

                Mypref mypref = new Mypref();
                mypref.Glide(picpath, SignupActivity.this, image);

                image.setImageBitmap(thumbnail);
            }
        }
    }

    private void validation() {

        if (!validateName()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
        if (!validatePhone()) {
            return;
        }
        if (!validatepassword()) {
            return;
        }

    }

    private boolean validatepassword() {
        TextInputLayout tPassword = findViewById(R.id.password_inputtext);
        if (inputPassword.getText().toString().trim().isEmpty() && inputPassword.getText().toString().length() < 6) {
            tPassword.setError("Enter Your Password");
            return false;
        } else {
            tPassword.setErrorEnabled(false);
        }
        return true;

    }

    private boolean validatePhone() {
        TextInputLayout tPhone = findViewById(R.id.phone_inputtext);
        String phone = mphone.getText().toString().trim();
        if (phone.isEmpty() && !isValidPhone(phone)) {
            tPhone.setError("Enter Your Phonenumber");
            return false;
        } else {
            tPhone.setErrorEnabled(false);
        }
        return true;
    }

    private boolean isValidPhone(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }

    private boolean validateEmail() {
        TextInputLayout tEmail = findViewById(R.id.email_inputtext);
        String email = inputEmail.getText().toString().trim();
        if (email.isEmpty() && !isValidEmail(email)) {

            tEmail.setError("Enter Your emailId");
            return false;
        } else {
            tEmail.setErrorEnabled(false);
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validateName() {
        TextInputLayout tName = findViewById(R.id.name_inputtext);
        if (mname.getText().toString().trim().isEmpty()) {

            tName.setError("Enter Your name");
            return false;
        } else {
            tName.setErrorEnabled(false);
        }
        return true;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        progressBar.setVisibility(View.GONE);
//    }
}

