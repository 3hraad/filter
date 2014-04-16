package com.example.filter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/**
 * Created by Gary on 16/04/14.
 */
public class HighLowDialog extends Activity {
    String passBack=null;
    String previousCard=null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_high_low);
        final RadioButton higher=(RadioButton)findViewById(R.id.rbHigh);
        final RadioButton lower=(RadioButton)findViewById(R.id.rbLow);
        TextView header= (TextView) findViewById(R.id.tvHighLowTitle);
        Button choose = (Button) findViewById(R.id.bHLChoose);
        Bundle b = getIntent().getExtras();
        previousCard=b.getString("cardPrevious","error");
        header.append(previousCard);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (higher.isChecked()){
                    passBack="high";
                }else if (lower.isChecked()){
                    passBack="low";
                }else{
                    Toast.makeText(getApplicationContext(), "Please choose higher or lower", Toast.LENGTH_LONG).show();
                }
                if (passBack!=null){
                    Intent data = new Intent();
                    data.putExtra("pick",passBack);
                    setResult(RESULT_OK,data);
                    finish();
                }
            }
        });

    }
}