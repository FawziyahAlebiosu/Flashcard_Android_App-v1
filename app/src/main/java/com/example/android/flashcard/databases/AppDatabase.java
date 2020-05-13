package com.example.android.flashcard.databases;

import com.example.android.flashcard.miscallenousclasses.Flashcard;
import com.example.android.flashcard.interfaces.FlashcardDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Flashcard.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FlashcardDao flashcardDao();
}
