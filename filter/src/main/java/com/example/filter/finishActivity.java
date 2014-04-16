package com.example.filter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Gary on 16/04/14.
 */
public class finishActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        TextView finalScore=(TextView)findViewById(R.id.tvFinalScore);
        Bundle b = getIntent().getExtras();
        int score = b.getInt("score",0);
        finalScore.append(String.valueOf(score));
        Button end=(Button)findViewById(R.id.bBackToMenu);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}