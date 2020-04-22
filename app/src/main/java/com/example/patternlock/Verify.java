package com.example.patternlock;

import androidx.appcompat.app.AppCompatActivity;

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

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;
import com.andrognito.rxpatternlockview.RxPatternLockView;
import com.andrognito.rxpatternlockview.events.PatternLockCompleteEvent;
import com.andrognito.rxpatternlockview.events.PatternLockCompoundEvent;

import java.util.List;

import io.reactivex.functions.Consumer;

public class Verify extends AppCompatActivity {




    private TextView textView;
    private PatternLockView patternLockView;
    private Button confirm;
    String pattrn;

    final String MyPREFERENCES = "MyPrefs";
    String pass , skip;

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

            SharedPreferences pref = getSharedPreferences(MyPREFERENCES,
                    Context.MODE_PRIVATE);

            pass=  pref.getString("PASS", "");
            skip=  pref.getString("val", "");

            System.out.println("passval" +pass);


            if (PatternLockUtils.patternToString(patternLockView, pattern).equalsIgnoreCase(pass)) {
                patternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                Toast.makeText(Verify.this, "Welcome", Toast.LENGTH_LONG).show();

                Intent intent=new Intent(Verify.this ,Final.class);
                startActivity(intent);
                finish();


            } else {
                patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                Toast.makeText(Verify.this, "Incorrect password", Toast.LENGTH_LONG).show();
            }


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
        setContentView(R.layout.activity_verify);


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







    }
}
// Enter Pattern \n To Verify