package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView timerTextView;
    private Button startBtn, resetBtn;


    private Handler handler;
    private Runnable runnable;
    private long startTime = 0L, elapsedTime=0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    private void startTimer(){
       startTime = System.currentTimeMillis() - elapsedTime;
       runnable = new Runnable() {
           @Override
           public void run() {
               elapsedTime = System.currentTimeMillis() - startTime;
               updateTimerText();
               handler.postDelayed(this,10);
           }
       };
       handler.postDelayed(runnable,10);
       startBtn.setEnabled(false);
       resetBtn.setEnabled(true);
    }

//    private void stopTimer(){
//        handler.removeCallbacks(runnable);
//        startBtn.setEnabled(true);
//        resetBtn.setEnabled(true);
//    }

    private void resetTimer(){
       handler.removeCallbacks(runnable);
       elapsedTime = 0L;
       updateTimerText();
        startBtn.setEnabled(true);
        resetBtn.setEnabled(false);
    }

    private void updateTimerText(){

        int seconds = (int) (elapsedTime / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        int milliseconds = (int) (elapsedTime % 1000);

        String millisecondsStr = String.valueOf(milliseconds);
        millisecondsStr = millisecondsStr.substring(0, Math.min(millisecondsStr.length(), 2));

        String timeFormatted = String.format("%02d:%02d.%s",minutes, seconds, millisecondsStr );
        timerTextView.setText(timeFormatted);
    }

    private void init(){
        timerTextView = findViewById(R.id.timerTextView);
        startBtn = findViewById(R.id.startBtn);
        resetBtn = findViewById(R.id.resetBtn);
        handler = new Handler();
    }
}