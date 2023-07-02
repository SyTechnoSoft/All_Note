package com.allnote.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Update;

import com.allnote.Activity.Update_Activity;
import com.allnote.MainActivity;
import com.allnote.Model.Notes;
import com.allnote.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdatper extends RecyclerView.Adapter<NotesAdatper.notesViewHolder> {

    MainActivity mainActivity;
    List<Notes> notes;
    List<Notes> allNoteItem;

    public NotesAdatper(MainActivity mainActivity, List<Notes> notes) {
        this.mainActivity = mainActivity;
        this.notes = notes;
        allNoteItem=new ArrayList<>(notes);
    }
    public void searchNotes(List<Notes> filterredNotes){

        this.notes=filterredNotes;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public notesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new notesViewHolder(LayoutInflater.from(mainActivity).inflate(R.layout.item_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull notesViewHolder holder, int position) {
        Notes note = notes.get(position);

        switch (note.notesPriority) {
            case "1":
                holder.priority.setBackgroundResource(R.drawable.green_shape);
                holder.linearLayout.setBackgroundResource(R.drawable.green_background);
                break;
            case "2":
                holder.priority.setBackgroundResource(R.drawable.yellow_shape);
                holder.linearLayout.setBackgroundResource(R.drawable.yellow_background);
                break;
            case "3":
                holder.priority.setBackgroundResource(R.drawable.red_shape);
                holder.linearLayout.setBackgroundResource(R.drawable.red_background);
                break;
        }
        holder.title.setText(note.notesTitle);
        holder.subtitle.setText(note.notesSubTitle);
        holder.notes.setText(note.notes);
        holder.date.setText(note.notesDate);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mainActivity, Update_Activity.class);
            intent.putExtra("id", note.id);
            intent.putExtra("title", note.notesTitle);
            intent.putExtra("subtitle", note.notesSubTitle);
            intent.putExtra("notes", note.notes);
            intent.putExtra("priority", note.notesPriority);
            mainActivity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class notesViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle, notes, date;
        View priority;
        LinearLayout linearLayout;

        public notesViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.notesTitle);
            subtitle = itemView.findViewById(R.id.notesSubtitle);
            notes = itemView.findViewById(R.id.notes);
            date = itemView.findViewById(R.id.notesDaTe);
            priority = itemView.findViewById(R.id.noteSPriority);
            linearLayout = itemView.findViewById(R.id.linear_item);
        }
    }
}
