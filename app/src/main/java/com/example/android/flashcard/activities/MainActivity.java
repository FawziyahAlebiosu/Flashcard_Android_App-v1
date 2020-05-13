package com.example.android.flashcard.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.android.flashcard.miscallenousclasses.Flashcard;
import com.example.android.flashcard.interfaces.FlashcardDatabase;
import com.example.android.flashcard.R;
import com.google.android.material.snackbar.Snackbar;


import java.util.List;

public class MainActivity extends AppCompatActivity {
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;

    int currentCardDisplayedIndex = 0;
    //variable to keep track for editing purpose
    Flashcard cardToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        final Animation leftOutAnim = AnimationUtils.loadAnimation(this, R.anim.enter_right);
        final Animation rightInAnim = AnimationUtils.loadAnimation(this, R.anim.exit_left);

        if (allFlashcards != null && allFlashcards.size() > 0) {
            //if our deck of saved cards has at least one card, then present/display the first saved card in the deck

            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
            ((TextView) findViewById(R.id.optionOne)).setText(allFlashcards.get(0).getWrongAnswer1());
            ((TextView) findViewById(R.id.optionTwo)).setText(allFlashcards.get(0).getWrongAnswer2());
            ((TextView) findViewById(R.id.answer_side)).setText(allFlashcards.get(0).getAnswer());
        }

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
                View answerSideView = findViewById(R.id.flashcard_answer);
                //get center for clipping circle
                int cx = answerSideView.getWidth()/2;
                int cy = answerSideView.getHeight() / 2;
                //get final radius for clipping animation
                float finalRadius = (float) Math.hypot(cx, cy);
                //create animator for this view
                Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);

                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);

        //then make answer card visible
                findViewById(R.id.answer_side).setVisibility((View.VISIBLE));
        //then make the other two options invisible as well
                findViewById(R.id.optionTwo).setVisibility(View.INVISIBLE);
                findViewById(R.id.optionOne).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
        //then make next button invisible as well
                findViewById(R.id.nextbtn).setVisibility(View.INVISIBLE);
                anim.setDuration(5000);
                anim.start();
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
                findViewById(R.id.nextbtn).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.plusbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(MainActivity.this, addCardActivity.class);


                //create intent to link add button to next activity
                Intent intent = new Intent(MainActivity.this, addCardActivity.class);
                //we need to specify when going to this activity, that we want something back(the question and answer)

                MainActivity.this.startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.enter_right, R.anim.exit_left);


            }
        });

        //create on click listener for when edit button is clicked, grab the currently displayed question and answer strings,
        //then pass them to addcard activity to be populated inside of editText
        findViewById(R.id.editbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //button is clicked, grab the question inside of textView
                Intent intent = new Intent(MainActivity.this, addCardActivity.class);

                //create intent to link edit button to add card activity

                TextView answer = findViewById(R.id.flashcard_answer);
                String editAnswer = answer.getText().toString();

                TextView question =  findViewById(R.id.flashcard_question);
                String editQuestion = question.getText().toString();

                TextView choice = findViewById(R.id.optionOne);
                String editChoice1 = choice.getText().toString();

                TextView choice2 = findViewById(R.id.optionTwo);
                String editChoice2 = choice2.getText().toString();
               intent.putExtra("stringKey1",editAnswer);
                intent.putExtra("stringKey2", editQuestion);
                intent.putExtra("stringKey3", editChoice1 );
                
                intent.putExtra("stringKey4",editChoice2);

                MainActivity.this.startActivityForResult(intent, 100);


            }
        });

        findViewById(R.id.nextbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;

                }


                // set the question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                ((TextView) findViewById(R.id.answer_side)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                ((TextView) findViewById(R.id.optionOne)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                ((TextView) findViewById(R.id.optionTwo)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                    //make green and red dissappear

                findViewById(R.id.flashcard_answer).setBackgroundColor(getResources().getColor(R.color.regular));
                findViewById(R.id.optionTwo).setBackgroundColor(getResources().getColor(R.color.regular));
                findViewById(R.id.optionOne).setBackgroundColor(getResources().getColor(R.color.regular));
                //listeners for animations
                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {


                    @Override
                    public void onAnimationStart(Animation animation) {
                        //this emthod is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //called when animation finishes


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        //no need to worry about this
                    }

                });
                findViewById(R.id.flashcard_question).startAnimation(leftOutAnim);
                findViewById(R.id.optionOne).startAnimation(leftOutAnim);
                findViewById(R.id.optionTwo).startAnimation(leftOutAnim);


                findViewById(R.id.flashcard_answer).startAnimation(leftOutAnim);
            }
        });
        findViewById(R.id.deletebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when deletebtn is clicked on, delete current question
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                //delete current answer
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_answer)).getText().toString());
                //delete current multiple choice option one
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.optionOne)).getText().toString());
                //delete option two
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.optionTwo)).getText().toString());
                //delete the answer side as well
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.answer_side)).getText().toString());

                //now update our list of cards in the deck
                allFlashcards = flashcardDatabase.getAllCards();
                //currentCardDisplayedIndex-=1;

                //display snackbar telling user it was successful
                Snackbar.make(findViewById(R.id.flashcard_question),
                        "Card deleted successfully",
                        Snackbar.LENGTH_LONG)
                        .show();

            }
        });
        String questionOne = getString(R.string.questionOne);
        String answerOne = getString(R.string.answerOne);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //which it will be because we made it that in the method above
            String string1 = data.getExtras().getString("string1");
            //again it should match the key we defined in the other method
            String string2 = data.getExtras().getString("string2");
            String string3 = data.getExtras().getString("string3");
            String string4 = data.getExtras().getString("string4");
            //now send those answers to the text views
            ((TextView)findViewById(R.id.flashcard_question)).setText(string1);
            ((TextView)findViewById(R.id.flashcard_answer)).setText(string2);
            ((TextView) findViewById(R.id.answer_side)).setText(string2);
            ((TextView) findViewById(R.id.optionOne)).setText(string3);
            ((TextView) findViewById(R.id.optionTwo)).setText(string4);
            //displays card was created successfully
            Snackbar.make(findViewById(R.id.flashcard_question),
                    "Card added successfully",
                    Snackbar.LENGTH_LONG)
                    .show();
            findViewById(R.id.editbtn).setVisibility(View.VISIBLE);

            flashcardDatabase.insertCard(new Flashcard(string1, string2, string3, string4));
        }
        }

    }



