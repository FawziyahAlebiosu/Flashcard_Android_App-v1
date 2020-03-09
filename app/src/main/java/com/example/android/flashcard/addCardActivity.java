package com.example.android.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        //onactivity result method will allow us to get the data we passed into addcard back


        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when this button is clicked, grab the input from the question
                String question = ((EditText) findViewById(R.id.enterQuestion)).getText().toString();
                //and grab input from the answer
                String answer = ((EditText) findViewById(R.id.enterAnswer)).getText().toString();

                //create intent to begin passing the data
                Intent data = new Intent();
                //now put a string into the data with the key titled string1
                data.putExtra("string1", question );
                //do the same again but with key called string 2
                data.putExtra("string2", answer);
                //set the result and bundle to send the data back
                setResult(RESULT_OK, data);
                //dismiss this activity, and send data off to mainactivity

                finish();
            }

            String answer = getIntent().getStringExtra("string1");
            String question = getIntent().getStringExtra("string2");
        });

    }
}
