package com.rrajath.reactivenotes.notes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rrajath.reactivenotes.BaseActivity;
import com.rrajath.reactivenotes.R;
import com.rrajath.reactivenotes.ReactiveNotesApplication;
import com.rrajath.reactivenotes.data.model.Note;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotesActivity extends BaseActivity implements NotesContract.View {

    @Inject
    NotesPresenter notesPresenter;

    @Bind(R.id.add_note)
    FloatingActionButton fabAddNote;
    @Bind(R.id.no_notes_message)
    TextView tvNoNotesMessage;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.notes_list)
    RecyclerView rvNotesList;

    private NotesAdapter notesAdapter;
    private NotesItemListener notesItemListener = new NotesItemListener() {
        @Override
        public void onNoteClick(Note clickedNote) {
            notesPresenter.onNoteClick(clickedNote);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);
        notesAdapter = new NotesAdapter(notesItemListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                notesPresenter.removeNote(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvNotesList);
        notesPresenter.subscribe();
    }

    @Override
    public void setupActivityComponent() {
        ReactiveNotesApplication
                .get(this)
                .getAppComponent()
                .plus(new NotesModule(this))
                .inject(this);
    }

    @OnClick(R.id.add_note)
    public void onAddNoteClick() {
        Toast.makeText(this, "Coming soon!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNotes(List<Note> notes) {
        hideNoNotesMessage();
        notesAdapter = new NotesAdapter(notesItemListener);
        notesAdapter.setNotes(notes);
        rvNotesList.setLayoutManager(new LinearLayoutManager(this));
        rvNotesList.setAdapter(notesAdapter);

    }

    @Override
    public void hideNoNotesMessage() {
        tvNoNotesMessage.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNoNotesMessage() {
        tvNoNotesMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadingNotesError() {
        Toast.makeText(this, "There was an error loading your notes. Please try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoadingIndicator() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadingIndicator() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onNoteClick(Note clickedNote) {
        notesItemListener.onNoteClick(clickedNote);
    }

    @Override
    public void setPresenter(NotesContract.Presenter presenter) {
        this.notesPresenter = (NotesPresenter) presenter;
    }

    private void refreshContent() {
        notesPresenter.loadNotes();
    }

    interface NotesItemListener {
        void onNoteClick(Note clickedNote);
    }
}
