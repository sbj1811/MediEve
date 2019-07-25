package com.sjani.medieve.UI;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sjani.medieve.Models.Event;
import com.sjani.medieve.Models.User;
import com.sjani.medieve.Utils.DataRepository;

import java.util.List;

public class EventListViewModel extends ViewModel {
    private static final String TAG = EventListViewModel.class.getSimpleName();

    private DataRepository repository;

    public EventListViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<User>> getUsers() {
        return repository.getUserData();
    }

    public LiveData<List<User>> getUsersrfromDb() {
        return repository.getUsersFromDb();
    }

    public LiveData<List<Event>> getEventsforUser() {
        return repository.getEvents();
    }

    public void setEventinDb(Event event) {
        repository.setEvent(event);
    }

    public long getnewEventId() {
        return repository.getEventDbSize() + 1;
    }
}
