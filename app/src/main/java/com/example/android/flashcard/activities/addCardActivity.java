package com.example.android.flashcard.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.android.flashcard.R;

/*
addCardActivity is a class that can allow a user to edit an existing card,
 add information to a new card, and saves all card information.
 */
public class addCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        /*
        when cancel button is clicked, the activity will be dismissed
         */
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });

        //attach data collected from intent to variables:
        String editAnswer = getIntent().getStringExtra("stringKey1");
        String editQuestion = getIntent().getStringExtra("stringKey2");
        String editChoiceOne = getIntent().getStringExtra("stringKey3");
        String editChoiceTwo = getIntent().getStringExtra("stringKey4");

        //pass that data into the views for entering information of a card
        ((EditText)findViewById(R.id.enterQuestion)).setText(editQuestion);
        ((EditText)findViewById(R.id.enterAnswer)).setText(editAnswer);
        ((EditText)findViewById(R.id.choiceOne)).setText(editChoiceOne);
        ((EditText)findViewById(R.id.choiceTwo)).setText(editChoiceTwo);

        /*
        when save button is clicked, the data entered into the four fields will be saved into variables.
        next, that variable will be passed through the intent back to main activity.
         */
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String question = ((EditText) findViewById(R.id.enterQuestion)).getText().toString();
                String answer = ((EditText) findViewById(R.id.enterAnswer)).getText().toString();
                String optionOne = ((EditText) findViewById(R.id.choiceOne)).getText().toString();
                String optionTwo = ((EditText) findViewById(R.id.choiceTwo)).getText().toString();

                Intent data = new Intent();
                data.putExtra("string1", question);
                data.putExtra("string2", answer);
                data.putExtra("string3", optionOne);
                data.putExtra("string4", optionTwo);

                //validate input
                if (question.matches("") || answer.matches("")) {
                    Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.input_cannot_be_space), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                } else {
                    setResult(RESULT_OK, data);
                    //dismiss this activity, and send data off to mainactivity
                    finish();
                }

            }
        });

    }
}
