package com.rrajath.reactivenotes.notes;

import com.rrajath.reactivenotes.data.model.Note;

import java.util.List;

public interface NotesContract {
    interface View extends BaseView<Presenter> {
        void showNotes(List<Note> allNotes);

        void hideNoNotesMessage();

        void showNoNotesMessage();

        void showLoadingNotesError();

        void hideLoadingIndicator();

        void showLoadingIndicator();

        void onNoteClick(Note clickedNote);
    }

    interface Presenter extends BasePresenter {
        void loadNotes();

        void onNoteClick(Note clickedNote);

        void removeNote(int position);
    }

}
