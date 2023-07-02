package com.allnote.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.allnote.Model.Notes;
import com.allnote.Repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {
    public NotesRepository notesRepository;
    public LiveData<List<Notes>> getAllNotes;
    public LiveData<List<Notes>> hightolow;
    public LiveData<List<Notes>> lowtohigh;

    public NotesViewModel(@NonNull Application application) {
        super(application);

        notesRepository = new NotesRepository(application);
        getAllNotes = notesRepository.getAllNotes;
        hightolow=notesRepository.HighToLow;
        lowtohigh=notesRepository.LowToHigh;

    }

    public void insertNote(Notes notes) {
        notesRepository.insertNotes(notes);
    }

    public void deleteNote(int id) {
        notesRepository.deleteNotes(id);
    }

    public void updateNote(Notes notes) {
        notesRepository.updateNotes(notes);
    }

}
