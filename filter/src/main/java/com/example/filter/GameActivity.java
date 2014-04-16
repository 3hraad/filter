package com.example.filter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
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
    int score=0, currentRound=1;
    String shuffled[]=new String[52];
    ImageView chosenIV,card1,card2,card3,card4,card5,card6,card7,card8,card9,card10;
    TextView chosenTV,text1,text2,text3,text4,text5,text6,text7,text8,text9,text10;
    int ROUND1 = 1001,ROUND2 = 1002,ROUND3 = 1003,ROUND4 = 1004;
    TextView TVpickedCards[]=new TextView[4];
    ImageView IVpickedCards[]=new ImageView[4];
    TextView scoreView, highScoreView;
    Button restart;
    int nextCard=10;
    int prevHighScore;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        List<String> arr = Arrays.asList(cards);
        Collections.shuffle(arr);
        arr.toArray(cards);
        restart=(Button)findViewById(R.id.bBackToLv1);
        initImageViews();
        initTextViews();
        highScoreView = (TextView) findViewById(R.id.tvHighscore);
        prevHighScore=getHighScore();
        highScoreView.setText("High Score: "+String.valueOf(prevHighScore));
        startRound1();


    }

    private int getHighScore() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int score = preferences.getInt("score", 0);

        return score;
    }

    private void initTextViews() {
        text1 = (TextView)findViewById(R.id.tvCard1);
        text2 = (TextView)findViewById(R.id.tvCard2);
        text3 = (TextView)findViewById(R.id.tvCard3);
        text4 = (TextView)findViewById(R.id.tvCard4);
        text5 = (TextView)findViewById(R.id.tvCard5);
        text6 = (TextView)findViewById(R.id.tvCard6);
        text7 = (TextView)findViewById(R.id.tvCard7);
        text8 = (TextView)findViewById(R.id.tvCard8);
        text9 = (TextView)findViewById(R.id.tvCard9);
        text10 = (TextView)findViewById(R.id.tvCard10);

        text1.setText(cards[0]);
        text2.setText(cards[1]);
        text3.setText(cards[2]);
        text4.setText(cards[3]);
        text5.setText(cards[4]);
        text6.setText(cards[5]);
        text7.setText(cards[6]);
        text8.setText(cards[7]);
        text9.setText(cards[8]);
        text10.setText(cards[9]);

        if (text1.getText().toString().contains("d")||text1.getText().toString().contains("h")){
            text1.setTextColor(Color.RED);
        }
        if (text2.getText().toString().contains("d")||text2.getText().toString().contains("h")){
            text2.setTextColor(Color.RED);
        }
        if (text3.getText().toString().contains("d")||text3.getText().toString().contains("h")){
            text3.setTextColor(Color.RED);
        }
        if (text4.getText().toString().contains("d")||text4.getText().toString().contains("h")){
            text4.setTextColor(Color.RED);
        }
        if (text5.getText().toString().contains("d")||text5.getText().toString().contains("h")){
            text5.setTextColor(Color.RED);
        }
        if (text6.getText().toString().contains("d")||text6.getText().toString().contains("h")){
            text6.setTextColor(Color.RED);
        }
        if (text7.getText().toString().contains("d")||text7.getText().toString().contains("h")){
            text7.setTextColor(Color.RED);
        }
        if (text8.getText().toString().contains("d")||text8.getText().toString().contains("h")){
            text8.setTextColor(Color.RED);
        }
        if (text9.getText().toString().contains("d")||text9.getText().toString().contains("h")){
            text9.setTextColor(Color.RED);
        }
        if (text10.getText().toString().contains("d")||text10.getText().toString().contains("h")){
            text10.setTextColor(Color.RED);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ROUND1) {
            if (data.hasExtra("colour")){
                String colour= data.getExtras().getString("colour");
                showCard(chosenIV,chosenTV);

                if( colour.equals("red")){
                    if(chosenTV.getText().toString().contains("h")||chosenTV.getText().toString().contains("d")){
                        Toast.makeText(getApplicationContext(),"Correct",Toast.LENGTH_SHORT).show();
                        startRound2();
                    }else {
                        Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_SHORT).show();
                        score++;
                        updateScore();
                    }

                } else
                if( colour.equals("black")){
                    if(chosenTV.getText().toString().contains("s")||chosenTV.getText().toString().contains("c")){
                        Toast.makeText(getApplicationContext(),"Correct",Toast.LENGTH_SHORT).show();
                        startRound2();
                    }else {
                        Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_SHORT).show();
                        score++;
                        updateScore();

                    }
                }
                endRound1();

            }
        } else if (resultCode == RESULT_OK && requestCode == ROUND2) {
            if (data.hasExtra("pick")){
                currentRound=2;
                String pick= data.getExtras().getString("pick");
                showCard(chosenIV,chosenTV);

                int old =getCardValue(TVpickedCards[0]);
                int current= getCardValue(TVpickedCards[1]);

                if( pick.equals("high")){
                    if(current>old){
                        Toast.makeText(getApplicationContext(),"Correct",Toast.LENGTH_SHORT).show();
                        startRound3();
                    }else {
                        Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_SHORT).show();
                        score+=2;
                        updateScore();
                    }

                } else
                if( pick.equals("low")){
                    if(current<old){
                        Toast.makeText(getApplicationContext(),"Correct",Toast.LENGTH_SHORT).show();
                        startRound3();
                    }else {
                        Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_SHORT).show();
                        score+=2;
                        updateScore();

                    }
                }
                endRound2();

            }
        }  else if (resultCode == RESULT_OK && requestCode == ROUND3) {
            if (data.hasExtra("pick")){
                currentRound=3;
                String pick= data.getExtras().getString("pick");
                showCard(chosenIV,chosenTV);

                int old =getCardValue(TVpickedCards[1]);
                int current= getCardValue(TVpickedCards[2]);

                if( pick.equals("high")){
                    if(current>old){
                        Toast.makeText(getApplicationContext(),"Correct",Toast.LENGTH_SHORT).show();
                        startRound4();
                    }else {
                        Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_SHORT).show();
                        score+=3;
                        updateScore();
                    }

                } else
                if( pick.equals("low")){
                    if(current<old){
                        Toast.makeText(getApplicationContext(),"Correct",Toast.LENGTH_SHORT).show();
                        startRound4();
                    }else {
                        Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_SHORT).show();
                        score+=3;
                        updateScore();

                    }
                }
                endRound3();

            }
        }  else if (resultCode == RESULT_OK && requestCode == ROUND4) {
            if (data.hasExtra("pick")){
                currentRound=4;
                String pick= data.getExtras().getString("pick");
                showCard(chosenIV,chosenTV);

                int old =getCardValue(TVpickedCards[2]);
                int current= getCardValue(TVpickedCards[3]);

                if( pick.equals("high")){
                    if(current>old){
                        endOfGame();
                    }else {
                        Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_SHORT).show();
                        score+=4;
                        updateScore();
                    }

                } else
                if( pick.equals("low")){
                    if(current<old){
                        endOfGame();
                    }else {
                        Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_SHORT).show();
                        score+=4;
                        updateScore();

                    }
                }
                endRound4();

            }
        }
    }

    private void endOfGame() {
        restart.setText("Finished");
        restart.setVisibility(View.VISIBLE);
        restart.setEnabled(true);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (score>prevHighScore){
                    saveHighScore();

                }
                Intent i = new Intent(GameActivity.this,finishActivity.class);
                i.putExtra("score",score);
                startActivity(i);
                finish();
            }
        });
    }

    private void saveHighScore() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("score", score);
        editor.commit();
    }

    private int getCardValue(TextView card) {
        String origCard = card.getText().toString();
        int result=0;
        if (origCard.contains("A")){
            result=1;
        } else if (origCard.contains("J")){
            result=11;
        } else if (origCard.contains("Q")){
            result=12;
        } else if (origCard.contains("K")){
            result=13;
        }else{
        String noLetters= origCard.replaceAll("[^0-9]", "");
            result=Integer.parseInt(noLetters);
        }

        //Toast.makeText(getApplicationContext(),origCard+" to "+result,Toast.LENGTH_LONG).show();
        return result;
    }

    private void startRound1() {

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenIV=card1;
                chosenTV=text1;
                Intent i = new Intent(GameActivity.this,RedBlackDialog.class);
                startActivityForResult(i,ROUND1);

            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenIV=card2;
                chosenTV=text2;
                Intent i = new Intent(GameActivity.this,RedBlackDialog.class);
                startActivityForResult(i, ROUND1);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenIV=card3;
                chosenTV=text3;
                Intent i = new Intent(GameActivity.this,RedBlackDialog.class);
                startActivityForResult(i, ROUND1);
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenIV=card4;
                chosenTV=text4;
                Intent i = new Intent(GameActivity.this,RedBlackDialog.class);
                startActivityForResult(i, ROUND1);
            }
        });
    }

    private void startRound2() {
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenIV=card5;
                chosenTV=text5;

                Intent i = new Intent(GameActivity.this,HighLowDialog.class);
                i.putExtra("cardPrevious", TVpickedCards[0].getText().toString());
                startActivityForResult(i, ROUND2);

            }
        });

        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenIV=card6;
                chosenTV=text6;

                Intent i = new Intent(GameActivity.this,HighLowDialog.class);
                i.putExtra("cardPrevious", TVpickedCards[0].getText().toString());
                startActivityForResult(i, ROUND2);

            }
        });

        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenIV=card7;
                chosenTV=text7;

                Intent i = new Intent(GameActivity.this,HighLowDialog.class);
                i.putExtra("cardPrevious", TVpickedCards[0].getText().toString());
                startActivityForResult(i, ROUND2);

            }
        });

    }

    private void startRound3() {
        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenIV=card8;
                chosenTV=text8;

                Intent i = new Intent(GameActivity.this,HighLowDialog.class);
                i.putExtra("cardPrevious", TVpickedCards[1].getText().toString());
                startActivityForResult(i, ROUND3);

            }
        });

        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenIV=card9;
                chosenTV=text9;

                Intent i = new Intent(GameActivity.this,HighLowDialog.class);
                i.putExtra("cardPrevious", TVpickedCards[1].getText().toString());
                startActivityForResult(i, ROUND3);

            }
        });



    }

    private void startRound4() {
        card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenIV=card10;
                chosenTV=text10;

                Intent i = new Intent(GameActivity.this,HighLowDialog.class);
                i.putExtra("cardPrevious", TVpickedCards[2].getText().toString());
                startActivityForResult(i, ROUND4);

            }
        });


    }



    private void updateScore() {
        scoreView = (TextView) findViewById(R.id.tvScore);
        scoreView.setText("Score: "+ Integer.toString(score)); 
        restart.setVisibility(View.VISIBLE);
        restart.setEnabled(true);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToStart();
                restart.setVisibility(View.INVISIBLE);
                restart.setEnabled(true);
            }
        });
        if (score>prevHighScore){
            highScoreView.setText("High Score: "+String.valueOf(score));

        }

    }

    private void backToStart() {
       for (int x=0;x<currentRound;x++){
           TVpickedCards[x].setVisibility(View.INVISIBLE);
           TVpickedCards[x].setText(cards[nextCard]);
           if (TVpickedCards[x].getText().toString().contains("d")||TVpickedCards[x].getText().toString().contains("h")){
               TVpickedCards[x].setTextColor(Color.RED);
           } else{
               TVpickedCards[x].setTextColor(Color.BLACK);

           }
//               TVpickedCards[x].setText(cards[nextCard]);

           IVpickedCards[x].setImageResource(R.drawable.img_card_back);
           nextCard++;
           if(nextCard==52){
               nextCard=0;
           }


       }
        currentRound=1;
        startRound1();
    }

    private void endRound1() {
        card1.setClickable(false);
        card2.setClickable(false);
        card3.setClickable(false);
        card4.setClickable(false);
    }

    private void endRound2() {
        card5.setClickable(false);
        card6.setClickable(false);
        card7.setClickable(false);
    }

    private void endRound3() {
        card8.setClickable(false);
        card9.setClickable(false);
    }

    private void endRound4() {
        card10.setClickable(false);
    }

    private void showCard(ImageView imageView, TextView textView) {
        imageView.setImageResource(R.drawable.img_card_front_bean);
        textView.setVisibility(View.VISIBLE);
        IVpickedCards[currentRound-1]=imageView;
        TVpickedCards[currentRound-1]=textView;
        for (int x=0;x<currentRound-1;x++){

            IVpickedCards[x].setImageResource(R.drawable.img_card_front);


        }

    }

    private void initImageViews() {
        card1=(ImageView)findViewById(R.id.ivCard1);
        card2=(ImageView)findViewById(R.id.ivCard2);
        card3=(ImageView)findViewById(R.id.ivCard3);
        card4=(ImageView)findViewById(R.id.ivCard4);
        card5=(ImageView)findViewById(R.id.ivCard5);
        card6=(ImageView)findViewById(R.id.ivCard6);
        card7=(ImageView)findViewById(R.id.ivCard7);
        card8=(ImageView)findViewById(R.id.ivCard8);
        card9=(ImageView)findViewById(R.id.ivCard9);
        card10=(ImageView)findViewById(R.id.ivCard10);
    }
}