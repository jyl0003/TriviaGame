package com.example.triviagame;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    String answer;
    String choice1;
    String choice2;
    String choice3;
    String choice4;
    String question;
    TextView select;
    int i = 0;
    List<String> quizBank;
    int time = 10;
    int scoreCount= 0;
    int questionCount= 0;
    boolean game = true;
    boolean isCounterRunning;
    private static MediaPlayer mediaPlayer;
    boolean dothisFirst =true;
    CountDownTimer timer;
    boolean firstClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quizBank = new ArrayList<>();
        readFile();
        TextView option1 = findViewById(R.id.choice1);
        TextView option2 = findViewById(R.id.choice2);
        TextView option3 = findViewById(R.id.choice3);
        TextView option4 = findViewById(R.id.choice4);
        question = quizBank.get(i);
        option1.setText("Touch");
        option2.setText("here");
        option3.setText("to begin");
        option4.setText("Trivia!!");

        //i = i+6;
        final TextView clock = findViewById(R.id.clock);
        timer = new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long l) {
                time--;
                clock.setText(String.valueOf(l / 1000) + "s");

            }

            @Override
            public void onFinish() {
                time = 10;
                isCounterRunning = false;

            }
        };
    }

    public void Play(View view) {
        if (firstClick) {
            if (!isCounterRunning) {
                isCounterRunning = true;
                timer.start();
            } else {
                timer.cancel(); // cancel
                timer.start();  // then restart
            }
            TextView questions = findViewById(R.id.question);
            questions.setText(quizBank.get(0));
            TextView option1 = findViewById(R.id.choice1);
            TextView option2 = findViewById(R.id.choice2);
            TextView option3 = findViewById(R.id.choice3);
            TextView option4 = findViewById(R.id.choice4);
            question = quizBank.get(0);
            answer = quizBank.get(1);
            choice1 = quizBank.get(2);
            choice2 = quizBank.get(3);
            choice3 = quizBank.get(4);
            choice4 = quizBank.get(5);
            answer = quizBank.get(1);
            option1.setText(quizBank.get(2));
            option2.setText(quizBank.get(3));
            option3.setText(quizBank.get(4));
            option4.setText(quizBank.get(5));
            firstClick = false;
            i = i + 6;
        } else {
            if (!isCounterRunning) {
                isCounterRunning = true;
                timer.start();
            } else {
                timer.cancel(); // cancel
                timer.start();  // then restart
            }
            if (i < quizBank.size() - 5) {

                Log.i("answer", answer);
                Log.i("question", question);

                select = (TextView) view;
                String selectedChoice;
                selectedChoice = select.getTag().toString();
                if (selectedChoice.equals("A")) {
                    selectedChoice = choice1.substring(2);
                }
                if (selectedChoice.equals("B")) {
                    selectedChoice = choice2.substring(2);
                }
                if (selectedChoice.equals("C")) {
                    selectedChoice = choice3.substring(2);
                }
                if (selectedChoice.equals("D")) {
                    selectedChoice = choice4.substring(2);
                }
                Log.i("selectedCjoice", selectedChoice);
                Log.i("Answer", answer);
                if (answer.contains(selectedChoice)) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.correct);
                    mediaPlayer.start();

                    scoreCount++;
                    questionCount++;
                } else {
                    mediaPlayer = MediaPlayer.create(this, R.raw.wrong);
                    mediaPlayer.start();
                    questionCount++;
                }
                Log.i("selected answer", selectedChoice);
                TextView questions1 = findViewById(R.id.question);
                TextView option1 = findViewById(R.id.choice1);
                TextView option2 = findViewById(R.id.choice2);
                TextView option3 = findViewById(R.id.choice3);
                TextView option4 = findViewById(R.id.choice4);
                select = (TextView) view;
                // randomizeList(quizBank);
                question = quizBank.get(i);
                questions1.setText(question);
                Log.i("hi", quizBank.get(i));
                choice1 = quizBank.get(i + 2);
                choice2 = quizBank.get(i + 3);
                choice3 = quizBank.get(i + 4);
                choice4 = quizBank.get(i + 5);

                option1.setText(choice1);
                option2.setText(choice2);
                if (choice3.isEmpty() && choice4.isEmpty()) {
                    //Do Nothing
                    option3.setVisibility(View.INVISIBLE);
                    option4.setVisibility(View.INVISIBLE);
                } else {
                    option3.setVisibility(View.VISIBLE);
                    option4.setVisibility(View.VISIBLE);
                    option3.setText(choice3);
                    option4.setText(choice4);
                }
                answer = quizBank.get(i + 1);
                TextView score = findViewById(R.id.questions);
                score.setText(String.valueOf(scoreCount) + "/" + String.valueOf(questionCount));

                i = i + 6;
                // }


                // }
            }

        }
    }
    public void readFile() {
        try {
            InputStream is = getApplicationContext().getAssets().open("animalTrivia.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String strLine;
            while ((strLine = br.readLine()) != null) {
               // Log.i("strLine = ", strLine);
                if (strLine.isEmpty()) {
                    //DO NOTHING
                }
                    else if (strLine.contains("^") && (strLine.contains("False") || strLine.contains("True"))) {
                        quizBank.add(strLine);
                        quizBank.add("A True");
                        quizBank.add("B False");
                        quizBank.add("");
                        quizBank.add("");
                        br.readLine();
                        br.readLine();
                    }
                    else if (strLine.equals("^ Yes") || strLine.equals("^ No")){
                        quizBank.add(strLine);
                        quizBank.add("A Yes");
                        quizBank.add("B No");
                        quizBank.add("");
                        quizBank.add("");
                        //for (int j = 0; j < 3; j++) {
                        br.readLine();
                        br.readLine();

                       // }
                    }
                    else {
                        quizBank.add(strLine);
                    }

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void randomizeList(List<String> data) {
        Collections.shuffle(data);
    }

}
