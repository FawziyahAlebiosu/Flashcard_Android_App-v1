package com.example.android.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //android studio wil complain if block of code is not inside oncreate method
        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when we click on the correct answer, we want background color to change to green!
                findViewById(R.id.flashcard_answer).setBackgroundColor(getResources().getColor(R.color.green));
            }

        });
        //now do the same for the other options
        findViewById(R.id.optionOne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when we click on the wrong answer, color changes to red, but right answer will turn green
                findViewById(R.id.flashcard_answer).setBackgroundColor(getResources().getColor(R.color.green));
                findViewById(R.id.optionOne).setBackgroundColor(getResources().getColor(R.color.red));
            }
        });
        findViewById(R.id.optionTwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when we click on the wrong answer, color changes to red, but right answer will turn green
                findViewById(R.id.flashcard_answer).setBackgroundColor(getResources().getColor(R.color.green));
                findViewById(R.id.optionTwo).setBackgroundColor(getResources().getColor(R.color.red));
            }
        });
        //when we click on the question card, we want question card to be invisible
        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
                //then make answer card visible
                findViewById(R.id.answer_side).setVisibility((View.VISIBLE));
                //then make the other two options invisible as well
                findViewById(R.id.optionTwo).setVisibility(View.INVISIBLE);
                findViewById(R.id.optionOne).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
            }
        });

        //when we click on the answer card, we want question card to be VISIBLE, AND all the other options to appear
        findViewById(R.id.answer_side).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                //then make answer card visible
                findViewById(R.id.answer_side).setVisibility((View.INVISIBLE));
                //then make the other two options invisible as well
                findViewById(R.id.optionTwo).setVisibility(View.VISIBLE);
                findViewById(R.id.optionOne).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
            }
        });


        String questionOne = getString(R.string.questionOne);
        String answerOne = getString(R.string.answerOne);


        findViewById(R.id.plusbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create intent to link add button to next activity
                Intent intent = new Intent(MainActivity.this, addCardActivity.class);
                //we need to specify when going to this activity, that we want something back(the question and answer)
                MainActivity.this.startActivityForResult(intent, 100);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //which it will be because we made it that in the method above
            String string1 = data.getExtras().getString("string1");
            //again it should match the key we defined in the other method
            String string2 = data.getExtras().getString("string2");
            //now send those answers to the text views
            ((TextView)findViewById(R.id.flashcard_question)).setText(string1);
            ((TextView)findViewById(R.id.flashcard_answer)).setText(string2);
        }

    }
}


