package com.example.android.flashcard.databases;

import com.example.android.flashcard.miscallenousclasses.Flashcard;
import com.example.android.flashcard.interfaces.FlashcardDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Flashcard.class}, version = 1)
/*
This class creates the actual database! I'll tell it that I'm putting objects of type Flashcard in here
and that I'm planning on accessing the data using Flashcard DAO
 */
public abstract class AppDatabase extends RoomDatabase {
    public abstract FlashcardDao flashcardDao();
}
