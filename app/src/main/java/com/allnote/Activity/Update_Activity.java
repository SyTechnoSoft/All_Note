package com.allnote.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allnote.Model.Notes;
import com.allnote.R;
import com.allnote.ViewModel.NotesViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

public class Update_Activity extends AppCompatActivity {
    String sTitle, sSubtitle, sNotes, sPriority;
    int iid;
    EditText Uet_title, Uet_subTitle, Uet_notes;
    ImageView redPriority, yellowPriority, greenPriority;
    String Priority = "1";
    FloatingActionButton btn;
    NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().setTitle("Update Notes");
        find_id();
        iid = getIntent().getIntExtra("id", 1);
        sTitle = getIntent().getStringExtra("title");
        sSubtitle = getIntent().getStringExtra("subtitle");
        sNotes = getIntent().getStringExtra("notes");
        sPriority = getIntent().getStringExtra("priority");

        switch (sPriority) {
            case "1":
                Priority = "1";
                greenPriority.setImageResource(R.drawable.done);
                break;
            case "2":
                Priority = "2";
                yellowPriority.setImageResource(R.drawable.done);

                break;
            case "3":
                Priority = "3";
                redPriority.setImageResource(R.drawable.done);
                break;
        }

        Uet_title.setText(sTitle);
        Uet_subTitle.setText(sSubtitle);
        Uet_notes.setText(sNotes);
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
            Update();

            //  Snackbar.make(v, "Note Updated Successfully", Snackbar.LENGTH_SHORT).show();

        });

    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Do you to want to Update "+Uet_title.getText().toString()+"?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Update",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Update();
                    }
                });

        builder1.setNegativeButton(
                "Don't Update",
                (dialog, id) -> {
                    dialog.cancel();
                    finish();

                });
        builder1.setNeutralButton("Cancel",
                (dialog, id) -> dialog.cancel());
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    private void Update() {
        String title = Uet_title.getText().toString();
        String subtitle = Uet_subTitle.getText().toString();
        String notes = Uet_notes.getText().toString();
        UpdateNotes(title, subtitle, notes);

    }
    private void UpdateNotes(String title, String subtitle, String notes) {

        Date date = new Date();
        CharSequence sequence = DateFormat.format("d-MMMM-yyyy", date.getTime());
        Notes notes3 = new Notes();
        notes3.id = iid;
        notes3.notesTitle = title;
        notes3.notesSubTitle = subtitle;
        notes3.notes = notes;
        notes3.notesDate = sequence.toString();
        notes3.notesPriority = Priority;
        notesViewModel.updateNote(notes3);
        Toast.makeText(this, "Note Updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void find_id() {
        Uet_title = findViewById(R.id.Uedit_title);
        Uet_subTitle = findViewById(R.id.Uedit_subTitle);
        Uet_notes = findViewById(R.id.Uedit_notes);
        greenPriority = findViewById(R.id.greenU);
        yellowPriority = findViewById(R.id.yellowU);
        redPriority = findViewById(R.id.redU);
        btn = findViewById(R.id.updateNotes);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.ic_delete){
            BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(Update_Activity.this);

            View view = LayoutInflater.from(Update_Activity.this).inflate(R.layout.delete_botton_sheet,(LinearLayout)findViewById(R.id.bottom_sheet));
            bottomSheetDialog.setContentView(view);
            bottomSheetDialog.setCancelable(true);
            TextView yes,no;

            yes=view.findViewById(R.id.delete_yes);
            no=view.findViewById(R.id.delete_no);
            yes.setOnClickListener(v -> {
                notesViewModel.deleteNote(iid);
                finish();
            });
            no.setOnClickListener(v -> {
                bottomSheetDialog.dismiss();
            });
            bottomSheetDialog.show();

        }return true;
    };
}