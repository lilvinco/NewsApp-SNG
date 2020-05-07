package com.startng.newsapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {

    //this selected conflict strategy ignores a new word if its exactly the same as one already existing
    @Insert(onConflict = OnConflictStrategy.IGNORE)

    //method to insert one word
    void insert (Word word);


    @Delete
    void delete (Word word);

    @Update
    void  update (Word word);

    //this query require a string of parameter to the annotation
    @Query("DELETE FROM word_table")
    // method to delete all the words
    void deleteAll();



    //return a list of words sorted in ascending order
    @Query("SELECT * from word_table ORDER BY word ASC")
    //method to get all words and return in list of words

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    LiveData<List<Word>> getAlphabetizedWords();

}
