package com.example.android.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class addCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create intent to dismiss this activity
                finish();
            }
        });
        String editAnswer = getIntent().getStringExtra("stringKey1");
        String editQuestion = getIntent().getStringExtra("stringKey2");
        String editChoiceOne = getIntent().getStringExtra("stringKey3");
        String editChoiceTwo = getIntent().getStringExtra("stringKey4");

        ((EditText)findViewById(R.id.enterQuestion)).setText(editQuestion);
        ((EditText)findViewById(R.id.enterAnswer)).setText(editAnswer);
        ((EditText)findViewById(R.id.choiceOne)).setText(editChoiceOne);
        ((EditText)findViewById(R.id.choiceTwo)).setText(editChoiceTwo);
        //onactivity result method will allow us to get the data we passed into addcard back


        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when this button is clicked, grab the input from the question
                String question = ((EditText) findViewById(R.id.enterQuestion)).getText().toString();
                //and grab input from the answer
                String answer = ((EditText) findViewById(R.id.enterAnswer)).getText().toString();
                String optionOne = ((EditText) findViewById(R.id.choiceOne)).getText().toString();
                String optionTwo = ((EditText) findViewById(R.id.choiceTwo)).getText().toString();
                //create intent to begin passing the data
                Intent data = new Intent();
                //now put a string into the data with the key titled string1
                data.putExtra("string1", question);
                //do the same again but with key called string 2
                data.putExtra("string2", answer);
                data.putExtra("string3", optionOne);
                data.putExtra("string4", optionTwo);
                //set the result and bundle to send the data back
                if (question.matches("") || answer.matches("")) {
                    //Create toast by initializing
                    Toast toast = Toast.makeText(getApplicationContext(), "question and answer cannot be blank", Toast.LENGTH_SHORT);

                    //Configure the position of a toast
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                } else {
                    //grab input from the answer

                    setResult(RESULT_OK, data);
                    //dismiss this activity, and send data off to mainactivity

                    finish();
                }

            }
        });

    }
}
