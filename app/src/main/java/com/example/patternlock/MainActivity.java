package com.example.patternlock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {




    private TextView textView;
    private PatternLockView patternLockView;
    private Button confirm;
    String pattrn, skip;

    final String MyPREFERENCES = "MyPrefs" ;
    private SharedPreferences sharedpreferences;

    private PatternLockViewListener patternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            Log.d(getClass().getName(), "Pattern drawing started");

        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d(getClass().getName(), "Pattern progress: "+
                    PatternLockUtils.patternToString(patternLockView,progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {

            Log.d(getClass().getName(), "Pattern complete: " +
                    PatternLockUtils.patternToString(patternLockView, pattern));

            pattrn= PatternLockUtils.patternToString(patternLockView, pattern);




        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        confirm=findViewById(R.id.confirm);
        textView=findViewById(R.id.profile_name);
        patternLockView=findViewById(R.id.patter_lock_view);
        patternLockView.setDotCount(3);
        patternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(this,R.dimen.pattern_lock_dot_size));
        patternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_selected_size));
        patternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_path_width));
        patternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
        patternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
        patternLockView.setPathEndAnimationDuration(100);
        patternLockView.setCorrectStateColor(ResourceUtils.getColor(this, R.color.white));
        patternLockView.setInStealthMode(false);
        patternLockView.setTactileFeedbackEnabled(true);
        patternLockView.setInputEnabled(true);
        patternLockView.addPatternLockListener(patternLockViewListener);

        SharedPreferences pref = getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);

        skip = pref.getString("val", "");


        if (skip.equals("1")) {
            Intent intent = new Intent(MainActivity.this, Verify.class);
            startActivity(intent);
        }





        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (pattrn!=null) {

                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString("PASS", pattrn);
                    editor.putString("val", "1");

                    editor.commit();

                    Intent intent = new Intent(MainActivity.this, Verify.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();


                }
                else {
                    Toast.makeText(getApplicationContext(),"Enter pattern" ,Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
   // Enter Pattern \n To Verify