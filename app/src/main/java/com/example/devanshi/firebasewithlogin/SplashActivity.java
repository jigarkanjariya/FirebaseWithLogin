package com.example.devanshi.firebasewithlogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.devanshi.firebasewithlogin.util.Mypref;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.devanshi.firebasewithlogin.util.Mypref.FLAG;
import static com.example.devanshi.firebasewithlogin.util.Mypref.mypreference;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private ImageView mIVimg;
    private  static  int flag=0;
    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mIVimg = (ImageView)findViewById(R.id.imgLogo);
        final Mypref mypref=new Mypref();
        mypref.SharedPreferenceUtils(SplashActivity.this);
        mSharedPreferences = this.getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                //FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                //if(currentUser == null)
                if(mSharedPreferences.contains(FLAG))
                {

                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);


                }
                else
                {

                    Intent i = new Intent(SplashActivity.this,SigninActivity.class);
                    mypref.setValue(FLAG,"1");
                    startActivity(i);


                }

                finish();

            }
        }, SPLASH_TIME_OUT);
    }
    }

