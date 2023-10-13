package com.allnote.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.format.DateFormat;

import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.allnote.Model.Notes;
import com.allnote.R;
import com.allnote.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.Date;
import java.util.Objects;

public class Insert_Activity extends AppCompatActivity {
    EditText et_title, et_subTitle, et_notes;
    FloatingActionButton btn;
    NotesViewModel notesViewModel;
    String title, subtitle, notes;
    String Priority;
    ImageView redPriority, yellowPriority, greenPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create Notes");

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//floating button
        et_title = findViewById(R.id.edit_title);
        et_subTitle = findViewById(R.id.edit_subTitle);
        et_notes = findViewById(R.id.edit_notes);
        btn = findViewById(R.id.doneNotes);
        redPriority = findViewById(R.id.red);
        yellowPriority = findViewById(R.id.yellow);
        greenPriority = findViewById(R.id.green);
        Priority = "1";
        greenPriority.setOnClickListener(v -> {
            Priority = "1";
            greenPriority.setImageResource(R.drawable.done);
            yellowPriority.setImageResource(0);
            redPriority.setImageResource(0);
        });
        yellowPriority.setOnClickListener(v -> {
            Priority = "2";
            greenPriority.setImageResource(0);
            yellowPriority.setImageResource(R.drawable.done);
            redPriority.setImageResource(0);
        });
        redPriority.setOnClickListener(v -> {
            Priority = "3";
            greenPriority.setImageResource(0);
            yellowPriority.setImageResource(0);
            redPriority.setImageResource(R.drawable.done);
        });


        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        btn.setOnClickListener(v -> {
            Create();

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do tou want to save " + et_title.getText().toString() + " ?");
        builder.setNeutralButton("Cancel",
                (dialog, which) -> dialog.cancel());
        builder.setPositiveButton("Save",
                (dialog, which) -> Create());
        builder.setNegativeButton("Don't Save",
                (dialog, which) -> finish());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void Create() {
        title = et_title.getText().toString();
        subtitle = et_subTitle.getText().toString();
        notes = et_notes.getText().toString();
        CreateNotes(title, subtitle, notes);
    }


    private void CreateNotes(String title, String subtitle, String notes) {
        Date date = new Date();
        CharSequence sequence = DateFormat.format("d-MMMM-yyyy", date.getTime());
        Notes notes1 = new Notes();
        notes1.notesTitle = title;
        notes1.notesSubTitle = subtitle;
        notes1.notes = notes;
        notes1.notesDate = sequence.toString();
        notes1.notesPriority = Priority;
        notesViewModel.insertNote(notes1);
        Toast.makeText(this, "Note Created successfully ", Toast.LENGTH_SHORT).show();
        finish();
    }
}