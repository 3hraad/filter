package com.example.filter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Gary on 15/04/14.
 */
public class GameActivity extends Activity {
    String cards[]= {
            "As","2s","3s","4s","5s","6s","7s","8s","9s","10s","Js","Qs","Ks",
            "Ad","2d","3d","4d","5d","6d","7d","8d","9d","10d","Jd","Qd","Kd",
            "Ah","2h","3h","4h","5h","6h","7h","8h","9h","10h","Jh","Qh","Kh",
            "Ac","2c","3c","4c","5c","6c","7c","8c","9c","10c","Jc","Qc","Kc"
            };
    int score=0;
    String shuffled[]=new String[52];
    ImageView chosenIV,card1,card2,card3,card4;
    TextView chosenTV,text1,text2,text3,text4;
    int ROUND1 = 1001;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        List<String> arr = Arrays.asList(cards);
        Collections.shuffle(arr);
        arr.toArray(cards);
        initImageViews();
        initTextViews();
        cardClicks();


    }

    private void initTextViews() {
        text1 = (TextView)findViewById(R.id.tvCard1);
        text2 = (TextView)findViewById(R.id.tvCard2);
        text3 = (TextView)findViewById(R.id.tvCard3);
        text4 = (TextView)findViewById(R.id.tvCard4);

        text1.setText(cards[0]);
        text2.setText(cards[1]);
        text3.setText(cards[2]);
        text4.setText(cards[3]);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ROUND1) {
            if (data.hasExtra("colour")){
                String colour= data.getExtras().getString("colour");
                showCard(chosenIV,chosenTV);

                if( colour.equals("red")){
                    if(chosenTV.getText().toString().contains("h")||chosenTV.getText().toString().contains("d")){
                        Toast.makeText(getApplicationContext(),"Red - Correct",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_LONG).show();
                        score++;
                    }

                } else
                if( colour.equals("black")){
                    if(chosenTV.getText().toString().contains("s")||chosenTV.getText().toString().contains("c")){
                        Toast.makeText(getApplicationContext(),"Black - Correct",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_LONG).show();
                        score++;
                    }
                }
                endRound1();

            }
        }
    }

    private void endRound1() {
        card1.setClickable(false);
        card2.setClickable(false);
        card3.setClickable(false);
        card4.setClickable(false);
    }

    private void showCard(ImageView imageView, TextView textView) {
        imageView.setImageResource(R.drawable.img_card_front);
        textView.setVisibility(View.VISIBLE);
    }

    private void cardClicks() {

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenIV=card1;
                chosenTV=text1;
                Intent i = new Intent(GameActivity.this,DialogRound1.class);
                startActivityForResult(i,ROUND1);

            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenIV=card2;
                chosenTV=text2;
                Intent i = new Intent(GameActivity.this,DialogRound1.class);
                startActivityForResult(i, ROUND1);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenIV=card3;
                chosenTV=text3;
                Intent i = new Intent(GameActivity.this,DialogRound1.class);
                startActivityForResult(i, ROUND1);
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenIV=card4;
                chosenTV=text4;
                Intent i = new Intent(GameActivity.this,DialogRound1.class);
                startActivityForResult(i, ROUND1);
            }
        });
    }


    private void initImageViews() {
        card1=(ImageView)findViewById(R.id.ivCard1);
        card2=(ImageView)findViewById(R.id.ivCard2);
        card3=(ImageView)findViewById(R.id.ivCard3);
        card4=(ImageView)findViewById(R.id.ivCard4);
    }
}