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
import com.example.android.flashcard.databases.FlashcardDatabase;
import com.example.android.flashcard.R;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;

/*
mainActivity class displays saved cards, and allows users to delete cards.This class also
allow users to add cards.
 */

public class MainActivity extends AppCompatActivity {
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        final Animation leftOutAnim = AnimationUtils.loadAnimation(this, R.anim.enter_right);
        final Animation rightInAnim = AnimationUtils.loadAnimation(this, R.anim.exit_left);

         /*if our deck of saved cards has at least one card, then present/display
            the first saved card in the deck
          */
        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
            ((TextView) findViewById(R.id.optionOne)).setText(allFlashcards.get(0).getWrongAnswer1());
            ((TextView) findViewById(R.id.optionTwo)).setText(allFlashcards.get(0).getWrongAnswer2());
            ((TextView) findViewById(R.id.answer_side)).setText(allFlashcards.get(0).getAnswer());
        }

        /*
         when we click on the correct answer, we want background color to change to green!
        when we click on the wrong answer, color changes to red!
         */
        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.flashcard_answer).setBackgroundColor(getResources().getColor(R.color.green));
            }
        });

        findViewById(R.id.optionOne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.flashcard_answer).setBackgroundColor(getResources().getColor(R.color.green));
                findViewById(R.id.optionOne).setBackgroundColor(getResources().getColor(R.color.red));
            }
        });

        findViewById(R.id.optionTwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.flashcard_answer).setBackgroundColor(getResources().getColor(R.color.green));
                findViewById(R.id.optionTwo).setBackgroundColor(getResources().getColor(R.color.red));
            }
        });

        /*when users click on the question card, answer side becomes visible,
        question side, all choices, and buttons become invisible, animation is applied,
         */
        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View answerSideView = findViewById(R.id.flashcard_answer);
                //CENTER FOR CLIPPING CIRCLE
                int cx = answerSideView.getWidth()/2;
                int cy = answerSideView.getHeight() / 2;
                //FINAL RADIUS
                float finalRadius = (float) Math.hypot(cx, cy);
                Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);

                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
                findViewById(R.id.answer_side).setVisibility((View.VISIBLE));
                findViewById(R.id.optionTwo).setVisibility(View.INVISIBLE);
                findViewById(R.id.optionOne).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.nextbtn).setVisibility(View.INVISIBLE);

                anim.setDuration(5000);
                anim.start();
            }
        });

        /*when we click on the answer card, we want question card to be VISIBLE,
         AND all the other options to appear
         */
        findViewById(R.id.answer_side).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                findViewById(R.id.answer_side).setVisibility((View.INVISIBLE));
                findViewById(R.id.optionTwo).setVisibility(View.VISIBLE);
                findViewById(R.id.optionOne).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                findViewById(R.id.nextbtn).setVisibility(View.VISIBLE);
            }
        });

        /*
        when user clicks on plus button, start intent and take them to a blank addCard activity
        specify in the intent that we want data back; the question, answer and choices entered.
         */
        findViewById(R.id.plusbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, addCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.enter_right, R.anim.exit_left);
            }
        });

        /*when edit button is clicked, grab the currently displayed question and answer strings,
        then pass them to add card activity to be populated inside of editText
         */
        findViewById(R.id.editbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, addCardActivity.class);

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

        /*
         advance our pointer index so we can show the next card, check that we don't get
         an IndexOutOfBoundsError if we are viewing the last indexed card in our list
         set the question and answer TextViews with data from the database
          remove the green and red background, and add listener for animation, then start animation
         */
        findViewById(R.id.nextbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCardDisplayedIndex++;
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                ((TextView) findViewById(R.id.answer_side)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                ((TextView) findViewById(R.id.optionOne)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                ((TextView) findViewById(R.id.optionTwo)).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());

                findViewById(R.id.flashcard_answer).setBackgroundResource(R.drawable.answer_background);
                findViewById(R.id.optionTwo).setBackgroundResource(R.drawable.answer_background);
                findViewById(R.id.optionOne).setBackgroundResource(R.drawable.answer_background);


                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                });

                findViewById(R.id.flashcard_question).startAnimation(leftOutAnim);
                findViewById(R.id.optionOne).startAnimation(leftOutAnim);
                findViewById(R.id.optionTwo).startAnimation(leftOutAnim);
                findViewById(R.id.flashcard_answer).startAnimation(leftOutAnim);
            }
        });

        /*
        when deletebtn is clicked on, delete current question,current answer,current multiple choice option one, two
        and three, and answer side too.
        update list of cards in deck
        display snack bar saying it was successful
         */
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_answer)).getText().toString());
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.optionOne)).getText().toString());
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.optionTwo)).getText().toString());
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.answer_side)).getText().toString());
                allFlashcards = flashcardDatabase.getAllCards();
                Snackbar.make(findViewById(R.id.flashcard_question),
                        getString(R.string.success_delete), Snackbar.LENGTH_LONG).show();
            }
        });
    }
    /*
    verify that intent worked succefully, check that the data it gave us matches the key we defined
    then send answers to the text view in main activity.
    affirm user that card was added successfully
    make edit button visible, and add card to our database!
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            String string1 = data.getExtras().getString("string1");
            String string2 = data.getExtras().getString("string2");
            String string3 = data.getExtras().getString("string3");
            String string4 = data.getExtras().getString("string4");

            ((TextView)findViewById(R.id.flashcard_question)).setText(string1);
            ((TextView)findViewById(R.id.flashcard_answer)).setText(string2);
            ((TextView) findViewById(R.id.answer_side)).setText(string2);
            ((TextView) findViewById(R.id.optionOne)).setText(string3);
            ((TextView) findViewById(R.id.optionTwo)).setText(string4);

            Snackbar.make(findViewById(R.id.flashcard_question), getString(R.string.added_success),
                    Snackbar.LENGTH_LONG).show();

            findViewById(R.id.editbtn).setVisibility(View.VISIBLE);
            flashcardDatabase.insertCard(new Flashcard(string1, string2, string3, string4));
        }
        }
    }



