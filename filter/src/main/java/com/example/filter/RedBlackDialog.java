package com.example.filter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/**
 * Created by Gary on 16/04/14.
 */
public class RedBlackDialog extends Activity {
    String colour=null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_red_black);
        final RadioGroup choice =(RadioGroup)findViewById(R.id.rgRound1);
        final RadioButton red=(RadioButton)findViewById(R.id.rbRed);
        final RadioButton black=(RadioButton)findViewById(R.id.rbBlack);
        Button choose = (Button) findViewById(R.id.bRound1Choose);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (red.isChecked()){
                    colour="red";
                }else if (black.isChecked()){
                    colour="black";
                }else{
                    Toast.makeText(getApplicationContext(),"Please select a colour", Toast.LENGTH_LONG).show();
                }
                if (colour!=null){
                    Intent data = new Intent();
                    data.putExtra("colour",colour);
                    setResult(RESULT_OK,data);
                    finish();
                }
            }
        });

    }
}