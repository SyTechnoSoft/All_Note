package com.allnote.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.allnote.Dao.NotesDao;
import com.allnote.Database.NotesDatabase;
import com.allnote.Model.Notes;

import java.util.List;

public class NotesRepository {

    public NotesDao notesDao;
    public LiveData<List<Notes>> getAllNotes;

    public LiveData<List<Notes>> HighToLow;


    public LiveData<List<Notes>> LowToHigh;


    public NotesRepository(Application application) {
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);
        notesDao = database.notesDao();
        getAllNotes = notesDao.getAllNotes();
        HighToLow =notesDao.hightolow();
        LowToHigh=notesDao.lowtohigh();


    }

    public void insertNotes(Notes notes) {
        notesDao.insertNotes(notes);

    }

    public void deleteNotes(int id) {
        notesDao.deleteNotes(id);

    }

    public void updateNotes(Notes notes) {
        notesDao.updateNotes(notes);
    }
}
