package com.rrajath.reactivenotes.notes;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rrajath.reactivenotes.R;
import com.rrajath.reactivenotes.data.model.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> notes;
    private NotesActivity.NotesItemListener notesItemListener;

    public NotesAdapter(NotesActivity.NotesItemListener notesItemListener) {
        this.notesItemListener = notesItemListener;
    }

    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());
        holder.icon.setText(getFirstChar(note.getTitle()));
        holder.icon.setBackgroundResource(R.drawable.circle);
        Drawable background = holder.icon.getBackground();
        GradientDrawable shapeDrawable = (GradientDrawable) background;
        shapeDrawable.setColor(Color.BLUE);

    }

    private String getFirstChar(String title) {
        return title.substring(0, 1);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView icon;

        public NotesViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.note_title);
            description = (TextView) itemView.findViewById(R.id.note_description);
        }
    }
}
