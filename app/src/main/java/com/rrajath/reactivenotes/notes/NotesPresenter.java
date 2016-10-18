package com.rrajath.reactivenotes.notes;

import com.rrajath.reactivenotes.data.NotesRepository;
import com.rrajath.reactivenotes.data.model.Note;
import com.rrajath.reactivenotes.util.schedulers.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

public class NotesPresenter implements NotesContract.Presenter {

    private final NotesRepository notesRepository;
    private final NotesContract.View notesView;
    private CompositeSubscription mSubscriptions;
    private SchedulerProvider mSchedulerProvider;

    @Inject
    public NotesPresenter(NotesRepository notesRepository, NotesContract.View notesView, SchedulerProvider mSchedulerProvider) {
        this.notesRepository = notesRepository;
        this.notesView = notesView;
        this.mSchedulerProvider = mSchedulerProvider;

        mSubscriptions = new CompositeSubscription();
        notesView.setPresenter(this);
    }

    @Override
    public void loadNotes() {
        mSubscriptions.clear();
        notesView.showLoadingIndicator();
        Subscription subscription = notesRepository.getAllNotes()
                .flatMap(new Func1<List<Note>, Observable<Note>>() {
                    @Override
                    public Observable<Note> call(List<Note> notes) {
                        return Observable.from(notes);
                    }
                })
                .toList()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Observer<List<Note>>() {
                    @Override
                    public void onCompleted() {
                        notesView.hideLoadingIndicator();
                    }

                    @Override
                    public void onError(Throwable e) {
                        notesView.showLoadingNotesError();
                    }

                    @Override
                    public void onNext(List<Note> notes) {
                        if (notes.size() == 0) {
                            notesView.showNoNotesMessage();
                        } else {
                            notesView.showNotes(notes);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void subscribe() {
        loadNotes();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void onNoteClick(Note clickedNote) {
        notesView.onNoteClick(clickedNote);
    }

    @Override
    public void removeNote(int position) {
        notesRepository.deleteNote(position);
    }
}
